package vn.taa.mrta.history.Treatment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.MenuItem;

import java.util.List;

import vn.taa.mrta.adapter.HistoryTreatmentAdapter;
import vn.taa.mrta.custom.TypefaceSpanCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.object.Treatment;
import vn.taa.mrta.R;

public class HistoryTreatmentActivity extends AppCompatActivity implements IViewHistoryTreatment {

    private Toolbar toolbar;
    private Typeface typeface;
    private TypefaceSpanCSE typefaceSpan;
    private RecyclerView recyclerViewHistory;

    private SharedPreferences sprfSKB;
    private PresenterHistoryTreatment presenterHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_treatment);

        initialize();
    }

    private void initialize() {
        sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar_history_treatment);
        recyclerViewHistory = (RecyclerView) findViewById(R.id.recyclerview_history_treatment);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_opensans_light) + ".ttf");
        typefaceSpan = new TypefaceSpanCSE(typeface);
        String toolbarTitle = getString(R.string.toolbar_title_history_treatment);
        SpannableString spannableStringToolbarTitle = new SpannableString(toolbarTitle);
        spannableStringToolbarTitle.setSpan(typefaceSpan, 0, toolbarTitle.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        toolbar.setTitle(spannableStringToolbarTitle);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenterHistory = new PresenterHistoryTreatment(this);
        presenterHistory.getHistoryTreatment(sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showHistory(List<Treatment> treatmentList) {
        LinearLayoutManager lnlManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        lnlManager.setStackFromEnd(true);
        recyclerViewHistory.setLayoutManager(lnlManager);

        HistoryTreatmentAdapter adapter = new HistoryTreatmentAdapter(this, treatmentList);
        recyclerViewHistory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
