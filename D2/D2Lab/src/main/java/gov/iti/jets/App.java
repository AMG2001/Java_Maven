package gov.iti.jets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;

/**
 * Hello world!
 *
 */
public class App {

    static private Connection con;
    static private MysqlDataSource mysqlDS = null;
    static private Scanner get = new Scanner(System.in);

    /**
     * Set Properties of Database Connection in External File
     */
    static void setProperties() {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("db.properties");
            // set the properties value
            prop.setProperty("MYSQL_DB_URL", "jdbc:mysql://localhost:3306/students");
            prop.setProperty("MYSQL_DB_USERNAME", "root");
            prop.setProperty("MYSQL_DB_PASSWORD", "");
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Establish Connection with Database "students".
     */
    static void establishConnection() {
        try {
            con = mysqlDS.getConnection();
            System.out.println("Connection Established !!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void getDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            con = mysqlDS.getConnection();
            // get the properties value
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } catch (SQLException s) {
            System.out.println(s.getStackTrace());
        }
    }

    static void insertRecord() {
        System.out.print("Enter your first name -> ");
        String firstName = get.next();
        System.out.print("Enter your last name -> ");
        String lastName = get.next();
        System.out.print("Enter your location -> ");
        String location = get.next();
        try {
            Statement s = con.createStatement();
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("insert into students (first_name,last_name,location) values (")
                    .append("'" + firstName + "',").append("'" + lastName + "',").append("'" + location + "');");
            s.executeUpdate(strBuilder.toString());
            System.out.println("##################### inserted Successfully ####################");
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    static void removeRecord() {
        System.out.print("Enter ID you want to remove -> ");
        int id = get.nextInt();
        try {
            Statement s = con.createStatement();
            s.executeUpdate("delete from students where id = "+id+";");
            System.out.println("##################### Deleted Successfully ####################");
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    static void updateRecord() {
        String newLocation = "";
        System.out.print("Enter id of record you want to update : ");
        int id = get.nextInt();
        get.nextLine();
        System.out.print("Enter new location : ");
        newLocation = get.nextLine();
        try {
            Statement s = con.createStatement();
            s.executeUpdate("update students set location = '" + newLocation + "' where id = " + id);
            System.out.println("Updated Successfully ##");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void displayAllRecords() {
        try {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from students");
            System.out.println("ID\tFirst Name\tLast Name");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "        " + rs.getString("first_name") + "           "
                        + rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setProperties();
        getDataSource();
        establishConnection();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(
                    "Choose command -> [ Display : d | Insert : i | remove : r | update : u ] or e to end program");
            System.out.print("  -> ");
            String option = get.next();
            switch (option) {
                case "d":
                    displayAllRecords();
                    break;
                case "u":
                    updateRecord();
                    break;
                case "i":
                    insertRecord();
                    break;
                case "r":
                    removeRecord();
                    break;
                case "e":
                    isRunning = false;
                    try {
                        con.close();
                        System.out.println("Connection Closed #");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Program End #");
                    break;
                default:
                    System.out.println("Choose valid option !!");
                    break;
            }
        }
    }
}