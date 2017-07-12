package vn.taa.mrta.details.Treatment;

import android.content.Context;

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
 * Created by Putin on 4/2/2017.
 */

public class PresenterTreatmentDetails implements IPresenterTreatmentDetails {
    private IViewTreatmentDetails iViewTreatmentDetails;

    public PresenterTreatmentDetails(IViewTreatmentDetails iViewTreatmentDetails) {
        this.iViewTreatmentDetails = iViewTreatmentDetails;
    }

    @Override
    public void getTreatmentDetails(Context context, String mahs, String time) {
        List<HashMap<String, String>> params = new ArrayList<>();

        HashMap<String, String> param0 = new HashMap<>();
        CommonMethod.putParamsToHost(params);

        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getTreatment");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("mahs", mahs.substring(CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param3 = new HashMap<>();
        param3.put("time", time);

        params.add(param0);
        params.add(param1);
        params.add(param2);
        params.add(param3);

        String url = CommonField.URL_SERVICES;

        JSONDownloader jsonDownloader = new JSONDownloader(url, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();
            JSONObject object = new JSONObject(data);
            Treatment treatment = new Treatment();

            treatment.setTime(object.getString("LanKham"));
            treatment.setDate(object.getString("NgayKham"));
            treatment.setDoctorName(object.getString("NguoiKham"));
            treatment.setDoctorOffice(object.getString("ChucVuNguoiKham"));
            treatment.setSympton(object.getString("TrieuChung"));
            treatment.setContent(object.getString("NDDieuTri"));
            treatment.setNote(object.getString("GhiChu"));
            if (object.getInt("HuyKham") == 0) {
                treatment.setIsCancel(context.getString(R.string.message_no));
            } else {
                treatment.setIsCancel(context.getString(R.string.message_canceled));
            }

//            treatment.setCost(object.getString("ChiPhiKham"));
//            treatment.setCardiovascular(object.getString("Mach"));
//            treatment.setTemperature(object.getString("NhietDo"));
//            treatment.setBloodPressure(object.getString("HuyetAp"));
//            treatment.setBreathingRate(object.getString("NhipTho"));
//            treatment.setWeight(object.getString("CanNang"));
//            treatment.setHeight(object.getString("ChieuCao"));
//            treatment.setBmi(object.getString("BMI"));
//            treatment.setStatus(object.getString("Status"));
//            treatment.setClinicalApproach(object.getString("CanLamSang"));

            iViewTreatmentDetails.showTreatmentDetails(treatment);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
