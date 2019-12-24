package com.kthcorp.commons.http;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.kthcorp.commons.lang.CloseableUtils;
import com.kthcorp.commons.lang.http.HttpClientManager;

@SuppressWarnings("all")
public class HttpClientManagerTest {

	private static final Logger log = LoggerFactory.getLogger(HttpClientManagerTest.class);

	private String url = "https://www.google.co.kr";

	@Test
	@Ignore
	@Order(1)
	public void test() throws IOException, GeneralSecurityException {
		CloseableHttpResponse httpResponse = null;
		HttpClientManager httpManager = null;
		try {
			httpManager = HttpClientManager.get();
			httpManager.setConnectTimeout(2 * 1000);
			httpManager.setSocketTimeout(5 * 1000);

			httpResponse = httpManager.execute(url);
			log.debug(EntityUtils.toString(httpResponse.getEntity()));
		} finally {
			CloseableUtils.closeQuietly(httpResponse);
			CloseableUtils.closeQuietly(httpManager);
		}
	}

}
