package vn.taa.mrta.main.fragments.Dashboard;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import vn.taa.mrta.R;
import vn.taa.mrta.adapter.DrugListAdapter;
import vn.taa.mrta.adapter.EventTodayAdapter;
import vn.taa.mrta.calendar.CalendarHelper;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.general.CommonMethod;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.object.Dashboard;
import vn.taa.mrta.object.Drug;
import vn.taa.mrta.object.EventCustom;

/**
 * Created by Putin on 4/3/2017.
 */

public class DashboardFragment extends Fragment implements IViewDashboard, View.OnClickListener {
    private TextViewCSE txtOpenInCalendarApp;
    private RecyclerView rcvDrugListMorning;
    private RecyclerView rcvDrugListNoon;
    private RecyclerView rcvDrugListAfternoon;
    private RecyclerView rcvDrugListEvening;
    private TextViewCSE txtDrugListMorningTitle;
    private TextViewCSE txtDrugListNoonTitle;
    private TextViewCSE txtDrugListAfternoonTitle;
    private TextViewCSE txtDrugListEveningTitle;
    private boolean hasNotification = false;
    private RecyclerView rcvEventToday;
    private FloatingActionButton fabAddNewEvent;
    private SwipeRefreshLayout srlDashboard;
    private SwitchCompat swRemindDrinkDrug;
    private Hashtable<String, String> calendarIdTable;

    private PresenterDashboard presenterDashboard;
    private Dashboard dashboard;

    private List<EventCustom> eventCustomList;
    private EventTodayAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        txtOpenInCalendarApp = (TextViewCSE) view.findViewById(R.id.txt_open_in_calendar_app);
        fabAddNewEvent = (FloatingActionButton) view.findViewById(R.id.fab_dashboad_add_new_event);
        srlDashboard = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout_dashboard);
        swRemindDrinkDrug = (SwitchCompat) view.findViewById(R.id.sw_reminder_drink_drug);

        rcvEventToday = (RecyclerView) view.findViewById(R.id.scv_dashboard_event_list);

        txtDrugListMorningTitle = (TextViewCSE) view.findViewById(R.id.txt_dashboard_druglist_morning_title);
        txtDrugListNoonTitle = (TextViewCSE) view.findViewById(R.id.txt_dashboard_druglist_noon_title);
        txtDrugListAfternoonTitle = (TextViewCSE) view.findViewById(R.id.txt_dashboard_druglist_afternoon_title);
        txtDrugListEveningTitle = (TextViewCSE) view.findViewById(R.id.txt_dashboard_druglist_evening_title);

        rcvDrugListMorning = (RecyclerView) view.findViewById(R.id.recyclerview_dashboard_morning);
        rcvDrugListNoon = (RecyclerView) view.findViewById(R.id.recyclerview_dashboard_noon);
        rcvDrugListAfternoon = (RecyclerView) view.findViewById(R.id.recyclerview_dashboard_afternoon);
        rcvDrugListEvening = (RecyclerView) view.findViewById(R.id.recyclerview_dashboard_evening);

        LinearLayoutManager lnlDrugListMorning = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager lnlDrugListNoon = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager lnlDrugListAfternoon = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager lnlDrugListEvening = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rcvDrugListMorning.setLayoutManager(lnlDrugListMorning);
        rcvDrugListNoon.setLayoutManager(lnlDrugListNoon);
        rcvDrugListAfternoon.setLayoutManager(lnlDrugListAfternoon);
        rcvDrugListEvening.setLayoutManager(lnlDrugListEvening);

        presenterDashboard = new PresenterDashboard(this);

        eventCustomList = new ArrayList<>();
        eventAdapter = new EventTodayAdapter(getContext(), eventCustomList);
        LinearLayoutManager lnlManagerEventList = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvEventToday.setLayoutManager(lnlManagerEventList);
        rcvEventToday.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();

        String dashboardDrugListJSON = MainActivity.sprfSKB.getString(CommonField.FRAGMENT_DASHBOARD_DRUG_LIST, null);
        if (dashboardDrugListJSON != null) {
            showDashboard(new Gson().fromJson(dashboardDrugListJSON, Dashboard.class));
        } else {
            presenterDashboard.getDashboard(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(5));
        }

        presenterDashboard.getDashboard(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(5));

        srlDashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenterDashboard.getDashboard(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "").substring(5));
                srlDashboard.setRefreshing(false);
            }
        });

        if (CalendarHelper.haveCalendarReadWritePermissions(getActivity())) {
            //Load calendars
            calendarIdTable = CalendarHelper.listCalendarId(getActivity());
        }

        txtOpenInCalendarApp.setOnClickListener(this);
        fabAddNewEvent.setOnClickListener(this);

        swRemindDrinkDrug.setChecked(MainActivity.sprfSKB.getBoolean(CommonField.IS_CONTINUE_REMIND_DRINK_DRUG, true));

        if (swRemindDrinkDrug.isChecked() && !CommonMethod.hasToday(getContext(), getString(R.string.drink_drug_at))) {
            addRemindToday();
        }
        showEventToday();

        swRemindDrinkDrug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && !CommonMethod.hasToday(getContext(), getString(R.string.drink_drug_at))) {
                    addRemindToday();
                } else {
                    deleteReminderToday();
                }
                showEventToday();

                MainActivity.sprfSKB.edit().putBoolean(CommonField.IS_CONTINUE_REMIND_DRINK_DRUG, isChecked).apply();
            }
        });
        return view;
    }

    private void addRemindToday() {
        if (dashboard != null) {
            List<Drug> morningDrugList = dashboard.getDrugListMorning();
            List<Drug> noonDrugList = dashboard.getDrugListNoon();
            List<Drug> afternoonDrugList = dashboard.getDrugListAfternoon();
            List<Drug> eveningDrugList = dashboard.getDrugListEvening();

            String descriptionMorning = "";
            String descriptionNoon = "";
            String descriptionAfternoon = "";
            String descriptionEvening = "";

            for (int i = 0; i < morningDrugList.size(); i++) {
                descriptionMorning += i + "." + morningDrugList.get(i).getName() + "\n";
            }
            for (int i = 0; i < noonDrugList.size(); i++) {
                descriptionNoon += i + "." + noonDrugList.get(i).getName() + "\n";
            }
            for (int i = 0; i < afternoonDrugList.size(); i++) {
                descriptionAfternoon += i + "." + afternoonDrugList.get(i).getName() + "\n";
            }
            for (int i = 0; i < eveningDrugList.size(); i++) {
                descriptionEvening += i + "." + eveningDrugList.get(i).getName() + "\n";
            }

            if (morningDrugList.size() > 0) {
                addEventInDay(6, 30, 10, 30, getString(R.string.drink_drug_morning), descriptionMorning);
            }
            if (noonDrugList.size() > 0) {
                addEventInDay(11, 0, 14, 0, getString(R.string.drink_drug_noon), descriptionNoon);
            }
            if (afternoonDrugList.size() > 0) {
                addEventInDay(14, 30, 16, 0, getString(R.string.drink_drug_afternoon), descriptionAfternoon);
            }
            if (eveningDrugList.size() > 0) {
                addEventInDay(18, 30, 21, 30, getString(R.string.drink_drug_evening), descriptionEvening);
            }
        }
    }

    private void addEventInDay(int hourBegin, int minuteBegin, int hourEnd, int minuteEnd, String title, String description) {
        Calendar beginTime1 = Calendar.getInstance();
        beginTime1.set(Calendar.HOUR_OF_DAY, hourBegin);
        beginTime1.set(Calendar.MINUTE, minuteBegin);
        Calendar endTime1 = Calendar.getInstance();
        endTime1.set(Calendar.HOUR_OF_DAY, hourEnd);
        endTime1.set(Calendar.MINUTE, minuteEnd);
        addNewEvent(title, description, beginTime1.getTimeInMillis(), endTime1.getTimeInMillis());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.txt_open_in_calendar_app:
                CommonMethod.openInCalendar(getContext());
                break;
            case R.id.fab_dashboad_add_new_event:
                CommonMethod.openInCalendar(getContext());
                break;
        }
    }

    @Override
    public void showDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;

        if(this.dashboard != null){
            List<Drug> morningDrugList = dashboard.getDrugListMorning();
            List<Drug> noonDrugList = dashboard.getDrugListNoon();
            List<Drug> afternoonDrugList = dashboard.getDrugListAfternoon();
            List<Drug> eveningDrugList = dashboard.getDrugListEvening();

            if (MainActivity.sprfSKB.getString(CommonField.FRAGMENT_DASHBOARD_DRUG_LIST, null) == null) {
                MainActivity.sprfSKB.edit().putString(CommonField.FRAGMENT_DASHBOARD_DRUG_LIST, new Gson().toJson(dashboard)).apply();
            }

            if (morningDrugList.size() != 0) {
                rcvDrugListMorning.setVisibility(View.VISIBLE);
                txtDrugListMorningTitle.setVisibility(View.VISIBLE);
                DrugListAdapter adapterDrugListMorning = new DrugListAdapter(getContext(), morningDrugList);
                rcvDrugListMorning.setAdapter(adapterDrugListMorning);
                adapterDrugListMorning.notifyDataSetChanged();
            } else {
                rcvDrugListMorning.setVisibility(View.GONE);
                txtDrugListMorningTitle.setVisibility(View.GONE);
            }

            if (noonDrugList.size() != 0) {
                rcvDrugListNoon.setVisibility(View.VISIBLE);
                txtDrugListNoonTitle.setVisibility(View.VISIBLE);
                DrugListAdapter adapterDrugListNoon = new DrugListAdapter(getContext(), noonDrugList);
                rcvDrugListNoon.setAdapter(adapterDrugListNoon);
                adapterDrugListNoon.notifyDataSetChanged();
            } else {
                rcvDrugListNoon.setVisibility(View.GONE);
                txtDrugListNoonTitle.setVisibility(View.GONE);
            }

            if (afternoonDrugList.size() != 0) {
                rcvDrugListAfternoon.setVisibility(View.VISIBLE);
                txtDrugListAfternoonTitle.setVisibility(View.VISIBLE);
                DrugListAdapter adapterDrugListAfternoon = new DrugListAdapter(getContext(), afternoonDrugList);
                rcvDrugListAfternoon.setAdapter(adapterDrugListAfternoon);
                adapterDrugListAfternoon.notifyDataSetChanged();
            } else {
                rcvDrugListAfternoon.setVisibility(View.GONE);
                txtDrugListAfternoonTitle.setVisibility(View.GONE);
            }

            if (eveningDrugList.size() != 0) {
                rcvDrugListEvening.setVisibility(View.VISIBLE);
                txtDrugListEveningTitle.setVisibility(View.VISIBLE);
                DrugListAdapter adapterDrugListEvening = new DrugListAdapter(getContext(), eveningDrugList);
                rcvDrugListEvening.setAdapter(adapterDrugListEvening);
                adapterDrugListEvening.notifyDataSetChanged();
            } else {
                rcvDrugListEvening.setVisibility(View.GONE);
                txtDrugListEveningTitle.setVisibility(View.GONE);
            }
        }
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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = getContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);
        eventCustomList.clear();

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                eventCustomList.add(new EventCustom(cursor.getString(1), cursor.getLong(3), cursor.getLong(4)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        eventAdapter.notifyDataSetChanged();

        hasNotification = (eventCustomList.size() != 0);
        CommonMethod.invalidateNotificationStatus(getActivity(), hasNotification);
    }

    private void deleteReminderToday() {
        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};

        Calendar startTime = Calendar.getInstance();

        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.DATE, 1);

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ) AND ( deleted != 1 ))";
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                if (cursor.getString(2).contains(getString(R.string.drink_drug_at))) {
                    resolver.delete(ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, cursor.getLong(1)), null, null);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        eventAdapter.notifyDataSetChanged();

        hasNotification = (eventCustomList.size() != 0);
        CommonMethod.invalidateNotificationStatus(getActivity(), hasNotification);

        if (eventCustomList.size() == 0) {
            MainActivity.sprfSKB.edit().putBoolean(CommonField.IS_CONTINUE_REMIND_DRINK_DRUG, false).apply();
        }
    }

    private void addNewEvent(String title, String description, long timeBegin, long timeEnd) {
        if (calendarIdTable == null) {
            //Load calendars
            calendarIdTable = CalendarHelper.listCalendarId(getActivity());
            return;
        }

        List<String> list = new ArrayList<>();

        Enumeration e = calendarIdTable.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            list.add(key);
        }

        String calendarString = list.get(0);

        int calendar_id = Integer.parseInt(calendarIdTable.get(calendarString));

        CalendarHelper.MakeNewCalendarEntry(getActivity(), title, description, "Ho Chi Minh City", timeBegin, timeEnd, false, true, calendar_id, 3);
    }

    @Override
    public void onResume() {
        super.onResume();
        showEventToday();
    }
}