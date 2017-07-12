package vn.taa.mrta.details.Treatment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.custom.TypefaceSpanCSE;
import vn.taa.mrta.details.Prescription.PrescriptionDetailsActivity;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.R;
import vn.taa.mrta.object.Treatment;

public class TreatmentDetailsActivity extends AppCompatActivity implements IViewTreatmentDetails {

    private Toolbar toolbar;
    private Typeface typeface;
    private TypefaceSpanCSE typefaceSpan;

    private TextViewCSE txtTime;
    private TextViewCSE txtDate;
    private TextViewCSE txtDoctorName;
    private TextViewCSE txtDoctorOffice;
    private TextViewCSE txtSympton;
    private TextViewCSE txtContent;
    private TextViewCSE txtNote;
    private TextViewCSE txtIsCancel;
    private FloatingActionButton fabViewPrescriptionDetail;

//    private TextViewCSE txtCost;
//    private TextViewCSE txtCardiovascular;
//    private TextViewCSE txtTemperature;
//    private TextViewCSE txtBloodPressure;
//    private TextViewCSE txtBreathingRate;
//    private TextViewCSE txtWeight;
//    private TextViewCSE txtHeight;
//    private TextViewCSE txtBMI;
//    private TextViewCSE txtStatus;
//    private TextViewCSE txtClinicalApproach;

    private SharedPreferences sprfSKB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_treatment);

        initialize();
    }

    private void initialize() {
        sprfSKB = getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar_details_treatment);
        txtTime = (TextViewCSE) findViewById(R.id.txt_details_treatment_time);
        txtDate = (TextViewCSE) findViewById(R.id.txt_details_treatment_date);
        txtDoctorName = (TextViewCSE) findViewById(R.id.txt_details_treatment_doctor);
        txtDoctorOffice = (TextViewCSE) findViewById(R.id.txt_details_treatment_office);
        txtSympton = (TextViewCSE) findViewById(R.id.txt_details_treatment_sympton);
        txtContent = (TextViewCSE) findViewById(R.id.txt_details_treatment_content);
        txtNote = (TextViewCSE) findViewById(R.id.txt_details_treatment_note);
        txtIsCancel = (TextViewCSE) findViewById(R.id.txt_details_treatment_cancel);
        fabViewPrescriptionDetail = (FloatingActionButton) findViewById(R.id.fab_view_detail_prescription);

//        txtCost = (TextViewCSE) findViewById(R.id.txt_details_treatment_cost);
//        txtCardiovascular = (TextViewCSE) findViewById(R.id.txt_details_treatment_cardiovascular);
//        txtTemperature = (TextViewCSE) findViewById(R.id.txt_details_treatment_temperature);
//        txtBloodPressure = (TextViewCSE) findViewById(R.id.txt_details_treatment_blood_pressure);
//        txtBreathingRate = (TextViewCSE) findViewById(R.id.txt_details_treatment_breathing_rate);
//        txtWeight = (TextViewCSE) findViewById(R.id.txt_details_treatment_weight);
//        txtHeight = (TextViewCSE) findViewById(R.id.txt_details_treatment_height);
//        txtBMI = (TextViewCSE) findViewById(R.id.txt_details_treatment_bmi);
//        txtStatus = (TextViewCSE) findViewById(R.id.txt_details_treatment_status);
//        txtClinicalApproach = (TextViewCSE) findViewById(R.id.txt_details_treatment_clinical_approach);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_opensans_light) + ".ttf");
        typefaceSpan = new TypefaceSpanCSE(typeface);
        String toolbarTitle = getString(R.string.toolbar_title_details_treatment);
        SpannableString spannableStringToolbarTitle = new SpannableString(toolbarTitle);
        spannableStringToolbarTitle.setSpan(typefaceSpan, 0, toolbarTitle.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        toolbar.setTitle(spannableStringToolbarTitle);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getTreatmentDetails();

        fabViewPrescriptionDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPrescription = new Intent(TreatmentDetailsActivity.this, PrescriptionDetailsActivity.class);
                intentPrescription.putExtra(CommonField.TREATMENT_TIME, txtTime.getText());
                startActivity(intentPrescription);
                finish();
            }
        });
    }

    private void getTreatmentDetails() {
        Intent intent = getIntent();
        PresenterTreatmentDetails presenterTreatmentDetails = new PresenterTreatmentDetails(this);
        presenterTreatmentDetails.getTreatmentDetails(TreatmentDetailsActivity.this, sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""), intent.getStringExtra(CommonField.TREATMENT_TIME));
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
    public void showTreatmentDetails(Treatment treatment) {
        if (treatment != null) {
            txtTime.setText(treatment.getTime());
            txtDate.setText(treatment.getDate());
            txtDoctorName.setText(treatment.getDoctorName());
            txtDoctorOffice.setText(treatment.getDoctorOffice());
            txtSympton.setText(treatment.getSympton());
            txtContent.setText(treatment.getContent());
            txtNote.setText(treatment.getNote());
            txtIsCancel.setText(treatment.getIsCancel());

//            txtCost.setText(treatment.getCost());
//            txtCardiovascular.setText(treatment.getCardiovascular());
//            txtTemperature.setText(treatment.getTemperature());
//            txtBloodPressure.setText(treatment.getBloodPressure());
//            txtBreathingRate.setText(treatment.getBreathingRate());
//            txtWeight.setText(treatment.getWeight());
//            txtHeight.setText(treatment.getHeight());
//            txtBMI.setText(treatment.getBmi());
//            txtStatus.setText(treatment.getStatus());
//            txtClinicalApproach.setText(treatment.getClinicalApproach());
        }
    }
}
