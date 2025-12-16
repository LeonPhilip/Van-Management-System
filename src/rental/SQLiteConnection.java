package rental;



import java.sql.*;
import javax.swing.*;
public class SQLiteConnection {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(
                    "jdbc:sqlite:E:\\From usb\\eclipse project\\Rental\\src\\Database\\borDB.db");

                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA journal_mode=WAL;");
                stmt.close();

                System.out.println("Connected (single connection)");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return conn;
    }
}

	
	
	
	

