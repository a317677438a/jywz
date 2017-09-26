package xft.workbench.backstage.base.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {
	public static final String GBK = "GBK";
	public static final String UTF_8 = "UTF-8";
	public static final String CONTENT_TYPE_XML="application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_JOSN="application/json";
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

	public static String sendHttpGet(URI uri) {

		String response = "";

		long stime = System.currentTimeMillis();
		CloseableHttpResponse httpResponse = null;
		try {

			CloseableHttpClient httpClient = HttpClientFactory.createHttpClient();

			HttpGet httpGet = new HttpGet(uri);
			RequestConfig rc = requestConfig();
			httpGet.setConfig(rc);
			httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				response = EntityUtils.toString(httpResponse.getEntity(), GBK);

			}
			httpResponse.close();

			httpClient.close();

		} catch (Exception e) {
			logger.error("System Error", e);
		}finally{
			String state = "9999";
			String reason = "connect fail";
			if(httpResponse != null){
				state = httpResponse.getStatusLine().getStatusCode()+"";
				reason = httpResponse.getStatusLine().getReasonPhrase();
			}
			logger.info(
					"url:{},communication total time:[{}],Http status code: {}, reason phrase: {}",
					uri.toString(), System.currentTimeMillis() - stime,
					state, reason);
		}
		return response;
	}

	public static String sendHttpPost(URI uri, List<NameValuePair> valuePairs) {

		String response = "";
		long stime = System.currentTimeMillis();
		CloseableHttpResponse httpResponse = null;
		try {

			CloseableHttpClient httpClient = HttpClientFactory.createHttpClient();

			HttpPost httpPost = new HttpPost(uri);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, UTF_8);
			httpPost.setEntity(formEntity);
			RequestConfig rc = requestConfig();
			httpPost.setConfig(rc);
			
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				response = EntityUtils.toString(httpResponse.getEntity(), GBK);

			}
			httpResponse.close();

			httpClient.close();

		} catch (Exception e) {
			logger.error("System Error", e);
		}finally{
			String state = "9999";
			String reason = "connect fail";
			if(httpResponse != null){
				state = httpResponse.getStatusLine().getStatusCode()+"";
				reason = httpResponse.getStatusLine().getReasonPhrase();
			}
			logger.info(
					"url:{},communication total time:[{}],Http status code: {}, reason phrase: {} NameValuePair:[{}] response :{}",
					uri.toString(), System.currentTimeMillis() - stime,
					state, reason, valuePairs.toString() , response );
		}
		return response;
	}
	
	public static GenericResponse sendHttpPost(URI uri, List<NameValuePair> valuePairs, boolean enableSSL, String contentType, String encoding) throws Exception{				
		logger.info("http request uri: {}", uri);
		logger.info("http request content: {}", valuePairs);
		
		UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(valuePairs, encoding);
		requestEntity.setContentEncoding(encoding);
		requestEntity.setContentType(contentType);
		
		return sendHttpPost(uri, enableSSL, encoding, requestEntity);
	}
	
	public static GenericResponse sendICBCHttpPost(URI uri, List<NameValuePair> valuePairs, boolean enableSSL, String contentType, String encoding,String path,String pwd) throws Exception{				
		logger.info("http request uri: {}", uri);
		logger.info("http request content: {}", valuePairs);
		
		UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(valuePairs, encoding);
		requestEntity.setContentEncoding(encoding);
		requestEntity.setContentType(contentType);
		
		return _sendICBCHttpPost(uri, enableSSL, encoding, requestEntity,path,pwd);
	}
	
	public static GenericResponse sendHttpsPost(URI uri, String valuePairs, boolean enableSSL, String contentType, String encoding,String path,String pwd) throws Exception{				
		logger.info("http request uri: {}", uri);
		logger.info("http request content: {}", valuePairs);
		
		StringEntity requestEntity = new StringEntity(valuePairs, encoding);
		requestEntity.setContentEncoding(encoding);
		requestEntity.setContentType(contentType);
		
		return _sendHttpsPost(uri, enableSSL, encoding, requestEntity,path,pwd);
	}
	
	private static GenericResponse _sendHttpsPost(URI uri, boolean enableSSL,
			String encoding, StringEntity requestEntity,String path,String pwd)
			throws Exception, IOException {
		GenericResponse response = new GenericResponse();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		HttpPost post = null;
		long stime = System.currentTimeMillis();
		try {
			if(enableSSL){
				httpClient = HttpClientFactory.createHttpsClient();
			}else{
				httpClient = HttpClientFactory.createHttpClient();
			}			

			post = new HttpPost(uri);
			post.setEntity(requestEntity);
			
			RequestConfig rc = requestConfig();
			post.setConfig(rc);
			
			httpResponse = httpClient.execute(post);
			//InputStream in = httpResponse.getEntity().getContent();
			
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//InputStream in = .getContent();
				String content = _toString(httpResponse.getEntity(),encoding);
				logger.info("icbc rsp content_cc: "+content);
				
				
				if(content==null){
				    response.setSuccess(false);
				    response.setContent("");
				}else{
				    response.setContent(content);
				    response.setSuccess(true); 
				}
				
			}else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND){
				logger.error("Http status code: {}, reason phrase: {} ", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				response.setSuccess(false);
				response.setContent("");
			}
			else{
				logger.error("Http status code: {}, reason phrase: {} ", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				response.setSuccess(false);
				response.setContent("");
			}
			 
		}  catch (Exception e) {
			logger.error("System Error:", e);
			throw e;
		}
		finally{
			String state = "9999";
			String reason = "connect fail";
			if(httpResponse != null){
				state = httpResponse.getStatusLine().getStatusCode()+"";
				reason = httpResponse.getStatusLine().getReasonPhrase();
			}
			logger.info(
					"url:{},communication total time:[{}],Http status code: {}, reason phrase: {}",
					uri.toString(), System.currentTimeMillis() - stime,
					state, reason);
			
			try {
				if(post != null) {
					post.abort();
				}
				if(httpResponse != null) {
					EntityUtils.consumeQuietly(httpResponse.getEntity());
				}
				if(httpClient != null){
					httpClient.close();
				}
			} catch (Exception e) {
				logger.error("System Error:", e);
				throw e;
			}
			
		}
		
		logger.info("http response content: {}", response.getContent());
		return response;
	}
	
	private static GenericResponse _sendICBCHttpPost(URI uri, boolean enableSSL,
			String encoding, StringEntity requestEntity,String path,String pwd)
			throws Exception, IOException {
		GenericResponse response = new GenericResponse();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		HttpPost post = null;
		long stime = System.currentTimeMillis();
		try {
			if(enableSSL){
				httpClient = HttpClientFactory.createHttpsClientICBC(path,pwd);
			}
			else{
				httpClient = HttpClientFactory.createHttpClient();
			}			

			post = new HttpPost(uri);
			post.setEntity(requestEntity);
			
			RequestConfig rc = requestConfig();
			post.setConfig(rc);
			
			httpResponse = httpClient.execute(post);
			//InputStream in = httpResponse.getEntity().getContent();
			
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//InputStream in = .getContent();
				String content = _toString(httpResponse.getEntity(),encoding);
				logger.info("icbc rsp content_cc: "+content);
				
				
				if(content==null){
				    response.setSuccess(false);
				    response.setContent("");
				}else{
				    response.setContent(content);
				    response.setSuccess(true); 
				}
				
			}else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND){
				logger.error("Http status code: {}, reason phrase: {} ", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				response.setSuccess(false);
				response.setContent("");
			}
			else{
				logger.error("Http status code: {}, reason phrase: {} ", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				response.setSuccess(false);
				response.setContent("");
			}
			 
		}  catch (Exception e) {
			logger.error("System Error:", e);
			throw e;
		}
		finally{
			String state = "9999";
			String reason = "connect fail";
			if(httpResponse != null){
				state = httpResponse.getStatusLine().getStatusCode()+"";
				reason = httpResponse.getStatusLine().getReasonPhrase();
			}
			logger.info(
					"url:{},communication total time:[{}],Http status code: {}, reason phrase: {}",
					uri.toString(), System.currentTimeMillis() - stime,
					state, reason);
			
			try {
				if(post != null) {
					post.abort();
				}
				if(httpResponse != null) {
					EntityUtils.consumeQuietly(httpResponse.getEntity());
				}
				if(httpClient != null){
					httpClient.close();
				}
			} catch (Exception e) {
				logger.error("System Error:", e);
				throw e;
			}
			
		}
		
		logger.info("http response content: {}", response.getContent());
		return response;
	}


	private static GenericResponse sendHttpPost(URI uri, boolean enableSSL,
			String encoding, StringEntity requestEntity)
			throws Exception, IOException {
		GenericResponse response = new GenericResponse();
		CloseableHttpClient httpClient = null;
		long stime = System.currentTimeMillis();
		CloseableHttpResponse httpResponse = null;
		try {
			if(enableSSL){
				httpClient = HttpClientFactory.createHttpsClientICBC("","");
			}
			else{
				httpClient = HttpClientFactory.createHttpClient();
			}			

			HttpPost post = new HttpPost(uri);
			post.setEntity(requestEntity);
			
			RequestConfig rc = requestConfig();
			post.setConfig(rc);
			
			httpResponse = httpClient.execute(post);
			//InputStream in = httpResponse.getEntity().getContent();
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				response.setContent(EntityUtils.toString(httpResponse.getEntity(), encoding));
				response.setSuccess(true);
			}else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND){
				logger.error("Http status code: {}, reason phrase: {} ", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				response.setSuccess(false);
				response.setContent("");
			}
			else{
				logger.error("Http status code: {}, reason phrase: {} ", httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
				response.setSuccess(false);
				response.setContent("");
			}
			
			httpResponse.close();
			httpClient.close();
		}  catch (Exception e) {
			logger.error("System Error:", e);
			throw e;
		}
		finally{
			String state = "9999";
			String reason = "connect fail";
			if(httpResponse != null){
				state = httpResponse.getStatusLine().getStatusCode()+"";
				reason = httpResponse.getStatusLine().getReasonPhrase();
			}
			logger.info(
					"url:{},communication total time:[{}],Http status code: {}, reason phrase: {}",
					uri.toString(), System.currentTimeMillis() - stime,
					state, reason);
			
			if(httpClient != null){
				try{
					httpClient.close();
				}
				catch(IOException e){
					logger.error("System Error:", e);
					throw e;
				}
			}
		}
		
		logger.info("http response content: {}", response.getContent());
		return response;
	}

	/**
	 * post方式发送http/json请求
	 * @param uri 目标URL
	 * @param reqContent 待发送数据
	 * @param enableSSL true:https; false:http
	 * @return 成功返回请求结果；失败则返回null
	 * @throws Exception
	 */
	public static GenericResponse sendHttpPost(URI uri, String reqContent, boolean enableSSL) throws Exception{
		return sendHttpPost(uri, reqContent, enableSSL, CONTENT_TYPE_JOSN, UTF_8);
	}
	
	public static GenericResponse sendHttpPost(URI uri, String reqContent, boolean enableSSL, String contentType, String encoding) throws Exception{
		logger.info("http request uri: {}", uri);
		logger.info("http request content: {}", reqContent);
		
		StringEntity requestEntity = new StringEntity(reqContent, encoding);
		requestEntity.setContentEncoding(encoding);
		requestEntity.setContentType(contentType);
		
		return sendHttpPost(uri, enableSSL, encoding, requestEntity);		
	}
	
	public static String _toString(HttpEntity entity,String encoding) {
		InputStream instream = null;
		try {
			instream = entity.getContent();
			final ContentType contentType = ContentType.getOrDefault(entity);
			Charset charset = contentType.getCharset();
			
			if (charset == null) {
                charset = Charset.forName(encoding);
            }
			
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			final StringBuilder b = new StringBuilder();
			final char[] tmp = new char[1024];
			final Reader reader = new InputStreamReader(instream, charset);
			try {
				int l;
				while ((l = reader.read(tmp)) != -1) {
					b.append(tmp, 0, l);
				}
			} catch (final Exception ignore) {
				//logger.error("_toString read is error",ignore);
			}
			return b.toString();
		}catch(Exception e){
			//logger.error("_toString is error",e);
		}finally {
			try {
				instream.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	private static RequestConfig requestConfig() {
		int connectTimeout = 30*1000;
		int connectionRequestTimeout = 30*1000;
		int socketTimeout = 60*1000;
		
		RequestConfig rc = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout).build();
		return rc;
	}
}
