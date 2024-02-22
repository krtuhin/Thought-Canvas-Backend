package com.rootapp.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. get token
        String requestHeader = request.getHeader("Authorization");

        // Bearer 2352345235sdfrsfgsdfsdf
        logger.info(" Header : {}", requestHeader);

        String username = null;
        String token = null;

        // check header value and token
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            // extract token
            token = requestHeader.substring(7);

            try {
                // get username from token
                username = this.jwtTokenHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {

                logger.info("Illegal Argument while fetching the username !!");

            } catch (ExpiredJwtException e) {

                logger.info("Given jwt token is expired !!");

            } catch (MalformedJwtException e) {

                logger.info("Some changed has done in token !! Invalid Token");
            }
        } else {

            logger.info("Invalid Header Value !! ");
        }

        // check user is already authenticated or not
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // fetch userdetails from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token, userDetails)) {

                // do authenticate
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {

                logger.info("validation failed");
            }
        } else {

            logger.info("username or context is null");
        }

        filterChain.doFilter(request, response);
    }

}
