package vn.taa.mrta.main.fragments.Meeting;

import java.util.List;

import vn.taa.mrta.object.Meeting;

/**
 * Created by Putin on 5/3/2017.
 */

public interface IViewMeeting {
    void showLastMeeting(Meeting lastMeeting);

    void showMeetingList(List<Meeting> meetingList);
}
