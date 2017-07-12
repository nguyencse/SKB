package vn.taa.mrta.auth;

import android.content.Context;

/**
 * Created by Putin on 3/4/2017.
 */

public interface IPresenterAuth {
    void getHostInfo(Context context, String mahs);

    void login(Context context, String mahs);
}
