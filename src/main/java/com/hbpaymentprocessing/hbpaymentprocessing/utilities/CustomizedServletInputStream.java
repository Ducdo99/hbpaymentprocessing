package com.hbpaymentprocessing.hbpaymentprocessing.utilities;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomizedServletInputStream extends ServletInputStream {

    private InputStream cachedBodyInputStream;

    public CustomizedServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        try {
            return this.cachedBodyInputStream.available() == 0;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
    }

    @Override
    public int read() throws IOException {
        return this.cachedBodyInputStream.read();
    }
}
