package vn.taa.mrta.calendar;

/**
 * Created by Putin on 4/16/2017.
 */

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    private static final String TAG = "OnItemSelectedListener";

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        /*Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Log.v(TAG, "onNohingSelected() called.");
    }
}
