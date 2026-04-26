package be.riddler.domain.user.bff;

import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * UserService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@BrowserCallable
@PermitAll
public class UserService {
    public UserInfo getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return new UserInfo(
                auth.getName(),
                auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }

    public record UserInfo(String name, List<String> roles) {
    }
}
