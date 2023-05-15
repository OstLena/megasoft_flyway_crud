package configurations;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FlywayConfigurations {
    private Flyway flyway;
    private static final String DEFAULT_FILE_NAME ="application.properties";

        public FlywayConfigurations setup() throws IOException {
            setup(DEFAULT_FILE_NAME);
            return this;
        }
        public FlywayConfigurations setup(String propertiesFileName) throws IOException {
            Properties properties = new Properties();
            properties.load(FlywayConfigurations.class.getClassLoader().getResourceAsStream(propertiesFileName));
            String url = properties.getProperty(Constants.FLYWAY_CONNECTION_URL);
            String userName = properties.getProperty(Constants.FLYWAY_USER);
            String password = properties.getProperty(Constants.FLYWAY_PASSWORD);
            // Create the Flyway instance and point it to the database
            Location migration = new Location("db/migration");
            Location mixture = new Location("db/mixture");
            flyway = Flyway.configure()
                    .encoding(StandardCharsets.UTF_8)
                    .locations(migration, mixture)
                    .dataSource(url, userName, password)
                    .load();
            return this;
        }

        public void migrate() {
            flyway.migrate();
        }
    }
