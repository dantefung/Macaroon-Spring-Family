package com.dantefung.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Description:
 * @Author: DANTE FUNG
 * @Date: 2021/3/3 15:37
 * @since JDK 1.8
 * @return:
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

	@Override
	public String getDescription() {
		return null;
	}
}
