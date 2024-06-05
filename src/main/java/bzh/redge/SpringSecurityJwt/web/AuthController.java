package bzh.redge.SpringSecurityJwt.web;

import bzh.redge.SpringSecurityJwt.service.TokenService;
import bzh.redge.SpringSecurityJwt.web.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public AuthResponse token(Authentication auth) {
        log.debug("token request for user -> {}", auth.getName());
        String token = tokenService.generateToken(auth);
        log.debug("token generated for user -> {}", token);

        return new AuthResponse(token);
    }

}
