package vn.taa.mrta.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.general.CommonMethod;
import vn.taa.mrta.internet.JSONDownloader;
import vn.taa.mrta.object.Clinic;

import static vn.taa.mrta.main.MainActivity.sprfSKB;

/**
 * Created by Putin on 3/27/2017.
 */

public class PresenterMain implements IPresenterMain {
    private IViewMain iViewMain;
    private String url;

    public PresenterMain(IViewMain iViewMain) {
        this.iViewMain = iViewMain;
    }

    @Override
    public void getProfile(Context context) {
//        SharedPreferences sprfSKB = context.getSharedPreferences(CommonField.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void getClinicInfo(Context context, String id) {
        url = CommonField.URL_SERVICES;

        List<HashMap<String, String>> params = new ArrayList<>();

        CommonMethod.putParamsToHost(params);

        HashMap<String, String> param0 = new HashMap<>();
        param0.put("id", id);

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getClinicInfo");

        params.add(param0);
        params.add(param1);

        JSONDownloader jsonDownloader = new JSONDownloader(url, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            try {
                JSONObject jsonObject = new JSONObject(data);

                if (jsonObject.getString("clinic_matched").equals("true")) {
                    JSONObject object = jsonObject.getJSONObject("clinic");
                    String nameUnit = object.getString("TenDonVi");
                    String nameClinic = object.getString("TenPM");
                    String address = object.getString("DiaChiPM");
                    String phone = object.getString("DienThoaiPM");
                    String email = object.getString("EmailPM");
                    String fax = object.getString("FaxPM");

                    Clinic clinic = new Clinic(nameUnit, nameClinic, address, phone, email, fax);
                    iViewMain.showClinicInfo(clinic);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
