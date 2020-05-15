package com.dantefung.springbootmvc.sign;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;//缓存内容

    public HttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    // 重写获取body内容的接口
    @Override
    public ServletInputStream getInputStream() throws IOException {
        String method = getHttpServletRequest().getMethod();
        if (method.equalsIgnoreCase(RequestMethod.GET.name())) {
            return super.getInputStream();
        }
        if (body == null) {
            InputStream in = super.getInputStream();
            body = IOUtils.toByteArray(in);
        }

        return new CacheableServletInputStream(new ByteArrayInputStream(body));
    }

    public static class CacheableServletInputStream extends ServletInputStream {
        ByteArrayInputStream in;

        public CacheableServletInputStream(ByteArrayInputStream in) {
            this.in = in;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            //do nothing
        }

        @Override
        public int read() throws IOException {
            return in.read();
        }

    }
}
