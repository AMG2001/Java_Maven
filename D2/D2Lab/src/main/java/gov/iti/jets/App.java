package gov.iti.jets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Hello world!
 *
 */
public class App {

    static private Connection con;
    static private MysqlDataSource mysqlDS = null;

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
            // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students",
            // "root", "");
            con = mysqlDS.getConnection();
            System.out.println("Connection Established !!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static DataSource getDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            // get the properties value
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } finally {
            return mysqlDS;
        }
    }

    public static void main(String[] args) {
        setProperties();
        getDataSource();
        establishConnection();
    }
}
