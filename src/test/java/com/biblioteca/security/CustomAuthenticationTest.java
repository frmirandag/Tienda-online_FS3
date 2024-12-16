package com.biblioteca.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CustomAuthenticationEntryPointTest {

    @Test
    void testCommence() throws IOException {
        // Crear los mocks de HttpServletRequest y HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        // Crear una instancia de CustomAuthenticationEntryPoint
        CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();
        
        // Llamar al método commence
        entryPoint.commence(request, response, new AuthenticationException("Unauthorized") {});
        
        // Verificar que el código de estado 401 se haya establecido
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // Verificar que el mensaje personalizado se haya escrito en la respuesta
        verify(response.getWriter()).write("Error 401: No estás autenticado. Por favor, proporciona credenciales válidas.");
    }
}
