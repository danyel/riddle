package be.riddler.common.repository;

import be.riddler.configuration.TestcontainersConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * AbstractRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DataJpaTest
@Import(value = {TestcontainersConfiguration.class})
@ActiveProfiles("test")
public abstract class AbstractRepositoryTest {
}
