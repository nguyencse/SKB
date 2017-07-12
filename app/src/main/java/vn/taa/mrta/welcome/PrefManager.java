package vn.taa.mrta.welcome;

import android.content.Context;
import android.content.SharedPreferences;

import vn.taa.mrta.general.CommonField;

/**
 * Created by Putin on 4/17/2017.
 */

public class PrefManager {
    private SharedPreferences sprfSKB;
    private SharedPreferences.Editor editor;
    private Context context;

    public PrefManager(Context context) {
        this.context = context;
        sprfSKB = this.context.getSharedPreferences(CommonField.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sprfSKB.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(CommonField.IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    public boolean isFirstTimeLaunch() {
        return sprfSKB.getBoolean(CommonField.IS_FIRST_TIME_LAUNCH, true);
    }

    public void setHasMrTA(boolean hasMrTA) {
        editor.putBoolean(CommonField.HAS_MRTA, hasMrTA).apply();
    }

    public boolean hasMrTA() {
        return sprfSKB.getBoolean(CommonField.HAS_MRTA, true);
    }
}
