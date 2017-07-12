package vn.taa.mrta.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class ListEventCustomAdapter extends ArrayAdapter<EventCustom> {
    private Context context;
    private int resource;
    private List<EventCustom> objects;

    public ListEventCustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EventCustom> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        LinearLayout lnlEvent = (LinearLayout) convertView.findViewById(R.id.lnl_item_event);
        TextViewCSE txtEventName = (TextViewCSE) convertView.findViewById(R.id.txt_reminder_item_event_name);
        TextViewCSE txtEventTimeBegin = (TextViewCSE) convertView.findViewById(R.id.txt_reminder_event_item_time_begin);
        TextViewCSE txtEventStatus = (TextViewCSE) convertView.findViewById(R.id.txt_reminder_item_event_status);

        EventCustom eventCustom = objects.get(position);

        txtEventName.setText(eventCustom.getTitle());
        txtEventTimeBegin.setText(DateFormat.format("HH:mm", eventCustom.getTimeBegin()) + " - " + DateFormat.format("HH:mm", eventCustom.getTimeEnd()));

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        if (now.getTime() < eventCustom.getTimeBegin()) {
            txtEventStatus.setText(context.getString(R.string.txt_event_status_pending));
            txtEventStatus.setTextColor(ContextCompat.getColor(context, R.color.colorBlueSuperDark));
        } else if (now.getTime() > eventCustom.getTimeEnd()) {
            txtEventStatus.setText(context.getString(R.string.txt_event_status_happened));
            txtEventStatus.setTextColor(ContextCompat.getColor(context, R.color.colorBrownDark));
        } else {
            txtEventStatus.setText(context.getString(R.string.txt_event_status_happening));
            txtEventStatus.setTextColor(ContextCompat.getColor(context, R.color.colorVioletDark));
        }

        lnlEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri calendarUri = CalendarContract.CONTENT_URI
                        .buildUpon()
                        .appendPath("time")
                        .build();
                context.startActivity(new Intent(Intent.ACTION_VIEW, calendarUri));
            }
        });

        return convertView;
    }
}
