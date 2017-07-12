package vn.taa.mrta.object;

/**
 * Created by Putin on 4/23/2017.
 */

public class DrugOffline {
    private String name;
    private int quantity;
    private String unit;
    private String note;
    private int quantityMorning;
    private int quantityNoon;
    private int quantityAfternoon;
    private int quantityEvening;

    public DrugOffline() {
    }

    public DrugOffline(String name, int quantity, String unit, String note, int quantityMorning, int quantityNoon, int quantityAfternoon, int quantityEvening) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
        this.quantityMorning = quantityMorning;
        this.quantityNoon = quantityNoon;
        this.quantityAfternoon = quantityAfternoon;
        this.quantityEvening = quantityEvening;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantityMorning() {
        return quantityMorning;
    }

    public void setQuantityMorning(int quantityMorning) {
        this.quantityMorning = quantityMorning;
    }

    public int getQuantityNoon() {
        return quantityNoon;
    }

    public void setQuantityNoon(int quantityNoon) {
        this.quantityNoon = quantityNoon;
    }

    public int getQuantityAfternoon() {
        return quantityAfternoon;
    }

    public void setQuantityAfternoon(int quantityAfternoon) {
        this.quantityAfternoon = quantityAfternoon;
    }

    public int getQuantityEvening() {
        return quantityEvening;
    }

    public void setQuantityEvening(int quantityEvening) {
        this.quantityEvening = quantityEvening;
    }
}
