package com.hbpaymentprocessing.hbpaymentprocessing.utilities;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;


public class CustomizedRequestWrapper extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public CustomizedRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream inputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(inputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomizedServletInputStream(this.cachedBody);
    }

}
