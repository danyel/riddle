package be.riddler;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@StyleSheet(Lumo.STYLESHEET)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
