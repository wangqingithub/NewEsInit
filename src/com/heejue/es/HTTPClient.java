package com.heejue.es;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HTTPClient {

	public String sendPost(String url, String param) {
		boolean isSuccess = false;

		HttpPut put = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			// 设置超时时间
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

			put = new HttpPut(url);
			// 构造消息头
			put.setHeader("Content-type", "application/json; charset=utf-8");
			put.setHeader("Connection", "Close");

			// 构建消息实体
			StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));
			entity.setContentEncoding("UTF-8");
			// 发送Json格式的数据请求
			entity.setContentType("application/json");
			put.setEntity(entity);

			HttpResponse response = httpClient.execute(put);

			// 检验返回码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("请求出错: " + statusCode);
				HttpEntity entity1 = response.getEntity();
				String result = EntityUtils.toString(entity1);
				
				System.out.println(result);
				isSuccess = false;
			} else {
				System.out.println(statusCode);
				HttpEntity entity1 = response.getEntity();
				
				String result = EntityUtils.toString(entity1);
				System.out.println(result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		System.out.println(isSuccess);
		return "";
	}

	@SuppressWarnings("unused")
	public HttpResponse doDelete(String urlToRead) throws Exception {
		HttpResponse response = new HttpResponse() {
			@Override
			public void setParams(HttpParams arg0) {}

			@Override
			public void setHeaders(Header[] arg0) {}

			@Override
			public void setHeader(String arg0, String arg1) {}

			@Override
			public void setHeader(Header arg0) {}

			@Override
			public void removeHeaders(String arg0) {}

			@Override
			public void removeHeader(Header arg0) {}

			@Override
			public HeaderIterator headerIterator(String arg0) {
				return null;
			}

			@Override
			public HeaderIterator headerIterator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ProtocolVersion getProtocolVersion() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HttpParams getParams() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Header getLastHeader(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Header[] getHeaders(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Header getFirstHeader(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Header[] getAllHeaders() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean containsHeader(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void addHeader(String arg0, String arg1) {}

			@Override
			public void addHeader(Header arg0) {}

			@Override
			public void setStatusLine(ProtocolVersion arg0, int arg1, String arg2) {}

			@Override
			public void setStatusLine(ProtocolVersion arg0, int arg1) {}

			@Override
			public void setStatusLine(StatusLine arg0) {}

			@Override
			public void setStatusCode(int arg0) throws IllegalStateException {}

			@Override
			public void setReasonPhrase(String arg0) throws IllegalStateException {}

			@Override
			public void setLocale(Locale arg0) {}

			@Override
			public void setEntity(HttpEntity arg0) {}

			@Override
			public StatusLine getStatusLine() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HttpEntity getEntity() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		String encode = "utf-8";
		String content = null;
		// since 4.3 不再使用 DefaultHttpClient
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpDelete httpdelete = new HttpDelete(urlToRead);
		// 设置header
		httpdelete.setHeader("Content-type", "application/json; charset=utf-8");
		httpdelete.setHeader("Connection", "Close");
		CloseableHttpResponse httpResponse = null;
		try {
			System.out.println(httpdelete);
			httpResponse = closeableHttpClient.execute(httpdelete);
			HttpEntity entity = httpResponse.getEntity();
			content = EntityUtils.toString(entity, encode);
			
			response.setHeaders(httpResponse.getAllHeaders());
			response.setHeader("Connection", "Close");
			
			response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
			response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try { // 关闭连接、释放资源
			closeableHttpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
