package com.app.pocket.configuration;

import com.app.pocket.services.JwtService;
import com.app.pocket.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is our spring security custom filter, each request goes through the filter before reaching controller
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Inside JwtRequestFilter.doFilterInternal");

        final String header = request.getHeader("Authorization");
        String jwtToken = null;
        String userName =  null;

        /**
         * This block reads the userName from token
         */
        if(header != null && header.startsWith("Bearer ")){
            jwtToken = header.substring(7);
            try{
                userName = jwtUtil.getUserNameFromToken(jwtToken);

            }catch(IllegalArgumentException e){
                System.out.println("Unable to get JWT token");
            }catch(ExpiredJwtException e){
                System.out.println("JWT token is expired");
            }
        }else{
            System.out.println("Jwt token doesn't start with Bearer");
        }

        /**
         * This loop executes when token has userName and when we need to validate this particular request
         * Gets the userDetails and authenticates token against it and sets up security context if token is validated
         */
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("Inside JwtRequestFilter, when token has value");
            UserDetails userDetails = jwtService.loadUserByUsername(userName);

            /**
             * Typical username password authentication protocol in spring security
             */
            if(jwtUtil.validateToken(jwtToken, userDetails)){

               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                       new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request,response);
    }

}
