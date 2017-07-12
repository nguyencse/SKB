package vn.taa.mrta.main.fragments.Dashboard;

import android.text.TextUtils;
import android.util.Log;

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
import vn.taa.mrta.object.Dashboard;
import vn.taa.mrta.object.Drug;

/**
 * Created by Putin on 4/3/2017.
 */

public class PresenterDashboard implements IPresenterDashboard {
    private IViewDashboard iViewDashboard;

    public PresenterDashboard(IViewDashboard iViewDashboard) {
        this.iViewDashboard = iViewDashboard;
    }

    @Override
    public void getDashboard(String mahs) {
        List<HashMap<String, String>> params = new ArrayList<>();

        CommonMethod.putParamsToHost(params);

        HashMap<String, String> param0 = new HashMap<>();
        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getLastPrescription");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("mahs", mahs);

        params.add(param0);
        params.add(param1);
        params.add(param2);

        JSONDownloader jsonDownloader = new JSONDownloader(CommonField.URL_SERVICES, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            JSONObject jsonObject = new JSONObject(data);

            Log.e("CSE_Dashboard", data);

//            JSONObject objectProfile = jsonObject.getJSONObject("info");
//            Patient patient = new Patient();
//            patient.setName(objectProfile.getString("HoTen"));
//            patient.setGender(objectProfile.getString("Phai"));
//            patient.setBirthday(objectProfile.getString("NgaySinh"));
//            patient.setAddress(objectProfile.getString("DiaChi"));
//            patient.setPhone(objectProfile.getString("DienThoai"));
//            patient.setAvatar(objectProfile.getString("Hinh"));

            JSONArray jsonArrayDrug = jsonObject.getJSONArray("drugs");
            int count = jsonArrayDrug.length();

            List<Drug> drugListMorning = new ArrayList<>();
            List<Drug> drugListNoon = new ArrayList<>();
            List<Drug> drugListAfternoon = new ArrayList<>();
            List<Drug> drugListEvening = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                JSONObject object = jsonArrayDrug.getJSONObject(i);
                Drug drug = new Drug();
                drug.setName(object.getString("TenThuoc"));
                drug.setUnit(object.getString("DonViSuDung"));

                String quantityMorning = object.getString("Sang");
                String quantityNoon = object.getString("Trua");
                String quantityAfternoon = object.getString("Chieu");
                String quantityEvening = object.getString("Toi");

                if (!quantityMorning.isEmpty() && TextUtils.isDigitsOnly(quantityMorning)) {
                    int quantity = Integer.parseInt(quantityMorning);
                    if (quantity > 0) {
                        drug.setQuantity(quantity);
                        drugListMorning.add(drug);
                    }
                }
                if (!quantityNoon.isEmpty() && TextUtils.isDigitsOnly(quantityNoon)) {
                    int quantity = Integer.parseInt(quantityNoon);
                    if (quantity > 0) {
                        drug.setQuantity(quantity);
                        drugListNoon.add(drug);
                    }
                }
                if (!quantityAfternoon.isEmpty() && TextUtils.isDigitsOnly(quantityAfternoon)) {
                    int quantity = Integer.parseInt(quantityAfternoon);
                    if (quantity > 0) {
                        drug.setQuantity(quantity);
                        drugListAfternoon.add(drug);
                    }
                }

                if (!quantityEvening.isEmpty() && TextUtils.isDigitsOnly(quantityEvening)) {
                    int quantity = Integer.parseInt(quantityEvening);
                    if (quantity > 0) {
                        drug.setQuantity(quantity);
                        drugListEvening.add(drug);
                    }
                }
            }

            Dashboard dashboard = new Dashboard(null, drugListMorning, drugListNoon, drugListAfternoon, drugListEvening);
            iViewDashboard.showDashboard(dashboard);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
