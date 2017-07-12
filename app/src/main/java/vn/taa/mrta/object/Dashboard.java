package vn.taa.mrta.object;

import java.util.List;

/**
 * Created by Putin on 3/27/2017.
 */

public class Dashboard {
    private Patient patient;
    private List<Drug> drugListMorning;
    private List<Drug> drugListNoon;
    private List<Drug> drugListAfternoon;
    private List<Drug> drugListEvening;

    public Dashboard() {
    }

    public Dashboard(Patient patient, List<Drug> drugListMorning, List<Drug> drugListNoon, List<Drug> drugListAfternoon, List<Drug> drugListEvening) {
        this.patient = patient;
        this.drugListMorning = drugListMorning;
        this.drugListNoon = drugListNoon;
        this.drugListAfternoon = drugListAfternoon;
        this.drugListEvening = drugListEvening;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Drug> getDrugListMorning() {
        return drugListMorning;
    }

    public void setDrugListMorning(List<Drug> drugListMorning) {
        this.drugListMorning = drugListMorning;
    }

    public List<Drug> getDrugListNoon() {
        return drugListNoon;
    }

    public void setDrugListNoon(List<Drug> drugListNoon) {
        this.drugListNoon = drugListNoon;
    }

    public List<Drug> getDrugListAfternoon() {
        return drugListAfternoon;
    }

    public void setDrugListAfternoon(List<Drug> drugListAfternoon) {
        this.drugListAfternoon = drugListAfternoon;
    }

    public List<Drug> getDrugListEvening() {
        return drugListEvening;
    }

    public void setDrugListEvening(List<Drug> drugListEvening) {
        this.drugListEvening = drugListEvening;
    }
}
