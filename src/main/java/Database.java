import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    //  Database credentials
    static final String USER = "admin";
    static final String PASS = "qwerty";

    public static Database getInstance() {
        return new Database();
    }

    private Database() {
    }

    public Connection getConnection() {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
