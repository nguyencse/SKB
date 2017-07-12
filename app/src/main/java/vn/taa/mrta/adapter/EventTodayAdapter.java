package vn.taa.mrta.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.taa.mrta.R;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.object.EventCustom;

/**
 * Created by Putin on 4/16/2017.
 */

public class EventTodayAdapter extends RecyclerView.Adapter<EventTodayAdapter.ViewHolder> {
    private Context context;
    private List<EventCustom> objects;

    public EventTodayAdapter(Context context, List<EventCustom> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_listview_event, parent, false);
        return new EventTodayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventCustom eventCustom = objects.get(position);

        holder.txtEventName.setText(eventCustom.getTitle());
        holder.txtEventTimeBegin.setText(DateFormat.format("HH:mm", eventCustom.getTimeBegin()) + " - " + DateFormat.format("HH:mm", eventCustom.getTimeEnd()));

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        if (now.getTime() < eventCustom.getTimeBegin()) {
            holder.txtEventStatus.setText(context.getString(R.string.txt_event_status_pending));
            holder.txtEventStatus.setTextColor(ContextCompat.getColor(context, R.color.colorBlueSuperDark));
        } else if (now.getTime() > eventCustom.getTimeEnd()) {
            holder.txtEventStatus.setText(context.getString(R.string.txt_event_status_happened));
            holder.txtEventStatus.setTextColor(ContextCompat.getColor(context, R.color.colorBrownDark));
        } else {
            holder.txtEventStatus.setText(context.getString(R.string.txt_event_status_happening));
            holder.txtEventStatus.setTextColor(ContextCompat.getColor(context, R.color.colorVioletDark));
        }

        holder.lnlEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri calendarUri = CalendarContract.CONTENT_URI
                        .buildUpon()
                        .appendPath("time")
                        .build();
                context.startActivity(new Intent(Intent.ACTION_VIEW, calendarUri));
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lnlEvent;
        public TextViewCSE txtEventName;
        public TextViewCSE txtEventTimeBegin;
        public TextViewCSE txtEventStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlEvent = (LinearLayout) itemView.findViewById(R.id.lnl_item_event);
            txtEventName = (TextViewCSE) itemView.findViewById(R.id.txt_reminder_item_event_name);
            txtEventTimeBegin = (TextViewCSE) itemView.findViewById(R.id.txt_reminder_event_item_time_begin);
            txtEventStatus = (TextViewCSE) itemView.findViewById(R.id.txt_reminder_item_event_status);
        }
    }
}
