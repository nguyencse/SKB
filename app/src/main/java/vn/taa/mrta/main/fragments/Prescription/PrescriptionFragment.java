package vn.taa.mrta.main.fragments.Prescription;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import vn.taa.mrta.adapter.PrescriptionAdapter;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.history.Prescription.HistoryPrescriptionActivity;
import vn.taa.mrta.main.MainActivity;
import vn.taa.mrta.object.Prescription;
import vn.taa.mrta.R;

/**
 * Created by Putin on 3/9/2017.
 */

public class PrescriptionFragment extends Fragment implements IViewPrescription, View.OnClickListener {
    private RecyclerView recyclerViewLastPrescriptionDrugs;
    private TextViewCSE txtDate;
    private TextViewCSE txtGeneralNote;
    private TextViewCSE txtDoctor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prescription, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        recyclerViewLastPrescriptionDrugs = (RecyclerView) view.findViewById(R.id.recyclerview_last_prescription);
        txtDate = (TextViewCSE) view.findViewById(R.id.txt_prescription_treatment_date);
        txtGeneralNote = (TextViewCSE) view.findViewById(R.id.txt_prescription_note_general);
        txtDoctor = (TextViewCSE) view.findViewById(R.id.txt_prescription_doctor);
        TextViewCSE txtGetMorePrescriptions = (TextViewCSE) view.findViewById(R.id.txt_get_more_prescriptions);

        PresenterPrescription presenterPrescription = new PresenterPrescription(this);

        String lastPrescriptionJSON = MainActivity.sprfSKB.getString(CommonField.FRAGMENT_PRESCRIPTION_LAST_PRESCRIPTION, null);
        if (lastPrescriptionJSON != null) {
            showLastPrescriptionDrug(new Gson().fromJson(lastPrescriptionJSON, Prescription.class));
        } else {
            presenterPrescription.getLastPrescription(MainActivity.sprfSKB.getString(CommonField.PATIENT_BRIEF_ID, ""));
        }

        txtGetMorePrescriptions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.txt_get_more_prescriptions:
                Intent intent = new Intent(getActivity(), HistoryPrescriptionActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showLastPrescriptionDrug(Prescription prescription) {
        if (MainActivity.sprfSKB.getString(CommonField.FRAGMENT_PRESCRIPTION_LAST_PRESCRIPTION, null) == null) {
            MainActivity.sprfSKB.edit().putString(CommonField.FRAGMENT_PRESCRIPTION_LAST_PRESCRIPTION, new Gson().toJson(prescription)).apply();
        }

        txtDate.setText(prescription.getInfo().getDate());
        txtGeneralNote.setText(prescription.getInfo().getGeneralNote());
        txtDoctor.setText(prescription.getInfo().getDoctorName());

        PrescriptionAdapter adapter = new PrescriptionAdapter(getContext(), prescription.getDrugList());
        LinearLayoutManager lnlManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerViewLastPrescriptionDrugs.setLayoutManager(lnlManager);
        recyclerViewLastPrescriptionDrugs.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
