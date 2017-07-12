package vn.taa.mrta.object;

/**
 * Created by Putin on 5/8/2017.
 */

public class UserInfo {
    private String name;
    private String age;
    private String gender;
    private String address;
    private String diagnose;
    private String note;

    public UserInfo() {
    }

    public UserInfo(String name, String age, String gender, String address, String diagnose, String note) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.diagnose = diagnose;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
