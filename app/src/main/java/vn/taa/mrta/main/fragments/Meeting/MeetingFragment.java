package vn.taa.mrta.main.fragments.Meeting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import vn.taa.mrta.R;
import vn.taa.mrta.adapter.MeetingAdapter;
import vn.taa.mrta.calendar.CalendarHelper;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.general.CommonMethod;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.object.Meeting;

/**
 * Created by Putin on 5/3/2017.
 */

public class MeetingFragment extends Fragment implements IViewMeeting {
    private TextViewCSE txtLastMeetingDate, txtLastMeetingContent, txtLastMeetingNote,
            txtLastMeetingDoctor, txtLastMeetingOffice, txtLastMeetingPhone, txtLastMeetingStatus, txtMeetingListTotal;
    private RecyclerView rcvMeetingList;
    private MeetingAdapter meetingAdapter;
    private Hashtable<String, String> calendarIdTable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);

        txtLastMeetingDate = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_date);
        txtLastMeetingContent = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_content);
        txtLastMeetingNote = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_note);
        txtLastMeetingDoctor = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_doctor);
        txtLastMeetingOffice = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_office);
        txtLastMeetingPhone = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_doctor_phone);
        txtLastMeetingStatus = (TextViewCSE) view.findViewById(R.id.txt_last_meeting_status);
        txtMeetingListTotal = (TextViewCSE) view.findViewById(R.id.txt_meeting_list_total);
        rcvMeetingList = (RecyclerView) view.findViewById(R.id.rcv_meeting_list);

        LinearLayoutManager lnlManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcvMeetingList.setLayoutManager(lnlManager);

        if (CalendarHelper.haveCalendarReadWritePermissions(getActivity())) {
            //Load calendars
            calendarIdTable = CalendarHelper.listCalendarId(getActivity());
        }

        PresenterMeeting presenterMeeting = new PresenterMeeting(this);
        presenterMeeting.getLastMeeting(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""));
        presenterMeeting.getMeetingList(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""));

        return view;
    }

    @Override
    public void showLastMeeting(Meeting lastMeeting) {
        txtLastMeetingDate.setText(lastMeeting.getDate().equals("null") ? "" : lastMeeting.getDate());
        txtLastMeetingContent.setText(lastMeeting.getContent().equals("null") ? "" : lastMeeting.getContent());
        txtLastMeetingNote.setText(lastMeeting.getNote().equals("null") ? "" : lastMeeting.getNote());
        txtLastMeetingDoctor.setText(lastMeeting.getDoctor().equals("null") ? "" : lastMeeting.getDoctor());
        txtLastMeetingOffice.setText(lastMeeting.getOffice().equals("null") ? "" : lastMeeting.getOffice());
        txtLastMeetingPhone.setText(lastMeeting.getPhone().equals("null") ? "" : lastMeeting.getPhone());
        try {
            if (new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(lastMeeting.getDate()).getTime() < Calendar.getInstance().getTime().getTime()) {
                txtLastMeetingStatus.setText(getString(R.string.txt_event_status_happened));
                txtLastMeetingStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBrownDark));
            } else {
                txtLastMeetingStatus.setText(getString(R.string.txt_event_status_pending));
                txtLastMeetingStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlueSuperDark));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String description = getString(R.string.txt_last_meeting_date_title) + " " + lastMeeting.getDate() + "\n"
                + getString(R.string.txt_last_meeting_content_title) + " " + lastMeeting.getContent() + "\n"
                + getString(R.string.txt_last_meeting_note_title) + " " + lastMeeting.getNote() + "\n"
                + getString(R.string.txt_last_meeting_doctor_title) + " " + lastMeeting.getDoctor() + "\n"
                + getString(R.string.txt_last_meeting_office_title) + " " + lastMeeting.getDoctor() + "\n"
                + getString(R.string.txt_last_meeting_doctor_phone_title) + " " + lastMeeting.getPhone();

        try {
            Date date = new SimpleDateFormat("dd-MM-yyy HH:mm").parse(lastMeeting.getDate());
            long timeBegin = date.getTime();

            Log.e("CSE_TIME_MEETING", timeBegin + "");

            Log.e("CSE_TIME_NOW", Calendar.getInstance().getTime().getTime() + "");

            if (!CommonMethod.hasOnDate(getContext(), getString(R.string.meeting_scheduler), date)) {
                addNewEvent(getString(R.string.meeting_scheduler), description, timeBegin, timeBegin + 1000 * 3600);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMeetingList(List<Meeting> meetingList) {
        txtMeetingListTotal.setText(meetingList.size() + "");

        meetingAdapter = new MeetingAdapter(getContext(), meetingList);
        rcvMeetingList.setAdapter(meetingAdapter);
        meetingAdapter.notifyDataSetChanged();
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
}
