package vn.taa.mrta.main.fragments.LastTreatment;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.taa.mrta.R;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.general.CommonMethod;
import vn.taa.mrta.internet.JSONDownloader;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.object.Treatment;

/**
 * Created by Putin on 3/4/2017.
 */

public class PresenterLastTreatment implements IPresenterLastTreatment {
    private IViewLastTreatment iViewLastTreatment;
    private String url;

    public PresenterLastTreatment(IViewLastTreatment iViewLastTreatment) {
        this.iViewLastTreatment = iViewLastTreatment;
    }

    @Override
    public void getLastTreatment(Context context, String mahs) {
        List<HashMap<String, String>> params = new ArrayList<>();

        HashMap<String, String> param0 = new HashMap<>();
        CommonMethod.putParamsToHost(params);

        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getLastTreatment");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("mahs", mahs.substring(CommonField.CLINIC_ID_SIZE));

        params.add(param0);
        params.add(param1);
        params.add(param2);

        url = CommonField.URL_SERVICES;
        JSONDownloader jsonDownloader = new JSONDownloader(url, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            JSONObject jsonObject = new JSONObject(data);

            Treatment lastTreatment = new Treatment();

            lastTreatment.setTime(jsonObject.getString("LanKham"));
            lastTreatment.setDate(jsonObject.getString("NgayKham"));
            lastTreatment.setDoctorName(jsonObject.getString("NguoiKham"));
            lastTreatment.setDoctorOffice(jsonObject.getString("ChucVuNguoiKham"));
            lastTreatment.setSympton(jsonObject.getString("TrieuChung"));
            lastTreatment.setContent(jsonObject.getString("NDDieuTri"));
            lastTreatment.setNote(jsonObject.getString("GhiChu"));
            if (jsonObject.getInt("HuyKham") == 0) {
                lastTreatment.setIsCancel(context.getString(R.string.message_no));
            } else {
                lastTreatment.setIsCancel(context.getString(R.string.message_canceled));
            }

            SharedPreferences sprfSKB = context.getSharedPreferences(CommonField.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            sprfSKB.edit().putString(CommonField.LAST_TREATMENT_DETAIL, new Gson().toJson(lastTreatment)).apply();

//            String cost = jsonObject.getString("ChiPhiKham");
//            String cardiovascular = jsonObject.getString("Mach");
//            String temperature = jsonObject.getString("NhietDo");
//            String bloodPressure = jsonObject.getString("HuyetAp");
//            String breathingRate = jsonObject.getString("NhipTho");
//            String weight = jsonObject.getString("CanNang");
//            String height = jsonObject.getString("ChieuCao");
//            String bmi = jsonObject.getString("BMI");
//            String status = jsonObject.getString("Status");
//            String clinicalApproach = jsonObject.getString("CanLamSang");

//            iViewLastTreatment.showInfoTreatment(time, date, doctorName, doctorOffice, symptom,
//                    content, cost, note, isCancel, cardiovascular, temperature,
//                    bloodPressure, breathingRate, weight, height, bmi, status, clinicalApproach);

            iViewLastTreatment.showLastTreatment(lastTreatment);

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
