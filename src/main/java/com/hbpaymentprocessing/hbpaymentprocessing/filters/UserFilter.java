package com.hbpaymentprocessing.hbpaymentprocessing.filters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.ConstantVariables;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Utility utility;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqObj = (HttpServletRequest) request;
        HttpServletResponse resObj = (HttpServletResponse) response;
        Map<String, String> errors = new HashMap<>();
        String uri = reqObj.getRequestURI().trim();

        try {
            if (!uri.trim().startsWith("/user/".trim())) {
                chain.doFilter(request, response);
            } else {
                errors = this.checkUserRequests(reqObj, errors, uri);
                if (errors.size() > 0) {
                    resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    chain.doFilter(request, response);
                }
            }
        } catch (JsonParseException ex) {
            ex.printStackTrace();
            resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            errors.put("err_message", ex.getMessage());
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
            resObj.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            errors.put("err_message", ex.getMessage());
        } catch (ServletException ex) {
            resObj.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } catch (IOException ex) {
            resObj.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.put("err_message", ex.getMessage());
        } finally {
            if (!errors.isEmpty()) {
                resObj.setContentType("application/json");
                objectMapper.writeValue(resObj.getOutputStream(), errors);
            }
        }
    }

    private Map<String, String> checkUserRequests(HttpServletRequest request, Map<String, String> errors, String uri)
            throws IOException {

        if (uri.trim().startsWith("/user/invoice-payment/".trim())) {
            errors = this.checkInvoicePaymentParams(request, errors);
        }
        return errors;
    }


    private Map<String, String> checkInvoicePaymentParams(HttpServletRequest request,
                                                          Map<String, String> errors) throws IOException {

        errors = utility.checkStringError(request.getParameter("invoiceCode".trim()).trim(),
                ConstantVariables.INVOICE_CODE_FORMAT.trim(), errors, false, "invoice code error", "Invoice code format");
        errors = utility.checkDecimalNumberError(request.getParameter("total".trim()).trim(),
                ConstantVariables.DECIMAL_NUMBER_FORMAT.trim(), errors, false, "total error", "Invalid total format", "Invalid total value");
        errors = utility.checkStringError(request.getParameter("description".trim()).trim(),
                ConstantVariables.DESCRIPTION_FORMAT.trim(), errors, false, "description error", "Invalid description format");
        errors = utility.checkStringError(request.getParameter("tagName".trim()).trim(),
                ConstantVariables.TAG_NAME_FORMAT.trim(), errors, true, "tag name error", "Invalid tag name format");
        errors = utility.checkStringError(request.getParameter("senderAccountNumberCode".trim()).trim(),
                ConstantVariables.ACCOUNT_NUMBER_CODE_FORMAT.trim(), errors, false, "sender account number code error", "Invalid sender account number code format");
        errors = utility.checkStringError(request.getParameter("receiverAccountNumberCode".trim()).trim(),
                ConstantVariables.ACCOUNT_NUMBER_CODE_FORMAT.trim(), errors, false, "receiver account number code error", "Invalid receiver account number code format");
        return errors;
    }

}
