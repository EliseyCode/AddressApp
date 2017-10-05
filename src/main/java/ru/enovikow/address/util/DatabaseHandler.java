package ru.enovikow.address.util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    private static DatabaseHandler databaseHandler;
    private static boolean baseIsExist;

    public static DatabaseHandler getDatabaseHandler() {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler();
            baseIsExist = true;
            return databaseHandler;
        } else {
            return databaseHandler;
        }
    }

    public DatabaseHandler() {
        createConnection();
        if (baseIsExist) {
            createBookAndCustomerTables();
        }
    }

    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {

        }
    }

    void createBookAndCustomerTables() {
        final String TABLE_NAME_CONTACT = "contacts";
        final String TABLE_NAME_CUSTOMER = "customer";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME_CONTACT.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME_CONTACT + " already exist. Ready for go!");
            } else {
                System.out.println("Creating tables " + TABLE_NAME_CONTACT + " " + TABLE_NAME_CUSTOMER);
                stmt.execute("CREATE TABLE " + TABLE_NAME_CONTACT + "("
                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
                        + "name VARCHAR(40) NOT NULL,\n"
                        + "surname VARCHAR(50) NOT NULL,\n"
                        + "birthday DATE NOT NULL,\n"
                        + "address VARCHAR(200)"
                        + ")");

                stmt.execute("CREATE TABLE " + TABLE_NAME_CUSTOMER + "("
                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
                        + "login VARCHAR(25) NOT NULL,\n"
                        + "password VARCHAR(25) NOT NULL\n"
                        + ")");
            }
        } catch (SQLException e) {
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execQuery: data handler " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean createCustomer(String login, String password) {
        String query = "INSERT INTO CUSTOMER (LOGIN, PASSWORD) VALUES ('"
                + login + "', '"
                + password + "')";
        if (!loginIsExistInDB(login)) {
            System.out.println("Doesn't contain value in DB");
            execAction(query);
            return true;
        } else {
            System.out.println("Contains value in DB");
            return false;
        }
    }

    public boolean loginIsExistInDB(String value) {
        String qu = "SELECT LOGIN FROM CUSTOMER WHERE LOGIN='" + value + "'";
        try {
            if (execQuery(qu).next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isAccountExisting(String login, String password) {
        String qu = "SELECT LOGIN, PASSWORD FROM CUSTOMER WHERE LOGIN='" + login + "'";
        try {
            ResultSet rs = execQuery(qu);
            while (rs.next()) {
                if (login.equals(rs.getString("LOGIN")) && password.equals(rs.getString("PASSWORD"))) {

                    System.out.println("Login and password correct");
                    return true;
                }
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean addContactToDb(String firstname, String lastname, String day, String month, String year, String address) {
        ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));

        String qu = "INSERT INTO contacts (name, surname, birthday, address) VALUES ('"
                + firstname + "', "
                + " '" + lastname + "', "
                + " '" + LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)) + "', "
                + "'" + address + "'"
                + ")";

        System.out.println(qu);
        if (databaseHandler.execAction(qu)) {
            return true;
        } else {
            return false;
        }

    }
}