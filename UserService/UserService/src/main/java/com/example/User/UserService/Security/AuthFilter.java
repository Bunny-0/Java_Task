package com.example.User.UserService.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthnServiceOutboundCommunication authnService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String url=request.getRequestURL().toString();
        if(!url.contains("/api/v1/")){
            filterChain.doFilter(request, response);
        }
        String userResp = null;
        String token = null;
        ObjectMapper mapper = new ObjectMapper();
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {

                userResp = authnService.getUserInfo(token);
                if (userResp != null) {
                    JSONObject jsonResp = new JSONObject(userResp);
                    if(jsonResp.has("sub")){
                        String loggedInUser = jsonResp.getString("sub");

                        filterChain.doFilter(request, response);

                    } else {
                        Map<String, Object> errorDetails = new HashMap<>();
                        errorDetails.put("message", "No User Data Found");

                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                        mapper.writeValue(response.getWriter(), errorDetails);
                    }
                }else {
                    Map<String, Object> errorDetails = new HashMap<>();
                    errorDetails.put("message", "Invalid Token");

                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    mapper.writeValue(response.getWriter(), errorDetails);
                }


            } catch (IllegalArgumentException e) {
                logger.info("Exception Occured");
                e.printStackTrace();
            }


        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Token Not Passed");

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
        }

    }
}
