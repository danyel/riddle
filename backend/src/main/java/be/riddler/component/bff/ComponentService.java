package be.riddler.component.bff;

import be.riddler.component.resource.ComponentsApi;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Stream;

/**
 * ComponentService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ComponentService {
    private final ComponentsApi componentsApi;

    public @NonNull List<@NonNull String> icons() {
        return componentsApi.icons();
    }
}
