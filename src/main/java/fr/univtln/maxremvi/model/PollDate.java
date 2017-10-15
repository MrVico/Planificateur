package fr.univtln.maxremvi.model;

import javafx.beans.property.SimpleStringProperty;

public class PollDate {
    private final SimpleStringProperty datum = new SimpleStringProperty("");
    private final SimpleStringProperty hour1 = new SimpleStringProperty("");
    private final SimpleStringProperty hour2 = new SimpleStringProperty("");
    private final SimpleStringProperty hour3 = new SimpleStringProperty("");

    public PollDate() {
        setDatum("15/10/2017");
        setHour1("12:00");
        setHour2("13:00");
        setHour3("14:00");
    }

    public String getDatum() {
        return datum.get();
    }

    public SimpleStringProperty datumProperty() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }

    public String getHour1() {
        return hour1.get();
    }

    public SimpleStringProperty hour1Property() {
        return hour1;
    }

    public void setHour1(String hour1) {
        this.hour1.set(hour1);
    }

    public String getHour2() {
        return hour2.get();
    }

    public SimpleStringProperty hour2Property() {
        return hour2;
    }

    public void setHour2(String hour2) {
        this.hour2.set(hour2);
    }

    public String getHour3() {
        return hour3.get();
    }

    public SimpleStringProperty hour3Property() {
        return hour3;
    }

    public void setHour3(String hour3) {
        this.hour3.set(hour3);
    }
}
