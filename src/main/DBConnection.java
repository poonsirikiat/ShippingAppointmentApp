package main;
import javax.management.StandardEmitterMBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";

    //private static final String location = "//localhost:3306/";
    private static final String location = "//database-1.c1bb9bplrrer.us-west-2.rds.amazonaws.com/";

    private static final String dbName = "client_schedule";
    private static final String jdbcURL = protocol + vendorName + location + dbName + "?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    //private static final String userName = "sqlUser";
    private static final String userName = "admin";

    private static final String password = "Passw0rd!";
    public static Connection connection = null;

    /**
     *
     * @return user connection with username and password
     */
    public static Connection openConnection(){
        try{
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection successful");
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    /**
     *
     * @return connection to SQL database
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * close connection to SQL database
     */
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed");
        }
        catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }
}
