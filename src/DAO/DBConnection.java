package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by vindula on 9/16/16.
 */
public class DBConnection {

    private Connection connection;
    private static DBConnection dBConnection;
   // private static String host = "localhost";
    private static String uname = "root";
    private static String pword ="root";
    //private static String uname = "root";

    private static String host = "jdbc:mysql://localhost:3306/medical_portal";

    public DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(host, uname, pword);
    }

    public static String getUser() {
        return host;
    }

    public static String getPassword() {
        return pword;
    }

    public Connection getConnectionToDB() {
        return connection;
    }

    public static DBConnection getDBConnection() throws ClassNotFoundException, SQLException {
        if (dBConnection == null) {
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

}
