package vn.taa.mrta.history.Treatment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.general.CommonMethod;
import vn.taa.mrta.internet.JSONDownloader;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.object.Treatment;

/**
 * Created by Putin on 3/8/2017.
 */

public class PresenterHistoryTreatment implements IPresenterHistoryTreatment {
    private IViewHistoryTreatment iViewHistoryTreatment;
    private List<Treatment> treatmentList;

    public PresenterHistoryTreatment(IViewHistoryTreatment iViewHistoryTreatment) {
        this.iViewHistoryTreatment = iViewHistoryTreatment;
        treatmentList = new ArrayList<>();
    }

    @Override
    public void getHistoryTreatment(String mahs) {
        List<HashMap<String, String>> params = new ArrayList<>();
        CommonMethod.putParamsToHost(params);

        HashMap<String, String> param0 = new HashMap<>();
        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getTreatmentList");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("mahs", mahs.substring(CommonField.CLINIC_ID_SIZE));

        params.add(param0);
        params.add(param1);
        params.add(param2);

        String url = CommonField.URL_SERVICES;

        JSONDownloader jsonDownloader = new JSONDownloader(url, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("treatments");

            int count = jsonArray.length();

            for (int i = 0; i < count; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Treatment treatment = new Treatment();
                treatment.setTime(object.getString("LanKham"));
                treatment.setDate(object.getString("NgayKham"));
                treatment.setDoctorName(object.getString("NguoiKham"));
                treatmentList.add(treatment);
            }

            iViewHistoryTreatment.showHistory(treatmentList);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
