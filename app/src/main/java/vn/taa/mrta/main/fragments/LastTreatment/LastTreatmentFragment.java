package vn.taa.mrta.main.fragments.LastTreatment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.details.Treatment.TreatmentDetailsActivity;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.history.Treatment.HistoryTreatmentActivity;
import vn.taa.mrta.R;
import vn.taa.mrta.object.Treatment;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Putin on 3/9/2017.
 */

public class LastTreatmentFragment extends Fragment implements IViewLastTreatment, View.OnClickListener {
    private SharedPreferences sprfSKB;
    private TextViewCSE txtSympton;
    private TextViewCSE txtTreatmentContent;
    private TextViewCSE txtLastTreatmentDate;
    private String lastTreatmentTime;

//    private String time;
//    private String date;
//    private String doctorName;
//    private String doctorOffice;
//    private String sympton;
//    private String content;
//    private String cost;
//    private String note;
//    private String isCancel;
//    private String cardiovascular;
//    private String temperature;
//    private String bloodPressure;
//    private String breathingRate;
//    private String weight;
//    private String height;
//    private String bmi;
//    private String status;
//    private String clinicalApproach;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_treatment, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        sprfSKB = getContext().getSharedPreferences(CommonField.SHARED_PREFERENCES, MODE_PRIVATE);
        txtSympton = (TextViewCSE) view.findViewById(R.id.txt_item_sympton);
        txtTreatmentContent = (TextViewCSE) view.findViewById(R.id.txt_item_treatment_content);
        txtLastTreatmentDate = (TextViewCSE) view.findViewById(R.id.txt_last_treatment_date);
        LinearLayout lnlMoreDetails = (LinearLayout) view.findViewById(R.id.lnl_item_more_details);
        TextViewCSE txtGetOlderTreatmentTile = (TextViewCSE) view.findViewById(R.id.txt_get_older_treatment_time);

        txtSympton.setMovementMethod(new ScrollingMovementMethod());
        txtTreatmentContent.setMovementMethod(new ScrollingMovementMethod());

        String patientBriefID = sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, "");

        PresenterLastTreatment presenterLastTreatment = new PresenterLastTreatment(this);
        presenterLastTreatment.getLastTreatment(getContext(), patientBriefID);

        lnlMoreDetails.setOnClickListener(this);
        txtGetOlderTreatmentTile.setOnClickListener(this);
    }

    @Override
    public void showLastTreatment(Treatment treatment) {
        lastTreatmentTime = treatment.getTime();
        txtSympton.setText(treatment.getSympton());
        txtTreatmentContent.setText(treatment.getContent());
        txtLastTreatmentDate.setText(treatment.getDate());

//        this.time = time;
//        this.date = date;
//        this.doctorName = doctorName;
//        this.doctorOffice = doctorOffice;
//        this.sympton = sympton;
//        this.content = content;
//        this.cost = cost;
//        this.note = note;
//        this.isCancel = isCancel;
//        this.cardiovascular = cardiovascular;
//        this.temperature = temperature;
//        this.bloodPressure = bloodPressure;
//        this.breathingRate = breathingRate;
//        this.weight = weight;
//        this.height = height;
//        this.bmi = bmi;
//        this.status = status;
//        this.clinicalApproach = clinicalApproach;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.lnl_item_more_details:
                Intent intentDetails = new Intent(getActivity(), TreatmentDetailsActivity.class);
                intentDetails.putExtra(CommonField.TREATMENT_TIME, lastTreatmentTime);
                startActivity(intentDetails);

//                intentDetails.putExtra(CommonField.TREATMENT_TIME, time);
//                intentDetails.putExtra(CommonField.TREATMENT_DATE, date);
//                intentDetails.putExtra(CommonField.TREATMENT_DOCTOR_NAME, doctorName);
//                intentDetails.putExtra(CommonField.TREATMENT_DOCTOR_OFFICE, doctorOffice);
//                intentDetails.putExtra(CommonField.TREATMENT_SYMPTON, sympton);
//                intentDetails.putExtra(CommonField.TREATMENT_CONTENT, content);
//                intentDetails.putExtra(CommonField.TREATMENT_COST, cost);
//                intentDetails.putExtra(CommonField.TREATMENT_NOTE, note);
//                intentDetails.putExtra(CommonField.TREATMENT_IS_CANCEL, isCancel);
//                intentDetails.putExtra(CommonField.TREATMENT_CARDIOVASCULAR, cardiovascular);
//                intentDetails.putExtra(CommonField.TREATMENT_TEMPERATURE, temperature);
//                intentDetails.putExtra(CommonField.TREATMENT_BLOOD_PRESSURE, bloodPressure);
//                intentDetails.putExtra(CommonField.TREATMENT_BREATHING_RATE, breathingRate);
//                intentDetails.putExtra(CommonField.TREATMENT_WEIGHT, weight);
//                intentDetails.putExtra(CommonField.TREATMENT_HEIGHT, height);
//                intentDetails.putExtra(CommonField.TREATMENT_BMI, bmi);
//                intentDetails.putExtra(CommonField.TREATMENT_STATUS, status);
//                intentDetails.putExtra(CommonField.TREATMENT_CLINICAL_APPROACH, clinicalApproach);
                break;
            case R.id.txt_get_older_treatment_time:
                Intent intentHistory = new Intent(getActivity(), HistoryTreatmentActivity.class);
                startActivity(intentHistory);
                break;
        }
    }
}
