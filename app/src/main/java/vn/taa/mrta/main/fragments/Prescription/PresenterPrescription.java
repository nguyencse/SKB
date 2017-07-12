package vn.taa.mrta.main.fragments.Prescription;

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
 * Created by Putin on 3/9/2017.
 */

public class PresenterPrescription implements IPresenterPrescription {
    private IViewPrescription iViewPrescription;

    public PresenterPrescription(IViewPrescription iViewPrescription) {
        this.iViewPrescription = iViewPrescription;
    }

    @Override
    public void getLastPrescription(String mahs) {
        List<HashMap<String, String>> params = new ArrayList<>();
        CommonMethod.putParamsToHost(params);

        HashMap<String, String> param0 = new HashMap<>();
        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getLastPrescription");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("mahs", mahs.substring(CommonField.CLINIC_ID_SIZE));

        params.add(param0);
        params.add(param1);
        params.add(param2);

        JSONDownloader jsonDownloader = new JSONDownloader(CommonField.URL_SERVICES, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            JSONObject jsonObject = new JSONObject(data);

            JSONObject info = jsonObject.getJSONObject("info");
            PrescriptionInfo patientInfo = new PrescriptionInfo();

            patientInfo.setTime(info.getString("LanKham"));
            patientInfo.setDate(info.getString("NgayKham"));
            patientInfo.setGeneralNote(info.getString("GhiChuChung"));
            patientInfo.setDoctorName(info.getString("NguoiKham"));
            patientInfo.setDoctorOffice(info.getString("ChucVuNguoiKham"));

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

            iViewPrescription.showLastPrescriptionDrug(new Prescription(patientInfo, prescriptionDrugList));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}