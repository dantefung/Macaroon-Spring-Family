package com.dantefung.springbootmvc.sign;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream buffer;

    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
        buffer = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new WrapperOutputStream(super.getOutputStream());
    }


    public byte[] getContent()  {
        return buffer.toByteArray();
    }

    class WrapperOutputStream extends ServletOutputStream {
        private ServletOutputStream bos;

        public WrapperOutputStream(ServletOutputStream bos) {
            this.bos = bos;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
            buffer.write(b);
        }

        @Override
        public boolean isReady() {
            return bos.isReady();
        }

        @Override
        public void setWriteListener(WriteListener arg0) {
            bos.setWriteListener(arg0);
        }

    }

}
