package be.riddler.common.resource;

import be.riddler.DemoBackendApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.List;

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
@Import(DemoBackendApplication.class)
public abstract class AbstractResourceTest {
    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    protected String xAuthenticationContent() {
        var authentication = new UsernamePasswordAuthenticationToken("username", "password", List.of((GrantedAuthority) () -> "AMDIN"));
        var authenticationBytes = objectMapper.writeValueAsBytes(authentication);
        return Base64.getEncoder().encodeToString(authenticationBytes);
    }
}
