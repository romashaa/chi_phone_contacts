package com.example.chi_phone_contacts;

import com.example.chi_phone_contacts.auth.AuthService;
import com.example.chi_phone_contacts.auth.AuthenticationRequest;
import com.example.chi_phone_contacts.auth.AuthenticationResponse;
import com.example.chi_phone_contacts.auth.RegisterRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AuthService authService;

    @Test
    public void register_ShouldReturnAuthenticationResponse() {
        RegisterRequest registerRequest = new RegisterRequest();

        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        Mockito.when(authService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                "/api/auth/register",
                registerRequest,
                AuthenticationResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void authenticate_ShouldReturnAuthenticationResponse() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        Mockito.when(authService.authenticate(Mockito.any(AuthenticationRequest.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                "/api/auth/authenticate",
                authenticationRequest,
                AuthenticationResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}