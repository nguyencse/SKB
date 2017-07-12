package vn.taa.mrta.manual.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import vn.taa.mrta.R;
import vn.taa.mrta.custom.EditTextCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.manual.ManualActivity;
import vn.taa.mrta.object.UserInfo;

/**
 * Created by Putin on 5/8/2017.
 */

public class EnterProfileManualyFragment extends Fragment implements View.OnClickListener {
    private EditTextCSE edtManualUserName;
    private EditTextCSE edtManualUserAge;
    private EditTextCSE edtManualUserGender;
    private EditTextCSE edtManualUserAddress;
    private EditTextCSE edtManualUserDiagnose;
    private EditTextCSE edtManualUserNote;
    private LinearLayout lnlManualUserSaveInfo;
    private SharedPreferences sprfSKB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_profile_manual, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        sprfSKB = getContext().getSharedPreferences(CommonField.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        edtManualUserName = (EditTextCSE) view.findViewById(R.id.edt_enter_profile_user_name);
        edtManualUserAge = (EditTextCSE) view.findViewById(R.id.edt_enter_profile_user_age);
        edtManualUserGender = (EditTextCSE) view.findViewById(R.id.edt_enter_profile_user_gender);
        edtManualUserAddress = (EditTextCSE) view.findViewById(R.id.edt_enter_profile_user_address);
        edtManualUserDiagnose = (EditTextCSE) view.findViewById(R.id.edt_enter_profile_user_diagnose);
        edtManualUserNote = (EditTextCSE) view.findViewById(R.id.edt_enter_profile_user_note);
//        lnlManualUserSaveInfo = (LinearLayout) view.findViewById(R.id.lnl_enter_profile_save_manual);

        if (!ManualActivity.sprfSKB.getString(CommonField.USER_INFO, "").equals("")) {
            setUserInfo();
            setEnableViews(false);
        }

//        lnlManualUserSaveInfo.setOnClickListener(this);
    }

    private void setEnableViews(boolean b) {
        edtManualUserName.setEnabled(b);
        edtManualUserAge.setEnabled(b);
        edtManualUserGender.setEnabled(b);
        edtManualUserAddress.setEnabled(b);
        edtManualUserDiagnose.setEnabled(b);
        edtManualUserNote.setEnabled(b);


//        if (b) {
//            lnlManualUserSaveInfo.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//        } else {
//            lnlManualUserSaveInfo.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGrayDark));
//        }
    }

    private void setUserInfo() {
        String userInfoJson = sprfSKB.getString(CommonField.USER_INFO, "");
        if (!userInfoJson.equals("")) {
            UserInfo userInfo = new Gson().fromJson(userInfoJson, UserInfo.class);
            edtManualUserName.setText(userInfo.getName());
            edtManualUserAge.setText(userInfo.getAge());
            edtManualUserGender.setText(userInfo.getGender());
            edtManualUserAddress.setText(userInfo.getAddress());
            edtManualUserDiagnose.setText(userInfo.getDiagnose());
            edtManualUserNote.setText(userInfo.getNote());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

//        switch (id) {
//            case R.id.lnl_enter_profile_save_manual:
//                if (edtManualUserName.getText().toString().equals("")) {
//                    Toast.makeText(getContext(), getString(R.string.username_must_not_be_empty), Toast.LENGTH_SHORT).show();
//                } else if (Integer.parseInt(edtManualUserAge.getText().toString()) < 0) {
//                    Toast.makeText(getContext(), getString(R.string.age_must_not_less_than_0), Toast.LENGTH_SHORT).show();
//                } else {
//                    //TODO: SAVE USER INFO INTO SHAREPREFERENCES
//                    UserInfo userInfo = new UserInfo();
//                    userInfo.setName(edtManualUserName.getText().toString());
//                    userInfo.setGender(edtManualUserGender.getText().toString());
//                    userInfo.setAddress(edtManualUserAddress.getText().toString());
//                    userInfo.setDiagnose(edtManualUserDiagnose.getText().toString());
//                    userInfo.setNote(edtManualUserNote.getText().toString());
//
//                    sprfSKB.edit().putString(CommonField.USER_INFO, new Gson().toJson(userInfo)).apply();
//
//                    setEnableViews(false);
//
//                    ManualActivity.viewPagerManual.setCurrentItem(CommonField.FRAGMENT_MANUAL_PRESCRIPTION);
//                }
//                break;
//        }
    }
}
