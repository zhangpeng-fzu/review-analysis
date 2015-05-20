package com.zhangpeng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

/**
 * 发送请求获取商品评论
 * @author MZhang
 * @since 2015-5-6
 *
 */
public class HttpClientUtil {
	
	private static String defaultContentEncoding;
	 
	public HttpClientUtil() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}
	
	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public static HttpClientUtil sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}
	
	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 * @return 响映对象
	 * @throws IOException
	 */
	private static HttpClientUtil send(String urlString, String method,
			Map<String, String> parameters, Map<String, String> propertys)
			throws IOException {
		HttpURLConnection urlConnection = null;
 
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
 
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
 
		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
 
		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}
 
		return makeContent(urlString, urlConnection);
	}
	
	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws IOException
	 */
	private static HttpClientUtil makeContent(String urlString,
			HttpURLConnection urlConnection) throws IOException {
		HttpClientUtil httpClient = new HttpClientUtil();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			httpClient.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpClient.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
 
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = defaultContentEncoding;
 
			httpClient.urlString = urlString;
 
			httpClient.defaultPort = urlConnection.getURL().getDefaultPort();
			httpClient.file = urlConnection.getURL().getFile();
			httpClient.host = urlConnection.getURL().getHost();
			httpClient.path = urlConnection.getURL().getPath();
			httpClient.port = urlConnection.getURL().getPort();
			httpClient.protocol = urlConnection.getURL().getProtocol();
			httpClient.query = urlConnection.getURL().getQuery();
			httpClient.ref = urlConnection.getURL().getRef();
			httpClient.userInfo = urlConnection.getURL().getUserInfo();
 
			httpClient.content = new String(temp.toString().getBytes(), ecod);
			httpClient.contentEncoding = ecod;
			httpClient.code = urlConnection.getResponseCode();
			httpClient.message = urlConnection.getResponseMessage();
			httpClient.contentType = urlConnection.getContentType();
			httpClient.method = urlConnection.getRequestMethod();
			httpClient.connectTimeout = urlConnection.getConnectTimeout();
			httpClient.readTimeout = urlConnection.getReadTimeout();
 
			return httpClient;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
	String urlString;
	int defaultPort;
	String file;
	String host;
	String path;
	int port;
	String protocol;
	String query;
	String ref;
	String userInfo;
	String contentEncoding;
	String content;
	String contentType;
	int code;
	String message;
	String method;
	int connectTimeout;
	int readTimeout;
 
	Vector<String> contentCollection;
 
	public String getContent() {
		return content;
	}
 
	public String getContentType() {
		return contentType;
	}
 
	public int getCode() {
		return code;
	}
 
	public String getMessage() {
		return message;
	}
 
	public Vector<String> getContentCollection() {
		return contentCollection;
	}
 
	public String getContentEncoding() {
		return contentEncoding;
	}
 
	public String getMethod() {
		return method;
	}
 
	public int getConnectTimeout() {
		return connectTimeout;
	}
 
	public int getReadTimeout() {
		return readTimeout;
	}
 
	public String getUrlString() {
		return urlString;
	}
 
	public int getDefaultPort() {
		return defaultPort;
	}
 
	public String getFile() {
		return file;
	}
 
	public String getHost() {
		return host;
	}
 
	public String getPath() {
		return path;
	}
 
	public int getPort() {
		return port;
	}
 
	public String getProtocol() {
		return protocol;
	}
 
	public String getQuery() {
		return query;
	}
 
	public String getRef() {
		return ref;
	}
 
	public String getUserInfo() {
		return userInfo;
	}
 
}
