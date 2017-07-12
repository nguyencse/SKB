package vn.taa.mrta.details.Prescription;

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
import vn.taa.mrta.object.Prescription;
import vn.taa.mrta.object.PrescriptionDrug;
import vn.taa.mrta.object.PrescriptionInfo;

/**
 * Created by Putin on 3/11/2017.
 */

public class PresenterPrescriptionDetails implements IPresenterPrescriptionDetails {
    private IViewPrescriptionDetails iViewPrescriptionDetails;

    public PresenterPrescriptionDetails(IViewPrescriptionDetails iViewPrescriptionDetails) {
        this.iViewPrescriptionDetails = iViewPrescriptionDetails;
    }

    @Override
    public void getPrescriptionDetails(String mahs, String time) {
        List<HashMap<String, String>> params = new ArrayList<>();

        HashMap<String, String> param0 = new HashMap<>();
        CommonMethod.putParamsToHost(params);

        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getPrescription");

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
            JSONObject jsonObject = new JSONObject(data);
            JSONObject objectInfo = jsonObject.getJSONObject("info");

            PrescriptionInfo info = new PrescriptionInfo();
            info.setTime(objectInfo.getString("LanKham"));
            info.setDate(objectInfo.getString("NgayKham"));
            info.setGeneralNote(objectInfo.getString("GhiChuChung"));
            info.setDoctorOffice(objectInfo.getString("ChucVuNguoiKham"));
            info.setDoctorName(objectInfo.getString("NguoiKham"));

            JSONArray drugs = jsonObject.getJSONArray("drugs");
            int count = drugs.length();

            List<PrescriptionDrug> prescriptionDrugList = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                JSONObject object = drugs.getJSONObject(i);
                PrescriptionDrug drug = new PrescriptionDrug();

                drug.setOrder(object.getInt("STT"));
                drug.setName(object.getString("TenThuoc"));
                drug.setQuantity(object.getInt("SoLuong"));
                drug.setUnit(object.getString("DonViThuoc"));
                drug.setNote(object.getString("GhiChuRieng"));
                drug.setMorning(object.getString("Sang"));
                drug.setNoon(object.getString("Trua"));
                drug.setAfternoon(object.getString("Chieu"));
                drug.setEvening(object.getString("Toi"));

                prescriptionDrugList.add(drug);
            }

            iViewPrescriptionDetails.showPrescriptionDetails(new Prescription(info, prescriptionDrugList));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
