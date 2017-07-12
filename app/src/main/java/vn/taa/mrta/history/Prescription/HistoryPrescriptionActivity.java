package vn.taa.mrta.history.Prescription;

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

import vn.taa.mrta.adapter.HistoryPrescriptionAdapter;
import vn.taa.mrta.custom.TypefaceSpanCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.object.PrescriptionInfo;
import vn.taa.mrta.R;

public class HistoryPrescriptionActivity extends AppCompatActivity implements IViewHistoryPrescription {

    private Toolbar toolbar;
    private Typeface typeface;
    private TypefaceSpanCSE typefaceSpan;
    private RecyclerView recyclerView;
    private SharedPreferences sprfSKB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_prescription);

        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_history_prescription);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_history_prescription);

        toolbar.setTitle(getString(R.string.toolbar_title_history_prescription));
        typeface = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_opensans_light) + ".ttf");
        typefaceSpan = new TypefaceSpanCSE(typeface);
        String toolbarTitle = getString(R.string.toolbar_title_history_prescription);
        SpannableString spannableStringToolbarTitle = new SpannableString(toolbarTitle);
        spannableStringToolbarTitle.setSpan(typefaceSpan, 0, toolbarTitle.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        toolbar.setTitle(spannableStringToolbarTitle);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);
        PresenterHistoryPrescription historyPrescription = new PresenterHistoryPrescription(this);
        historyPrescription.getHistoryPrescription(sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""));
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
    public void showListHistoryPrescription(List<PrescriptionInfo> prescriptionInfoList) {
        HistoryPrescriptionAdapter adapter = new HistoryPrescriptionAdapter(this, prescriptionInfoList);

        LinearLayoutManager lnlManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        lnlManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(lnlManager);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
