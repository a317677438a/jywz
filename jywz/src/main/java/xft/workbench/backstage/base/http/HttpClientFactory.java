package xft.workbench.backstage.base.http;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient工厂类
 * 
 */
public class HttpClientFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpClientFactory.class);

	static {
		System.setProperty("javax.net.debug", "ssl,handshake,trustmanager");
		System.setProperty("jsse.enableSNIExtension", "false");
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
	}

	public static CloseableHttpClient createHttpClient() {
		return HttpClients.createDefault();
	}

	public static CloseableHttpClient createHttpsClientICBC(String path,
			String pwd) throws KeyStoreException, KeyManagementException,
			NoSuchAlgorithmException {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

		InputStream in = null;
		try {
			logger.info("path:" + path + "|pwd:" + pwd);
			// String path = requestEntity.
			// in = HttpClientFactory.class.getResourceAsStream(path);
			FileInputStream file = new FileInputStream(new File(path));
			in = file;

			trustStore.load(in, pwd.toCharArray());
			logger.info("trustStore.size23:" + trustStore.size());

			logger.info("trustStore load sucess...");

		} catch (Exception e) {
			logger.error("load jks error:path:" + path, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close InputStream error", e);
				}
			}
		}
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
				.build();

		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "SSLv3" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();

		return httpClient;
	}

	public static CloseableHttpClient createHttpsClient()
			throws KeyStoreException, KeyManagementException,
			NoSuchAlgorithmException {

		/*
		 * SSLContext ctx = SSLContext.getInstance("TLS"); X509TrustManager tm =
		 * new X509TrustManager() {
		 * 
		 * @Override public void checkClientTrusted(X509Certificate[] chain,
		 * String authType) throws CertificateException { }
		 * 
		 * @Override public void checkServerTrusted(X509Certificate[] chain,
		 * String authType) throws CertificateException { }
		 * 
		 * @Override public X509Certificate[] getAcceptedIssuers() { return
		 * null; } }; ctx.init(null, new TrustManager[]{tm}, null);
		 * SSLSocketFactory sslsf = new
		 * SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		 */

		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
				new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

}
