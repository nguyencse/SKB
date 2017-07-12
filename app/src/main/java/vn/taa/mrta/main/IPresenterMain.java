package vn.taa.mrta.main;

import android.content.Context;

/**
 * Created by Putin on 3/27/2017.
 */

public interface IPresenterMain {
    void getProfile(Context context);
    void getClinicInfo(Context context, String id);
}