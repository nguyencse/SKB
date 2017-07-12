package vn.taa.mrta.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.details.Treatment.TreatmentDetailsActivity;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.object.Treatment;
import vn.taa.mrta.R;

/**
 * Created by Putin on 3/6/2017.
 */

public class HistoryTreatmentAdapter extends RecyclerView.Adapter<HistoryTreatmentAdapter.ViewHolder> {
    private Context context;
    private List<Treatment> treatmentList;

    public HistoryTreatmentAdapter(Context context, List<Treatment> treatmentList) {
        this.context = context;
        this.treatmentList = treatmentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Treatment treatment = treatmentList.get(position);

        holder.txtHistoryTreatmentDate.setText(treatment.getDate());
        holder.txtHistoryTreatmentDoctor.setText(treatment.getDoctorName());
        holder.lnlItemHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetails = new Intent(context, TreatmentDetailsActivity.class);
                intentDetails.putExtra(CommonField.TREATMENT_TIME, treatment.getTime());
                context.startActivity(intentDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return treatmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lnlItemHistory;
        public TextViewCSE txtHistoryTreatmentDate;
        public TextViewCSE txtHistoryTreatmentDoctor;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlItemHistory = (LinearLayout) itemView.findViewById(R.id.lnl_item_history);
            txtHistoryTreatmentDate = (TextViewCSE) itemView.findViewById(R.id.txt_history_date);
            txtHistoryTreatmentDoctor = (TextViewCSE) itemView.findViewById(R.id.txt_history_doctor);
        }
    }
}
