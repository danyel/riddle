package be.riddler.v1.security.bff;

import be.riddler.v1.common.http.Unauthorized;
import be.riddler.v1.security.api.SecurityApi;
import be.riddler.v1.security.api.UserInfo;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

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
    private final SecurityApi securityApi;

    public @NonNull UserInfo getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        //noinspection NullableProblems,DataFlowIssue
        return new UserInfo(
                auth.getName(),
                auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }

    public UserInfo authenticate(String username, String password) {
        var authentication = securityApi.authenticate(username, password);
        if (authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            var user = usernamePasswordAuthenticationToken.getPrincipal();
            if (Objects.isNull(user)) {
                throw new Unauthorized("Incorrect username and/or password");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //noinspection NullableProblems,DataFlowIssue
            return new UserInfo(((User) user).getUsername(), ((User) user).getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
        }
        throw new Unauthorized("Incorrect username and/or password");
    }
}
