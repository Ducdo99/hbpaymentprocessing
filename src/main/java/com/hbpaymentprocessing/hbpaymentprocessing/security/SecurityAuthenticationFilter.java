package com.hbpaymentprocessing.hbpaymentprocessing.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbpaymentprocessing.hbpaymentprocessing.services.UserDetailsServiceImpl;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.ConstantVariables;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.JWTUtil;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.Utility;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
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
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {
    // This class is a customized filter that validates a token string.

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private Utility utility;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        String uri = request.getRequestURI();
        try {
            // check all requests from an authorized user
            if (uri.trim().startsWith("/guest/".trim())) {
                filterChain.doFilter(request, response);
            } else {
                // get token from Bearer string
                String jwtString = jwtUtil.getJWTStringFromHeader(request);
                if (jwtString != null) {
                    // check jwt string validation
                    if (jwtUtil.checkJWTExpirationTime(jwtString.trim())) {
                        String email = jwtUtil.getEmailFromJWTString(jwtString.trim());
                        String roleIDStr = jwtUtil.getRoleID(jwtString.trim());
                        if (utility.validateInputString(email.trim(), ConstantVariables.EMAIL_FORMAT.trim())
                                & utility.validateInputString(
                                roleIDStr.trim(), ConstantVariables.INTEGER_NUMBER_FORMAT.trim())) {
                            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                                // Creating a new Authentication object
                                // An UsernamePasswordAuthenticationToken object is also an Authentication object
                                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email.trim());
                                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                        new UsernamePasswordAuthenticationToken(userDetails,
                                                null, userDetails.getAuthorities());
                                usernamePasswordAuthenticationToken.setDetails(
                                        new WebAuthenticationDetailsSource().buildDetails(request));
                                securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                            }
                        } // end if role id is a number, and email string matches regex string
                        filterChain.doFilter(request, response);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    errors.put("err_message", "The invalid token");
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } catch (NumberFormatException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            errors.put("err_message", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            errors.put("err_message", "This token expired");
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } finally {
            if (errors.size() > 0) {
                response.setContentType("application/json");
                objectMapper.writeValue(response.getOutputStream(), errors);
            }
        }
    }
}

