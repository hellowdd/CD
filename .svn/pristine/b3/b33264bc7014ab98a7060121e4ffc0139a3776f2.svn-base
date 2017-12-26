package com.bocom.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private static Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
	private static String charset = "UTF-8";

	public static String decodeBase64(String str) {
		try {
			return new String(Base64.decodeBase64(str), charset);
		} catch (UnsupportedEncodingException e) {
			LOG.error("HttpClientUtil.decodeBase64 error", e);
		}
		return null;
	}

	public static String getBase64(String url) {
		return decodeBase64(get(url));
	}

	public static String postBase64(String url, String json) {
		return decodeBase64(post(url, json));
	}

	public static ResponseVo postBase64Vo(String url, String json) {
		String res = decodeBase64(post(url, json));
		if(StringUtils.isNotBlank(res)){
			return JsonUtil.readValue(res, ResponseVo.class);
		}
		return ResponseVoUtil.fail("接口无数据", null);
	}
	
	public static String postBase64(String url, String json,String charset) {
		return decodeBase64(post1(url, json,charset));
	}

	public static String get(String url) {
		return get(url, null, charset);
	}

	public static String post1(String url, String json,String charset) {
		return post(url, json, charset);
	}
	
	public static String post(String url, String json) {
		return post(url, json, charset);
	}

	public static String get(String url, Map<String, String> params) {
		return get(url, params, charset);
	}

	public static String get(String url, Map<String, String> params,
			String charset) {
		LOG.info("HttpClientUtil.get url:" + url + ",charset:" + charset);
		String result = null;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			// 设置参数
			if (null != params && params.size() > 0) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator
							.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem
							.getValue()));
				}
				URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());
				if (list.size() > 0) {
					uriBuilder.setParameters(list);
				}
				httpGet.setURI(uriBuilder.build());
			}
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置请求和传输超时时间
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			LOG.info("HttpClientUtil.get error", e);
		}
		return result;
	}

	public static String post(String url, String json, String charset) {
		LOG.info("HttpClientUtil.post url:" + url + ",json:" + json
				+ ",charset:" + charset);
		String result = null;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			// 设置参数
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			StringEntity entity = new StringEntity(json);
			entity.setContentEncoding(charset);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			LOG.info("HttpClientUtil.post error", e);
		}
		return result;
	}

	public static void main(String[] args) {
		String string = get("http://10.37.149.105:8282/site/org/whPoliceStation");
		System.out.println(string);
	}
}
