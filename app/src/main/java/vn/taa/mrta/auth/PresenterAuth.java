package vn.taa.mrta.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.taa.mrta.R;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.internet.JSONDownloader;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.object.Clinic;
import vn.taa.mrta.object.Patient;

/**
 * Created by Putin on 3/4/2017.
 */

public class PresenterAuth implements IPresenterAuth {
    private IViewAuth iViewAuth;
    private String urlHost;
    private String url;
    private SharedPreferences sprfSKB;

    public PresenterAuth(IViewAuth iViewAuth) {
        this.iViewAuth = iViewAuth;
    }

    @Override
    public void getHostInfo(Context context, String mahs) {
        sprfSKB = context.getSharedPreferences(CommonField.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        urlHost = CommonField.URL_HOST;

        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> param = new HashMap<>();
        param.put("PMID", mahs.substring(0, CommonField.CLINIC_ID_SIZE));
        params.add(param);

        JSONDownloader jsonDownloader = new JSONDownloader(urlHost, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            try {
                JSONObject jsonObject = new JSONObject(data);
                sprfSKB.edit().putString(CommonField.SQL_name, jsonObject.getString(CommonField.SQL_name))
                        .putString(CommonField.SQL_IP, jsonObject.getString(CommonField.SQL_IP))
                        .putString(CommonField.View_user, jsonObject.getString(CommonField.View_user))
                        .putString(CommonField.View_pass, jsonObject.getString(CommonField.View_pass))
                        .apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
            Toast.makeText(context, context.getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void login(Context context, String mahs) {
        url = CommonField.URL_SERVICES;

        List<HashMap<String, String>> params = new ArrayList<>();

        HashMap<String, String> param0 = new HashMap<>();
        param0.put(CommonField.SQL_name, sprfSKB.getString(CommonField.SQL_name, ""));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put(CommonField.SQL_IP, sprfSKB.getString(CommonField.SQL_IP, ""));

        HashMap<String, String> param2 = new HashMap<>();
        param2.put(CommonField.View_user, sprfSKB.getString(CommonField.View_user, ""));

        HashMap<String, String> param3 = new HashMap<>();
        param3.put(CommonField.View_pass, sprfSKB.getString(CommonField.View_pass, ""));

        HashMap<String, String> param4 = new HashMap<>();
        param4.put("func", "auth");

        HashMap<String, String> param5 = new HashMap<>();
        param5.put("mahs", mahs.substring(CommonField.CLINIC_ID_SIZE));

        params.add(param0);
        params.add(param1);
        params.add(param2);
        params.add(param3);
        params.add(param4);
        params.add(param5);

        JSONDownloader jsonDownloader = new JSONDownloader(url, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            try {
                JSONObject jsonObject = new JSONObject(data);

                if (jsonObject.getString("auth").equals("success")) {
                    JSONObject user = jsonObject.getJSONObject("user");
                    String name = user.getString("HoTen");
                    String gender = user.getString("Phai");
                    String birthday = user.getString("NgaySinh");
                    String address = user.getString("DiaChi");
                    String phone = user.getString("DienThoai");

                    SharedPreferences sprfSKB = context.getSharedPreferences(CommonField.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    Patient patient = new Patient(name, gender, birthday, address, phone);
                    sprfSKB.edit().putString(CommonField.PATIENT_INFO_DETAIL, new Gson().toJson(patient)).apply();

                    iViewAuth.loginSuccess(patient);
                } else {
                    iViewAuth.loginFailed();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
