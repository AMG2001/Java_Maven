package gov.iti.jets;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "");
            System.out.println("Connection Established !!");
            Statement stmt = con.createStatement();
            String queryString = new String("select * from tab");
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
