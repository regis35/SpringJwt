package bzh.redge.SpringSecurityJwt.web;

import bzh.redge.SpringSecurityJwt.conf.SecurityConfiguration;
import bzh.redge.SpringSecurityJwt.service.TokenService;
import bzh.redge.SpringSecurityJwt.web.response.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MyController.class, AuthController.class})
@Import({SecurityConfiguration.class, TokenService.class})
class MyControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should401WhenUnauthorized() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateTokenAndCallHome() throws Exception {
        MvcResult result = this.mvc.perform(post("/token")
                .with(httpBasic("regis","secret")))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);
        assertNotNull(authResponse);
        assertNotNull(authResponse.token());

        this.mvc.perform(get("/")
                        .header("Authorization", STR."Bearer \{authResponse.token()}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Jwt application regis"));
    }

    @Test
    @WithMockUser
    void shouldCallHomeWithMockUser() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

}