package vn.taa.mrta.manual;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import vn.taa.mrta.adapter.MainViewPagerAdapter;
import vn.taa.mrta.calendar.CalendarHelper;
import vn.taa.mrta.R;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.manual.fragments.dashboard.DashboardManualFragment;
import vn.taa.mrta.manual.fragments.prescription.EnterPrescriptionManualFragment;
import vn.taa.mrta.manual.fragments.profile.EnterProfileManualyFragment;
import vn.taa.mrta.object.UserInfo;

public class ManualActivity extends AppCompatActivity {
    public static SharedPreferences sprfSKB;
    public static ViewPager viewPagerManual;
    private TabLayout tabLayoutManual;
    private TextViewCSE txtScreenTitleManual;

    private TextViewCSE txtProfileManualName;
    private TextViewCSE txtProfileManualAge;
    private TextViewCSE txtProfileManualGender;
    private TextViewCSE txtProfileManualAddress;
    private TextViewCSE txtProfileManualDiagnose;
    private TextViewCSE txtProfileManualNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        initialize();

        if (!CalendarHelper.haveCalendarReadWritePermissions(ManualActivity.this)) {
            CalendarHelper.requestCalendarReadWritePermission(ManualActivity.this);
        }
    }

    private void initialize() {
        viewPagerManual = (ViewPager) findViewById(R.id.viewpager_main_manual);
        viewPagerManual.setOffscreenPageLimit(CommonField.FRAGMENT_COUNT_MANUAL);
        tabLayoutManual = (TabLayout) findViewById(R.id.tabs_main_manual);
        txtScreenTitleManual = (TextViewCSE) findViewById(R.id.txt_screen_title_manual);

        txtProfileManualName = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_name_manual);
        txtProfileManualAge = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_age_manual);
        txtProfileManualGender = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_gender_manual);
        txtProfileManualAddress = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_address_manual);
        txtProfileManualDiagnose = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_diagnose_manual);
        txtProfileManualNote = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_note_manual);

        sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);

        setupViewPager(viewPagerManual);
        tabLayoutManual.setupWithViewPager(viewPagerManual);
        setupTabIcons();

        setUpUserInfo();

        tabLayoutManual.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posTab = tab.getPosition();

                switch (posTab) {
                    case CommonField.FRAGMENT_MANUAL_DASHBOARD:
                        txtScreenTitleManual.setText(getString(R.string.txt_tab_dashboard));
                        break;
                    case CommonField.FRAGMENT_MANUAL_PROFILE:
                        txtScreenTitleManual.setText(getString(R.string.txt_tab_enter_profile_manual));
                        break;
                    case CommonField.FRAGMENT_MANUAL_PRESCRIPTION:
                        txtScreenTitleManual.setText(getString(R.string.txt_tab_enter_prescription_manual));
                        setUpUserInfo();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        txtScreenTitleManual.setText(getString(R.string.txt_title_screen_dashboard));
    }

    private void setupTabIcons() {
        // TODO: setup tab with icon
        tabLayoutManual.getTabAt(0).setIcon(R.drawable.ic_dashboard_white);
        tabLayoutManual.getTabAt(1).setIcon(R.drawable.ic_home_white);
        tabLayoutManual.getTabAt(2).setIcon(R.drawable.ic_home_white);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardManualFragment(), getString(R.string.txt_tab_dashboard));
        adapter.addFragment(new EnterProfileManualyFragment(), getString(R.string.txt_tab_enter_profile_manual));
        adapter.addFragment(new EnterPrescriptionManualFragment(), getString(R.string.txt_tab_enter_prescription_manual));
        viewPager.setAdapter(adapter);
    }

    private void setUpUserInfo() {
        String userInfoJson = sprfSKB.getString(CommonField.USER_INFO, "");
        if (!userInfoJson.equals("")) {
            UserInfo userInfo = new Gson().fromJson(userInfoJson, UserInfo.class);
            txtProfileManualName.setText(userInfo.getName());
            txtProfileManualAge.setText(userInfo.getAge());
            txtProfileManualGender.setText(userInfo.getGender());
            txtProfileManualAddress.setText(userInfo.getAddress());
            txtProfileManualDiagnose.setText(userInfo.getDiagnose());
            txtProfileManualNote.setText(userInfo.getNote());
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(getString(R.string.confirm_exit_app_content))
                .setPositiveButton(getString(R.string.message_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                    }
                })
                .setNegativeButton(getString(R.string.message_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}