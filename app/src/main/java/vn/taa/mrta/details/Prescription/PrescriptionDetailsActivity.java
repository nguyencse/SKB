package vn.taa.mrta.details.Prescription;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import vn.taa.mrta.adapter.PrescriptionAdapter;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.custom.TypefaceSpanCSE;
import vn.taa.mrta.details.Treatment.TreatmentDetailsActivity;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.object.Prescription;
import vn.taa.mrta.R;

public class PrescriptionDetailsActivity extends AppCompatActivity implements IViewPrescriptionDetails {

    private Toolbar toolbar;
    private Typeface typeface;
    private TypefaceSpanCSE typefaceSpan;

    private RecyclerView recyclerViewLastPrescriptionDrugs;
    private TextViewCSE txtDate;
    private TextViewCSE txtGeneralNote;
    private TextViewCSE txtDoctor;
    private FloatingActionButton fabViewDetailTreatment;
    private String treatmentTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_prescription);

        initialize();
    }

    private void initialize() {
        SharedPreferences sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details_prescription);
        recyclerViewLastPrescriptionDrugs = (RecyclerView) findViewById(R.id.recyclerview_prescription_detail);
        txtDate = (TextViewCSE) findViewById(R.id.txt_prescription_detail_treatment_date);
        txtGeneralNote = (TextViewCSE) findViewById(R.id.txt_prescription_detail_note_general);
        txtDoctor = (TextViewCSE) findViewById(R.id.txt_prescription_detail_doctor);
        fabViewDetailTreatment = (FloatingActionButton) findViewById(R.id.fab_view_detail_treatment);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_opensans_light) + ".ttf");
        typefaceSpan = new TypefaceSpanCSE(typeface);
        String toolbarTitle = getString(R.string.toolbar_title_details_prescription);
        SpannableString spannableStringToolbarTitle = new SpannableString(toolbarTitle);
        spannableStringToolbarTitle.setSpan(typefaceSpan, 0, toolbarTitle.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        toolbar.setTitle(spannableStringToolbarTitle);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        PresenterPrescriptionDetails prescriptionDetails = new PresenterPrescriptionDetails(this);
        prescriptionDetails.getPrescriptionDetails(sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""), intent.getStringExtra(CommonField.TREATMENT_TIME));

        fabViewDetailTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTreatment = new Intent(PrescriptionDetailsActivity.this, TreatmentDetailsActivity.class);
                intentTreatment.putExtra(CommonField.TREATMENT_TIME, treatmentTime);
                startActivity(intentTreatment);
            }
        });
    }

    @Override
    public void showPrescriptionDetails(Prescription prescription) {
        treatmentTime = prescription.getInfo().getTime();

        txtDate.setText(prescription.getInfo().getDate());
        txtGeneralNote.setText(prescription.getInfo().getGeneralNote());
        txtDoctor.setText(prescription.getInfo().getDoctorName());

        PrescriptionAdapter adapter = new PrescriptionAdapter(this, prescription.getDrugList());
        LinearLayoutManager lnlManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        recyclerViewLastPrescriptionDrugs.setLayoutManager(lnlManager);
        recyclerViewLastPrescriptionDrugs.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}