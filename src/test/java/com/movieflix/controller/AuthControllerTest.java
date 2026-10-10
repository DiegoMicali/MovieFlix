package com.movieflix.controller;


import com.movieflix.config.TokenService;
import com.movieflix.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenService tokenService;

    @Test
    public void should_return_400_login() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/movieflix/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "invalid-email",
                            "password": "password123"
                        }
                        """)
        ).andExpect(
                MockMvcResultMatchers
                        .status().isBadRequest()
        );}


    @Test
    public void should_return_400_register()throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/movieflix/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                "{
                                "name":"dummyName",
                                "email":"   ",
                                "password":"password123"}
                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}