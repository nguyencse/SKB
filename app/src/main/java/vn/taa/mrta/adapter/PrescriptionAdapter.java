package vn.taa.mrta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.object.PrescriptionDrug;
import vn.taa.mrta.R;

/**
 * Created by Putin on 3/9/2017.
 */

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private Context context;
    private List<PrescriptionDrug> prescriptionDrugList;

    public PrescriptionAdapter(Context context, List<PrescriptionDrug> prescriptionDrugList) {
        this.context = context;
        this.prescriptionDrugList = prescriptionDrugList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_drug_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PrescriptionDrug prescriptionDrug = prescriptionDrugList.get(position);

        holder.txtOrder.setText(prescriptionDrug.getOrder() + "");
        holder.txtDrugName.setText(prescriptionDrug.getName());
        holder.txtQuantity.setText(prescriptionDrug.getQuantity() + "");
        holder.txtUnit.setText(prescriptionDrug.getUnit());
        holder.txtNote.setText(prescriptionDrug.getNote());
        holder.txtMorning.setText((prescriptionDrug.getMorning().equals("") || prescriptionDrug.getMorning() == null || prescriptionDrug.getMorning().equals("null")) ? "0" : prescriptionDrug.getMorning());
        holder.txtNoon.setText((prescriptionDrug.getNoon().equals("") || prescriptionDrug.getNoon() == null || prescriptionDrug.getNoon().equals("null")) ? "0" : prescriptionDrug.getNoon());
        holder.txtAfternoon.setText((prescriptionDrug.getAfternoon().equals("") || prescriptionDrug.getAfternoon() == null || prescriptionDrug.getAfternoon().equals("null")) ? "0" : prescriptionDrug.getAfternoon());
        holder.txtEvening.setText((prescriptionDrug.getEvening().equals("") || prescriptionDrug.getEvening() == null || prescriptionDrug.getEvening().equals("null")) ? "0" : prescriptionDrug.getEvening());
    }

    @Override
    public int getItemCount() {
        return prescriptionDrugList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextViewCSE txtOrder;
        TextViewCSE txtDrugName;
        TextViewCSE txtQuantity;
        TextViewCSE txtUnit;
        TextViewCSE txtNote;
        TextViewCSE txtMorning;
        TextViewCSE txtNoon;
        TextViewCSE txtAfternoon;
        TextViewCSE txtEvening;

        ViewHolder(View itemView) {
            super(itemView);

            txtOrder = (TextViewCSE) itemView.findViewById(R.id.txt_drug_order);
            txtDrugName = (TextViewCSE) itemView.findViewById(R.id.txt_drug_name);
            txtQuantity = (TextViewCSE) itemView.findViewById(R.id.txt_drug_quantity);
            txtUnit = (TextViewCSE) itemView.findViewById(R.id.txt_drug_unit);
            txtNote = (TextViewCSE) itemView.findViewById(R.id.txt_drug_note);
            txtMorning = (TextViewCSE) itemView.findViewById(R.id.txt_drug_morning);
            txtNoon = (TextViewCSE) itemView.findViewById(R.id.txt_drug_noon);
            txtAfternoon = (TextViewCSE) itemView.findViewById(R.id.txt_drug_afternoon);
            txtEvening = (TextViewCSE) itemView.findViewById(R.id.txt_drug_evening);
        }
    }
}