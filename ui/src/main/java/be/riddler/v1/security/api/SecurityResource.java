package be.riddler.v1.security.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(String username, String password) {
        // TODO do better authentication control and check if the password is correct.
        var userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
