package com.oscarmartincol.med_booking_api.security;

import com.oscarmartincol.med_booking_api.entity.User;
import com.oscarmartincol.med_booking_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println(">>> Filtro JWT interceptando: " + request.getRequestURI());

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);
        try {
            String username = jwtTokenProvider.getUsername(token);

            /*User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

            UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);*/

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword()) // required
                    .authorities(authorities)
                    .build();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);


        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                //return;
        }
        System.out.println(">>> Continuando filtro JWT sin autenticación");
        filterChain.doFilter(request, response);


    }
}
