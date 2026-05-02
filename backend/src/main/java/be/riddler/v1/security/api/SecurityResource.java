package be.riddler.v1.security.api;

import be.riddler.api.v1.security.SecurityApi;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecurityResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequestMapping(path = "/v1/security")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SecurityResource implements SecurityApi {
    private final UserDetailsManager userDetailsManager;

    @Override
    public UsernamePasswordAuthenticationToken authenticate(String username, String password) {
        // TODO do better authentication control and check if the password is correct.
        var userDetails = userDetailsManager.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
