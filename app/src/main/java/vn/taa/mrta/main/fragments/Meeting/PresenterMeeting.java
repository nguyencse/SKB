package vn.taa.mrta.main.fragments.Meeting;

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
import vn.taa.mrta.object.Meeting;

/**
 * Created by Putin on 5/3/2017.
 */

public class PresenterMeeting implements IPresenterMeeting {
    private IViewMeeting iViewMeeting;

    public PresenterMeeting(IViewMeeting iViewMeeting) {
        this.iViewMeeting = iViewMeeting;
    }

    @Override
    public void getLastMeeting(String mahs) {
        List<HashMap<String, String>> params = new ArrayList<>();

        HashMap<String, String> param0 = new HashMap<>();
        CommonMethod.putParamsToHost(params);

        param0.put("id", MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getLastMeeting");

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
            Meeting lastMeeting = new Meeting();
            lastMeeting.setDate(jsonObject.getString("NgayHen"));
            lastMeeting.setContent(jsonObject.getString("NDHen"));
            lastMeeting.setNote(jsonObject.getString("GhiChu"));
            lastMeeting.setDoctor(jsonObject.getString("NguoiHen"));
            lastMeeting.setOffice(jsonObject.getString("ChucVuNguoiHen"));
            lastMeeting.setPhone(jsonObject.getString("DienThoai"));

            iViewMeeting.showLastMeeting(lastMeeting);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMeetingList(String mahs) {
        List<HashMap<String, String>> params = new ArrayList<>();

        HashMap<String, String> param0 = new HashMap<>();
        param0.put("id", Integer.parseInt(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, 5)) + "");

        HashMap<String, String> param1 = new HashMap<>();
        param1.put("func", "getMeetingList");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("mahs", mahs.substring(5));

        params.add(param0);
        params.add(param1);
        params.add(param2);

        JSONDownloader jsonDownloader = new JSONDownloader(CommonField.URL_SERVICES, params);
        jsonDownloader.execute();

        try {
            String data = jsonDownloader.get();

            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("meetings");
            int count = jsonArray.length();

            List<Meeting> meetingList = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Meeting meeting = new Meeting();
                meeting.setDate(object.getString("NgayHen"));
                meeting.setContent(object.getString("NDHen"));
                meeting.setNote(object.getString("GhiChu"));
                meeting.setDoctor(object.getString("NguoiHen"));
                meeting.setOffice(object.getString("ChucVuNguoiHen"));
                meeting.setPhone(object.getString("DienThoai"));

                meetingList.add(meeting);
            }

            iViewMeeting.showMeetingList(meetingList);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
