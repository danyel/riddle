package be.riddler.common.resource;

import be.riddler.configuration.TestcontainersConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * AbstractResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
public abstract class AbstractResourceTest {
    @Autowired
    protected WebTestClient webTestClient;
}
