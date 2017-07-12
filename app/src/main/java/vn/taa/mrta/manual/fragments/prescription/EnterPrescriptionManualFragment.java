package vn.taa.mrta.manual.fragments.prescription;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import vn.taa.mrta.R;
import vn.taa.mrta.custom.EditTextCSE;

/**
 * Created by Putin on 5/8/2017.
 */

public class EnterPrescriptionManualFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_prescription_manual, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {

    }

    @Override
    public void onClick(View v) {
        
    }
}
