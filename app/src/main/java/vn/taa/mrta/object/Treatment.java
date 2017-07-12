package vn.taa.mrta.object;

/**
 * Created by Putin on 3/5/2017.
 */

public class Treatment {
    private String time;
    private String date;
    private String doctorName;
    private String doctorOffice;
    private String sympton;
    private String content;
    private String note;
    private String isCancel;

//    private String cost;
//    private String cardiovascular;
//    private String temperature;
//    private String bloodPressure;
//    private String breathingRate;
//    private String weight;
//    private String height;
//    private String bmi;
//    private String status;
//    private String clinicalApproach;

    public Treatment() {
    }

    public Treatment(String time, String date, String doctorName, String doctorOffice, String sympton, String content, String note, String isCancel) {
        this.time = time;
        this.date = date;
        this.doctorName = doctorName;
        this.doctorOffice = doctorOffice;
        this.sympton = sympton;
        this.content = content;
        this.note = note;
        this.isCancel = isCancel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorOffice() {
        return doctorOffice;
    }

    public void setDoctorOffice(String doctorOffice) {
        this.doctorOffice = doctorOffice;
    }

    public String getSympton() {
        return sympton;
    }

    public void setSympton(String sympton) {
        this.sympton = sympton;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }
}
