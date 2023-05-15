import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseService {

    default String getSQLScript(String path) {
        Path filePath = Path.of(path);
        String script = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath.toUri()));
            script = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return script;
    }

    default void executeScript(String script) {
        try (Connection conn = Database.getInstance().getConnection()) {
            System.out.println(conn);
            Statement statement = conn.createStatement();
            boolean execute = statement.execute(script);
            System.out.println("Execute result = " + execute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


