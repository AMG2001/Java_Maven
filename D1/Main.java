import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Main {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", ""); 
            System.out.println("Connection Established !!");
            Statement stmt = con.createStatement() ;
            String queryString = new String("select * from students");
            ResultSet rs =  stmt.executeQuery(queryString);
            while(rs.next()){
                System.out.println(rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
