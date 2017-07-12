package vn.taa.mrta.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import vn.taa.mrta.R;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.object.Meeting;

/**
 * Created by Putin on 5/3/2017.
 */

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {
    private Context context;
    private List<Meeting> meetingList;

    public MeetingAdapter(Context context, List<Meeting> meetingList) {
        this.context = context;
        this.meetingList = meetingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_meeting, parent, false);
        return new MeetingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Meeting meeting = meetingList.get(position);
        holder.txtDate.setText(meeting.getDate());
        holder.txtDoctor.setText(meeting.getDoctor());

        try {
            if (new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(meeting.getDate()).getTime() < Calendar.getInstance().getTime().getTime()) {
//                holder.lnlItemMeeting.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrayLite));
                holder.txtStatus.setText(context.getString(R.string.txt_event_status_happened));
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorBrownDark));
            } else {
//                holder.lnlItemMeeting.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
                holder.txtStatus.setText(context.getString(R.string.txt_event_status_pending));
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorBlueSuperDark));
            }

            holder.lnlItemMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_layout_modal_meeting_detail);

                    TextViewCSE txtDate = (TextViewCSE) dialog.findViewById(R.id.txt_item_modal_meeting_date);
                    TextViewCSE txtContent = (TextViewCSE) dialog.findViewById(R.id.txt_item_modal_meeting_content);
                    TextViewCSE txtNote = (TextViewCSE) dialog.findViewById(R.id.txt_item_modal_meeting_note);
                    TextViewCSE txtDoctor = (TextViewCSE) dialog.findViewById(R.id.txt_item_modal_meeting_doctor);
                    TextViewCSE txtOffice = (TextViewCSE) dialog.findViewById(R.id.txt_item_modal_meeting_office);
                    TextViewCSE txtPhone = (TextViewCSE) dialog.findViewById(R.id.txt_item_modal_meeting_doctor_phone);

                    txtDate.setText(meeting.getDate().equals("null") ? "" : meeting.getDate());
                    txtContent.setText(meeting.getContent().equals("null") ? "" : meeting.getContent());
                    txtNote.setText(meeting.getNote().equals("null") ? "" : meeting.getNote());
                    txtDoctor.setText(meeting.getDoctor().equals("null") ? "" : meeting.getDoctor());
                    txtOffice.setText(meeting.getOffice().equals("null") ? "" : meeting.getOffice());
                    txtPhone.setText(meeting.getPhone().equals("null") ? "" : meeting.getPhone());

                    dialog.show();

                    Window window = dialog.getWindow();
                    if (window != null) {
                        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextViewCSE txtDate;
        public TextViewCSE txtDoctor;
        public TextViewCSE txtStatus;
        public LinearLayout lnlItemMeeting;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlItemMeeting = (LinearLayout) itemView.findViewById(R.id.lnl_item_meeting);
            txtDate = (TextViewCSE) itemView.findViewById(R.id.txt_meeting_date);
            txtDoctor = (TextViewCSE) itemView.findViewById(R.id.txt_meeting_doctor);
            txtStatus = (TextViewCSE) itemView.findViewById(R.id.txt_item_meeting_status);

        }
    }
}
