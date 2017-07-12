package vn.taa.mrta.object;

import java.util.List;

/**
 * Created by Putin on 3/11/2017.
 */

public class Prescription {
    private PrescriptionInfo info;
    private List<PrescriptionDrug> drugList;

    public Prescription() {
    }

    public Prescription(PrescriptionInfo info, List<PrescriptionDrug> drugList) {
        this.info = info;
        this.drugList = drugList;
    }

    public PrescriptionInfo getInfo() {
        return info;
    }

    public void setInfo(PrescriptionInfo info) {
        this.info = info;
    }

    public List<PrescriptionDrug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<PrescriptionDrug> drugList) {
        this.drugList = drugList;
    }
}
