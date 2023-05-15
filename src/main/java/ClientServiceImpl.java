import entity.Client;
import exception.ClientDbValidationException;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl implements ClientService {

    @Language("SQL")
    private static final String INSERT_CLIENT = """
            INSERT INTO client (id, name) VALUES (DEFAULT, ?)""";

    @Language("SQL")
    private static final String FIND_BY_ID = "SELECT name FROM client WHERE id=?";

    @Language("SQL")
    private static final String UPDATE_CLIENT_NAME = """
            UPDATE client SET name=? WHERE id=?;""";

    @Language("SQL")
    private static final String DELETE_BY_ID = "DELETE FROM client WHERE id=?";
    @Language("SQL")
    private final String FIND_ALL = "SELECT * FROM client;";

    @Override
    public long create(String name) {
        if (name == null || name.length() > 1000) {
            throw new ClientDbValidationException("Wrong client name " + name);
        }
        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.first();
            long clientId = generatedKeys.getLong("id");
            return clientId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getById(long id) {
        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (next) {
                return resultSet.getString("name");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void setName(long id, String name) {
        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_NAME);
            final int nameIndex = 1;
            final int idIndex = 2;

            preparedStatement.setString(nameIndex, name);
            preparedStatement.setLong(idIndex, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            final int idIndex = 1;
            preparedStatement.setLong(idIndex, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> listAll() {
        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clientList = new ArrayList<>();
            while (resultSet.next()) {
                Client client = parseClientRow(resultSet);
                clientList.add(client);
            }
            return clientList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Client parseClientRow(ResultSet resultSet) throws SQLException {
        Long clientId = resultSet.getLong("id");
        String clientName = resultSet.getString("name");
        return Client.of(clientId, clientName);
    }
}
