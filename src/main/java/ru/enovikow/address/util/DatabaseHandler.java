package ru.enovikow.address.util;

import javax.swing.*;
import java.sql.*;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseHandler() {
        createConnection();
        setupBookTable();
    }

    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {

        }
    }

    void setupBookTable() {
        String TABLE_NAME = "contacts";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exist. Ready for go!");
            } else {
                System.out.println("Exception");
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
                        + "name VARCHAR(40) NOT NULL,\n"
                        + "surname VARCHAR(50) NOT NULL,\n"
                        + "birthday DATE NOT NULL,\n"
                        + "address VARCHAR(200)"
//                        + "isAvail boolean DEFAULT TRUE"
                        + ")");
            }
//            stmt.close();
//            conn.close();
        } catch (SQLException e) {
            System.out.println("Motherfucker");
        }
        finally {


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
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
