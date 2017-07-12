package vn.taa.mrta.object;

/**
 * Created by Putin on 5/6/2017.
 */

public class Clinic {
    private String nameUnit;
    private String nameClinic;
    private String address;
    private String phone;
    private String email;
    private String fax;

    public Clinic() {
    }

    public Clinic(String nameUnit, String nameClinic, String address, String phone, String email, String fax) {
        this.nameUnit = nameUnit;
        this.nameClinic = nameClinic;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fax = fax;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public String getNameClinic() {
        return nameClinic;
    }

    public void setNameClinic(String nameClinic) {
        this.nameClinic = nameClinic;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}