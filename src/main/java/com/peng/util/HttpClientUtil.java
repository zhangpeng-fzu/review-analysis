package com.peng.util;

import com.peng.config.Config;
import com.peng.entity.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送请求获取商品评论
 * @author MZhang
 * @since 2015-5-6
 *
 */
public class HttpClientUtil {
	/**
	 * 发送GET请求
	 * @param urlString URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public static ResponseEntity sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}
	
	/**
	 * 发送HTTP请求
	 * @param urlString
	 * @return 响映对象
	 * @throws IOException
	 */
	private static ResponseEntity send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys)
			throws IOException {
		HttpURLConnection connecttion;
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
		connecttion = (HttpURLConnection) url.openConnection();

		connecttion.setRequestProperty("accept", "*/*");
		connecttion.setRequestProperty("cookie", Config.COOKIE);
		connecttion.setRequestProperty("connection", "Keep-Alive");
		connecttion.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
 
		connecttion.setRequestMethod(method);
		connecttion.setDoOutput(true);
		connecttion.setDoInput(true);
		connecttion.setUseCaches(false);
 
		if (propertys != null)
			for (String key : propertys.keySet()) {
				connecttion.addRequestProperty(key, propertys.get(key));
			}
 
		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			connecttion.getOutputStream().write(param.toString().getBytes());
			connecttion.getOutputStream().flush();
			connecttion.getOutputStream().close();
		}
 
		return makeContent(urlString, connecttion);
	}
	
	/**
	 * 得到响应对象
	 * 
	 * @param connecttion
	 * @return 响应对象
	 * @throws IOException
	 */
	private static ResponseEntity makeContent(String urlString, HttpURLConnection connecttion) throws IOException {
		ResponseEntity response = new ResponseEntity();
		try {
			InputStream in = connecttion.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			List<String> list = new ArrayList<>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				list.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			response.setContentCollection(list);
			bufferedReader.close();

			response.setUrlString(urlString);
			response.setDefaultPort(connecttion.getURL().getDefaultPort());
			response.setFile(connecttion.getURL().getFile());
			response.setHost(connecttion.getURL().getHost());
			response.setPath(connecttion.getURL().getPath());
			response.setPort(connecttion.getURL().getPort());
			response.setProtocol(connecttion.getURL().getProtocol());
			response.setQuery(connecttion.getURL().getQuery());
			response.setRef(connecttion.getURL().getRef());
			response.setUserInfo(connecttion.getURL().getUserInfo());
			response.setContent(new String(temp.toString().getBytes()));
			response.setCode(connecttion.getResponseCode());
			response.setMessage(connecttion.getResponseMessage());
			response.setContentType(connecttion.getContentType());
			response.setMethod(connecttion.getRequestMethod());
			response.setConnectTimeout(connecttion.getConnectTimeout());
			response.setReadTimeout(connecttion.getReadTimeout());
 
			return response;
		} catch (IOException e) {
			throw e;
		} finally {
			if (connecttion != null)
				connecttion.disconnect();
		}
	}
	
}
