package be.riddler.v1.security.bff;

import be.riddler.v1.common.http.Unauthorized;
import be.riddler.v1.security.api.SecurityClient;
import be.riddler.v1.security.api.UserInfo;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * UserService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserEndpoint {
    private final SecurityClient securityClient;

    public @NonNull UserInfo getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Assert.isTrue(auth != null, "Cannot pass null");
        return new UserInfo(
                auth.getName(),
                auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    public UserInfo authenticate(String username, String password) {
        var authentication = securityClient.authenticate(username, password);
        if (authentication instanceof UserInfo userInfo) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.roles().stream().map(SimpleGrantedAuthority::new).toList()));
            return authentication;
        }
        throw new Unauthorized("Incorrect username and/or password");
    }
}
