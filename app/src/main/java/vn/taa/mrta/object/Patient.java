package vn.taa.mrta.object;

/**
 * Created by Putin on 3/27/2017.
 */

public class Patient {
    private String name;
    private String gender;
    private String birthday;
    private String address;
    private String phone;

    public Patient() {
    }

    public Patient(String name, String gender, String birthday, String address, String phone) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
