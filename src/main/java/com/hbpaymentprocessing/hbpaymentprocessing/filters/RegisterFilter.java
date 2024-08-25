package com.hbpaymentprocessing.hbpaymentprocessing.filters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountRegistrationRequest;
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
public class RegisterFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Utility utility;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest reqObj = (HttpServletRequest) request;
        // create a HttpServletRequest wrapper class
        CustomizedRequestWrapper customizedRequestWrapper = new CustomizedRequestWrapper(reqObj);
        HttpServletResponse resObj = (HttpServletResponse) response;
        Map<String, String> errors = new HashMap<>();
        String uri = reqObj.getRequestURI();

        try {
            if (!uri.trim().startsWith("/guest/register/".trim())) {
                chain.doFilter(request, response);
            } else {
                errors = this.checkRegistrationRequest(customizedRequestWrapper, errors);
                if (errors.size() > 0) {
                    resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    chain.doFilter(customizedRequestWrapper, response);
                }
            }

        } catch (JsonParseException ex) {
            resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            errors.put("err_message", ex.getMessage());
        } catch (JsonMappingException ex) {
            resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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


    private Map<String, String> checkRegistrationRequest(CustomizedRequestWrapper customizedRequestWrapper, Map<String, String> errors)
            throws IOException {

        // Get data from RequestBody
        // Get the input stream data from request wrapper
        ServletInputStream cachedServletInputStream = customizedRequestWrapper.getInputStream();
        // Convert input stream data to an object
        AccountRegistrationRequest accountRegistrationRequest =
                objectMapper.readValue(cachedServletInputStream, AccountRegistrationRequest.class);

        errors = utility.checkStringError(accountRegistrationRequest.getEmail().trim(),
                ConstantVariables.EMAIL_FORMAT.trim(), errors, false, "email error", "Invalid email format");
        errors = utility.checkStringError(accountRegistrationRequest.getPwd().trim(),
                ConstantVariables.PASSWORD_FORMAT.trim(), errors, false, "password error", "Invalid password format");
        errors = utility.checkStringError(accountRegistrationRequest.getFullName(),
                ConstantVariables.FULL_NAME_FORMAT.trim(), errors, false, "full name error", "Invalid full name format");
        errors = utility.checkStringError(accountRegistrationRequest.getPhoneNumber(),
                ConstantVariables.PHONE_NUMBER_FORMAT.trim(), errors, true, "phone error", "Invalid phone format");
        errors = utility.checkStringError(accountRegistrationRequest.getAddress(),
                ConstantVariables.ADDRESS_FORMAT.trim(), errors, true, "address error", "Invalid address format");
        errors = utility.checkStringError(accountRegistrationRequest.getIdCard(),
                ConstantVariables.ID_CARD_FORMAT.trim(), errors, false, "id card error", "Invalid id card format");
        errors = utility.checkStringError(accountRegistrationRequest.getRoleName(),
                ConstantVariables.ROLE_NAME_FORMAT.trim(), errors, false, "role name error", "Invalid role name format");
        return errors;
    }
}
