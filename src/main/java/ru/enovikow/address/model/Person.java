package ru.enovikow.address.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {

    private final IntegerProperty ID;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty address;
    private final ObjectProperty<LocalDate> birthday;

    public Person() {
        this(null, null, null, null, null);
    }


    public Person(Integer id, String firstName, String lastName, String address, LocalDate birthday) {
        this.ID = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty getLastNameProperty() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> getBirthdayProperty() {
        return birthday;
    }

    public StringProperty getAddressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getAddress() {
        return address.get();
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty getIDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }
}
