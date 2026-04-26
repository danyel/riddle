package be.riddler;

import be.riddler.configuration.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * DemoApplication
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@ActiveProfiles("test")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.from(Application::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
