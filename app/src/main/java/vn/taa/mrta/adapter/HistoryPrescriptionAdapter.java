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
import vn.taa.mrta.details.Prescription.PrescriptionDetailsActivity;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.object.PrescriptionInfo;
import vn.taa.mrta.R;

/**
 * Created by Putin on 3/11/2017.
 */

public class HistoryPrescriptionAdapter extends RecyclerView.Adapter<HistoryPrescriptionAdapter.ViewHolder> {
    private Context context;
    private List<PrescriptionInfo> prescriptionInfoList;

    public HistoryPrescriptionAdapter(Context context, List<PrescriptionInfo> prescriptionInfoList) {
        this.context = context;
        this.prescriptionInfoList = prescriptionInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PrescriptionInfo info = prescriptionInfoList.get(position);

        holder.txtDate.setText(info.getDate());
        holder.txtDoctor.setText(info.getDoctorName());
        holder.lnlItemHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(context, PrescriptionDetailsActivity.class);
                intentDetail.putExtra(CommonField.TREATMENT_TIME, info.getTime());
                context.startActivity(intentDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lnlItemHistory;
        public TextViewCSE txtDate;
        public TextViewCSE txtDoctor;

        ViewHolder(View itemView) {
            super(itemView);
            lnlItemHistory = (LinearLayout) itemView.findViewById(R.id.lnl_item_history);
            txtDate = (TextViewCSE) itemView.findViewById(R.id.txt_history_date);
            txtDoctor = (TextViewCSE) itemView.findViewById(R.id.txt_history_doctor);
        }
    }
}