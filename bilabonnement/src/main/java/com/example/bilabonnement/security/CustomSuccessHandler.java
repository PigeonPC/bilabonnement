
package com.example.bilabonnement.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        String username = authentication.getName();

        // Redirect baseret på brugernavn
        if (username.equals("skade")) {
            response.sendRedirect("/damageDepartment");
            return;
        }
        if (username.equals("data")) {
            response.sendRedirect("/dataRegistration");
            return;
        }
        if (username.equals("forretning")) {
            response.sendRedirect("/businessDeveloper");
            return;
        }
        if (username.equals("økonomi")) {
            response.sendRedirect("/economy");
            return;
        }

        // Standard
        response.sendRedirect("/");
    }
}

