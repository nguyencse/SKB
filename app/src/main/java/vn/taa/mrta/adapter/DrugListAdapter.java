package vn.taa.mrta.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import vn.taa.mrta.R;
import vn.taa.mrta.custom.TextViewCSE;
import vn.taa.mrta.general.CommonField;
import vn.taa.mrta.object.Drug;

/**
 * Created by Putin on 3/27/2017.
 */

public class DrugListAdapter extends RecyclerView.Adapter<DrugListAdapter.ViewHolder> {
    private Context context;
    private List<Drug> drugList;

    public DrugListAdapter(Context context, List<Drug> drugList) {
        this.context = context;
        this.drugList = drugList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_recylcerview_dashboard_drug_list_today, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drug drug = drugList.get(position);

        holder.txtDrugName.setText(drug.getName());
        holder.txtDrugQuantity.setText(drug.getQuantity() + "");
        holder.txtDrugUnit.setText(drug.getUnit());
        setupIconUnit(holder, drug.getUnit());
        holder.lnlDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPagerMain = (ViewPager) ((Activity) context).findViewById(R.id.viewpager_main);
                viewPagerMain.setCurrentItem(CommonField.FRAGMENT_PRESCRIPTION);
                DrawerLayout drlDashboard = (DrawerLayout) ((Activity) context).findViewById(R.id.activity_main);
                LinearLayout lnlDashboard = (LinearLayout) ((Activity) context).findViewById(R.id.custom_layout_dashboard);
                drlDashboard.closeDrawer(lnlDashboard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lnlDrug;
        public TextViewCSE txtDrugName;
        public TextViewCSE txtDrugQuantity;
        public TextViewCSE txtDrugUnit;
        public ImageView imgDrugUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlDrug = (LinearLayout) itemView.findViewById(R.id.lnl_dashboard_item_drug);
            txtDrugName = (TextViewCSE) itemView.findViewById(R.id.txt_item_dashboard_drug_name);
            txtDrugQuantity = (TextViewCSE) itemView.findViewById(R.id.txt_item_dashboard_drug_quantity);
            txtDrugUnit = (TextViewCSE) itemView.findViewById(R.id.txt_item_dashboard_drug_unit);
            imgDrugUnit = (ImageView) itemView.findViewById(R.id.img_item_dashboard_drug_unit);
        }
    }

    private void setupIconUnit(ViewHolder holder, String unit) {
        switch (unit) {
            case CommonField.DRUG_UNIT_PILL:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_pill));
                break;
            case CommonField.DRUG_UNIT_BOTTLE:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_bottle));
                break;
            case CommonField.DRUG_UNIT_BOTTLE_2:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_bottle));
                break;
            case CommonField.DRUG_UNIT_TUBE:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_tube));
                break;
            case CommonField.DRUG_UNIT_TUBE_2:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_tube));
                break;
            case CommonField.DRUG_UNIT_BOX:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_box));
                break;
            case CommonField.DRUG_UNIT_ROLL:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_roll));
                break;
            case CommonField.DRUG_UNIT_UNKNOW:
                holder.imgDrugUnit.setImageDrawable(null);
                break;
            default:
                holder.imgDrugUnit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drug_unit_unknow));
                break;
        }
    }
}
