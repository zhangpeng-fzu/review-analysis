package com.peng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

import com.peng.java.HttpResponse;

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
	 * @param urlString URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public static HttpResponse sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}
	
	/**
	 * 发送HTTP请求
	 * @param urlString
	 * @return 响映对象
	 * @throws IOException
	 */
	private static HttpResponse send(String urlString, String method,
			Map<String, String> parameters, Map<String, String> propertys)
			throws IOException {
		HttpURLConnection urlConnection = null;
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0){
					param.append("?");
				} else{
					param.append("&");
				}
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
	private static HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
		HttpResponse response = new HttpResponse();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			Vector<String> v = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				v.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			response.setContentCollection(v);
			bufferedReader.close();
 
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null){
				ecod = defaultContentEncoding;
			}

			response.setUrlString(urlString);
 
			response.setDefaultPort(urlConnection.getURL().getDefaultPort());
			response.setFile(urlConnection.getURL().getFile());
			response.setHost(urlConnection.getURL().getHost());
			response.setPath(urlConnection.getURL().getPath());
			response.setPort(urlConnection.getURL().getPort());
			response.setProtocol(urlConnection.getURL().getProtocol());
			response.setQuery(urlConnection.getURL().getQuery());
			response.setRef(urlConnection.getURL().getRef());
			response.setUserInfo(urlConnection.getURL().getUserInfo());
			response.setContent(new String(temp.toString().getBytes(), ecod));
			response.setContentEncoding(ecod);
			response.setCode(urlConnection.getResponseCode());
			response.setMessage(urlConnection.getResponseMessage());
			response.setContentType(urlConnection.getContentType());
			response.setMethod(urlConnection.getRequestMethod());
			response.setConnectTimeout(urlConnection.getConnectTimeout());
			response.setReadTimeout(urlConnection.getReadTimeout());
 
			return response;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
	
}
