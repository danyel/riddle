package be.riddler.v1.log.bff;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.jspecify.annotations.NonNull;

/**
 * Logger
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@AnonymousAllowed
@BrowserCallable
public class Logger {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("Vaadin UI");

    public void debug(@NonNull String message, Object... args) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(message, args);
        }
    }

    public void info(@NonNull String message, Object... args) {
        LOGGER.info(message, args);
    }

    public void error(@NonNull String message, Object... args) {
        LOGGER.error(message, args);
    }

    public void warn(@NonNull String message, Object... args) {
        LOGGER.warn(message, args);
    }
}
