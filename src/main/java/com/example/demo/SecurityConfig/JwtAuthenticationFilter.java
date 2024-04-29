package com.example.demo.SecurityConfig;


import com.example.demo.entity.MasterUser;
import com.example.demo.repositary.MasterUsersRepo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsServiceAdmin;

    @Autowired
    private LoggedUserDetails loggedUserDetails;


    @Autowired
    private MasterUsersRepo usersRepo;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println(request.getServletPath());

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (request.getServletPath().contains("/public") || request.getServletPath().contains("/version")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader;

        Claims claims = jwtService.verifyToken(jwt);
        MasterUser masterUser=usersRepo.findById(Integer.valueOf(claims.getId())).orElse(null);


        loggedUserDetails.setId(masterUser.getId());
        loggedUserDetails.setRole((String) claims.get("Role"));

        if (masterUser!= null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails;

            userDetails = userDetailsServiceAdmin.loadUserByUsername(claims.getSubject());

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}