package vn.taa.mrta.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.taa.mrta.adapter.ListEventCustomAdapter;
import vn.taa.mrta.adapter.MainViewPagerAdapter;
import vn.taa.mrta.calendar.CalendarHelper;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.general.CommonMethod;
import vn.taa.mrta.main.fragments.Dashboard.DashboardFragment;
import vn.taa.mrta.main.fragments.LastTreatment.LastTreatmentFragment;
import vn.taa.mrta.main.fragments.Meeting.MeetingFragment;
import vn.taa.mrta.main.fragments.Prescription.PrescriptionFragment;
import vn.taa.mrta.R;
import vn.taa.mrta.object.Clinic;
import vn.taa.mrta.object.EventCustom;
import vn.taa.mrta.object.Patient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IViewMain {
    public static SharedPreferences sprfSKB;
    private TabLayout tabLayout;
    private DrawerLayout drlMain;
    private LinearLayout lnlDashboard;
    private LinearLayout lnlNotification;
    private ImageButton btnNavbar;
    private ImageButton btnNotification;
    private TextViewCSE txtScreenTitle;
    private ViewPager viewPager;

    private TextViewCSE txtProfileName;
    private TextViewCSE txtProfileGender;
    private TextViewCSE txtProfileBirthDay;
    private TextViewCSE txtProfileAddress;
    private TextViewCSE txtProfilePhone;

    private TextViewCSE txtClinicNameUnit;
    private TextViewCSE txtClinicNameClinic;
    private TextViewCSE txtClinicAddress;
    private TextViewCSE txtClinicPhone;
    private TextViewCSE txtClinicEmail;
    private TextViewCSE txtClinicFax;

    private ListView lvEventToday;
    private PresenterMain presenterMain;
    private boolean hasNotification = false;

    private List<EventCustom> eventCustomList;
    private ListEventCustomAdapter eventCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!CalendarHelper.haveCalendarReadWritePermissions(MainActivity.this)) {
            CalendarHelper.requestCalendarReadWritePermission(MainActivity.this);
        } else {
            initialize();
            showEventToday();
        }
    }

    private void initialize() {
        sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);
        drlMain = (DrawerLayout) findViewById(R.id.activity_main);
        lnlDashboard = (LinearLayout) findViewById(R.id.custom_layout_dashboard);
        lnlNotification = (LinearLayout) findViewById(R.id.custom_layout_notification);
        txtScreenTitle = (TextViewCSE) findViewById(R.id.txt_screen_title);
        btnNavbar = (ImageButton) findViewById(R.id.btn_navbar);
        btnNotification = (ImageButton) findViewById(R.id.btn_notification);
        tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setOffscreenPageLimit(CommonField.FRAGMENT_COUNT);

        txtProfileName = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_name);
        txtProfileGender = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_gender);
        txtProfileBirthDay = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_birthday);
        txtProfileAddress = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_address);
        txtProfilePhone = (TextViewCSE) findViewById(R.id.txt_dashboard_profile_phone);

        txtClinicNameUnit = (TextViewCSE) findViewById(R.id.txt_dashboard_clinic_name_unit);
        txtClinicNameClinic = (TextViewCSE) findViewById(R.id.txt_dashboard_clinic_name_clinic);
        txtClinicAddress = (TextViewCSE) findViewById(R.id.txt_dashboard_clinic_address);
        txtClinicPhone = (TextViewCSE) findViewById(R.id.txt_dashboard_clinic_phone);
        txtClinicEmail = (TextViewCSE) findViewById(R.id.txt_dashboard_clinic_email);
        txtClinicFax = (TextViewCSE) findViewById(R.id.txt_dashboard_clinic_fax);

        lvEventToday = (ListView) findViewById(R.id.listview_notification_event_today);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        if (!hasNotification) {
            drlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, lnlNotification);
        }

        btnNavbar.setOnClickListener(this);
        btnNotification.setOnClickListener(this);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posTab = tab.getPosition();

                switch (posTab) {
                    case 0:
                        txtScreenTitle.setText(getString(R.string.txt_title_screen_dashboard));
                        break;
                    case 1:
                        txtScreenTitle.setText(getString(R.string.txt_title_screen_last_treatment));
                        break;
                    case 2:
                        txtScreenTitle.setText(getString(R.string.txt_title_screen_last_prescription));
                        break;
                    case 3:
                        txtScreenTitle.setText(getString(R.string.txt_title_screen_meeting));
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

        txtScreenTitle.setText(getString(R.string.txt_title_screen_dashboard));

        presenterMain = new PresenterMain(this);
        presenterMain.getClinicInfo(this, sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(0, CommonField.CLINIC_ID_SIZE));

        String patientInfoJSON = sprfSKB.getString(CommonField.PATIENT_INFO_DETAIL, null);
        if (patientInfoJSON != null) {
            Patient patient = new Gson().fromJson(patientInfoJSON, Patient.class);
            showProfile(patient);
        } else {
            presenterMain.getProfile(this);
        }

        eventCustomList = new ArrayList<>();
        eventCustomAdapter = new ListEventCustomAdapter(this, R.layout.custom_item_listview_event, eventCustomList);
        lvEventToday.setAdapter(eventCustomAdapter);
        eventCustomAdapter.notifyDataSetChanged();
    }

    private void setupTabIcons() {
        // TODO: setup tab with icon
        tabLayout.getTabAt(CommonField.FRAGMENT_DASHBOARD).setIcon(R.drawable.ic_dashboard_white);
        tabLayout.getTabAt(CommonField.FRAGMENT_TREATMENT).setIcon(R.drawable.ic_home_white);
        tabLayout.getTabAt(CommonField.FRAGMENT_PRESCRIPTION).setIcon(R.drawable.ic_prescription_white);
        tabLayout.getTabAt(CommonField.FRAGMENT_MEETING).setIcon(R.drawable.ic_scheduler_white);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment(), getString(R.string.txt_tab_dashboard));
        adapter.addFragment(new LastTreatmentFragment(), getString(R.string.txt_tab_treatment_history));
        adapter.addFragment(new PrescriptionFragment(), getString(R.string.txt_tab_prescription));
        adapter.addFragment(new MeetingFragment(), getString(R.string.txt_tab_meeting));
        viewPager.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_navbar:
                drlMain.openDrawer(lnlDashboard);
                break;
            case R.id.btn_notification:
                if (hasNotification || CommonMethod.hasToday(this, getString(R.string.drink_drug_at))) {
                    drlMain.openDrawer(lnlNotification);
                    showEventToday();
                } else {
                    Toast.makeText(this, getString(R.string.message_no_notification_today), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showProfile(Patient patient) {
        txtProfileName.setText(patient.getName());
        txtProfileGender.setText(patient.getGender());
        txtProfileBirthDay.setText(patient.getBirthday());
        txtProfileAddress.setText(patient.getAddress());
        txtProfilePhone.setText(patient.getPhone());

        if (sprfSKB.getString(CommonField.PATIENT_INFO_DETAIL, null) == null) {
            String patientInfoJSON = new Gson().toJson(patient);
            sprfSKB.edit().putString(CommonField.PATIENT_INFO_DETAIL, patientInfoJSON).apply();
        }
    }

    @Override
    public void showClinicInfo(Clinic clinic) {
        txtClinicNameUnit.setText(clinic.getNameUnit());
        txtClinicNameClinic.setText(clinic.getNameClinic());
        txtClinicAddress.setText(clinic.getAddress());
        txtClinicPhone.setText(clinic.getPhone());
        txtClinicEmail.setText(clinic.getEmail());
        txtClinicFax.setText(clinic.getFax());
    }

    public void showEventToday() {
        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION};

        Calendar startTime = Calendar.getInstance();

        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.DATE, 1);

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ) AND ( deleted != 1 ))";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

        eventCustomList.clear();
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                eventCustomList.add(new EventCustom(cursor.getString(1), cursor.getLong(3), cursor.getLong(4)));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        eventCustomAdapter.notifyDataSetChanged();

        hasNotification = (eventCustomList.size() != 0);
        CommonMethod.invalidateNotificationStatus(this, hasNotification);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CalendarHelper.CALENDARHELPER_PERMISSION_REQUEST_CODE) {
            initialize();

            if (CalendarHelper.haveCalendarReadWritePermissions(MainActivity.this) && CommonMethod.hasToday(this, getString(R.string.drink_drug_at))) {
                CommonMethod.invalidateNotificationStatus(this, true);
                showEventToday();
            }
        }
    }
}