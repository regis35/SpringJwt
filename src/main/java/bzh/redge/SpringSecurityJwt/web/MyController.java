package bzh.redge.SpringSecurityJwt.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {

    @GetMapping
    public String home(Principal principal) {
        return "Jwt application "+principal.getName();
    }

}
