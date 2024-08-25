package com.hbpaymentprocessing.hbpaymentprocessing.filters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountLoginRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.ConstantVariables;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.CustomizedRequestWrapper;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Utility utility;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest reqObj = (HttpServletRequest) request;
        HttpServletResponse resObj = (HttpServletResponse) response;
        Map<String, String> errors = new HashMap<>();
        String uri = reqObj.getRequestURI();

        try {
            if (!uri.trim().startsWith("/guest/login/".trim())) {
                chain.doFilter(request, response);
            } else {
                CustomizedRequestWrapper customizedRequestWrapper = new CustomizedRequestWrapper(reqObj);
                errors = this.checkLoginRequest(customizedRequestWrapper, errors);
                if (errors.size() > 0) {
                    resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    chain.doFilter(customizedRequestWrapper, response);
                }
            }

        } catch (JsonParseException ex) {
            resObj.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } catch (JsonMappingException ex) {
            resObj.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } catch (IOException ex) {
            resObj.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } catch (Exception ex) {
            resObj.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } finally {
            if (errors.size() > 0) {
                resObj.setContentType("application/json");
                objectMapper.writeValue(resObj.getOutputStream(), errors);
            }
        }
    }

    private Map<String, String> checkLoginRequest(CustomizedRequestWrapper customizedRequestWrapper,
                                                  Map<String, String> errors) throws IOException {

        ServletInputStream cachedServletInputStream = customizedRequestWrapper.getInputStream();
        AccountLoginRequest accountLoginRequest = objectMapper
                .readValue(cachedServletInputStream, AccountLoginRequest.class);

        errors = utility.checkStringError(accountLoginRequest.getEmail(), ConstantVariables.EMAIL_FORMAT.trim(), errors,
                false, "email error", "Invalid email format");
        errors = utility.checkStringError(accountLoginRequest.getPwd(), ConstantVariables.PASSWORD_FORMAT.trim(), errors,
                false, "password error", "Invalid password format");
        return errors;
    }
}
