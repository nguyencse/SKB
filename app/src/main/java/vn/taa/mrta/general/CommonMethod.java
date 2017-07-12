package vn.taa.mrta.general;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import vn.taa.mrta.R;

import static vn.taa.mrta.main.MainActivity.sprfSKB;

/**
 * Created by Putin on 3/28/2017.
 */

public class CommonMethod {
    public static void putParamsToHost(List<HashMap<String, String>> params) {
        HashMap<String, String> param0 = new HashMap<>();
        param0.put(CommonField.SQL_name, sprfSKB.getString(CommonField.SQL_name, ""));

        HashMap<String, String> param1 = new HashMap<>();
        param1.put(CommonField.SQL_IP, sprfSKB.getString(CommonField.SQL_IP, ""));

        HashMap<String, String> param2 = new HashMap<>();
        param2.put(CommonField.View_user, sprfSKB.getString(CommonField.View_user, ""));

        HashMap<String, String> param3 = new HashMap<>();
        param3.put(CommonField.View_pass, sprfSKB.getString(CommonField.View_pass, ""));

        params.add(param0);
        params.add(param1);
        params.add(param2);
        params.add(param3);
    }

    public static void invalidateNotificationStatus(Activity activity, boolean hasNotification) {
        ImageButton btnNotification = (ImageButton) activity.findViewById(R.id.btn_notification);

        if (hasNotification) {
            btnNotification.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_notification_red));
        } else {
            btnNotification.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_notification));
        }
    }

    public static boolean hasToday(Context context, String key) {
        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};

        Calendar startTime = Calendar.getInstance();

        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.DATE, 1);

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ) AND ( deleted != 1 ))";
        ContentResolver resolver = context.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        Cursor cursor = resolver.query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                if (cursor.getString(2).contains(key)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return false;
    }

    public static boolean hasOnDate(Context context, String key, Date date) {
        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};

        Date endDate = new Date(date.getTime());
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + date.getTime() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endDate.getTime() + " ) AND ( deleted != 1 ))";
        ContentResolver resolver = context.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        Cursor cursor = resolver.query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                if (cursor.getString(2).contains(key)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return false;
    }

    public static void openInCalendar(Context context) {
        Uri calendarUri = CalendarContract.CONTENT_URI
                .buildUpon()
                .appendPath("time")
                .build();
        context.startActivity(new Intent(Intent.ACTION_VIEW, calendarUri));
    }
}