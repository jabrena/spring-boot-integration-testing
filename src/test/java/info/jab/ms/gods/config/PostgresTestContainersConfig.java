package info.jab.ms.gods.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgresTestContainersConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    @Container
    private static final PostgreSQLContainer<?> postgresContainer;
    
    // Initialize container
    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        // Start container when the class is loaded
        postgresContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        TestPropertyValues.of(
            "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
            "spring.datasource.username=" + postgresContainer.getUsername(),
            "spring.datasource.password=" + postgresContainer.getPassword()
        ).applyTo(ctx.getEnvironment());
    }
} 