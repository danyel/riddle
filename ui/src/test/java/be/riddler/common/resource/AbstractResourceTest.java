package be.riddler.common.resource;

import be.riddler.configuration.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import tools.jackson.databind.ObjectMapper;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
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
@Import({TestcontainersConfiguration.class})
public abstract class AbstractResourceTest {
    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUpSslWebTestClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
            this.webTestClient = WebTestClient.bindToServer(new JdkClientHttpConnector(httpClient))
                    .baseUrl("https://localhost:" + port)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize insecure testing SSL Context", e);
        }
    }
    protected String xAuthenticationContent() {
        var authentication = new UsernamePasswordAuthenticationToken("username", "password", List.of((GrantedAuthority) () -> "AMDIN"));
        var authenticationBytes = objectMapper.writeValueAsBytes(authentication);
        return Base64.getEncoder().encodeToString(authenticationBytes);
    }
}
