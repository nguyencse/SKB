package vn.taa.mrta.object;

/**
 * Created by Putin on 5/3/2017.
 */

public class Meeting {
    private String date;
    private String content;
    private String note;
    private String doctor;
    private String phone;
    private String office;

    public Meeting() {
    }

    public Meeting(String date, String content, String note, String doctor, String phone, String office) {
        this.date = date;
        this.content = content;
        this.note = note;
        this.doctor = doctor;
        this.phone = phone;
        this.office = office;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
