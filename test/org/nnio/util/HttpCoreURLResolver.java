package org.nnio.util;

import java.io.IOException;
import java.net.URL;
import java.net.Socket;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.Scheme;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.DefaultHttpParams;
import org.apache.http.impl.io.PlainSocketFactory;
import org.apache.http.io.SocketFactory;
import org.apache.http.message.HttpGet;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpExecutionContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.nnio.util.URLResolver;

public class HttpCoreURLResolver implements URLResolver {
	private HttpParams params = null;
	private HttpRequestExecutor httpexecutor = null;
	private SocketFactory mSocketfactory;
    
	public HttpCoreURLResolver() {
		params = new DefaultHttpParams(null);
	}
	
	public void init() {
		mSocketfactory = PlainSocketFactory.getSocketFactory();
		Scheme.registerScheme("http", new Scheme("http", mSocketfactory, 80));
        params = new DefaultHttpParams(null);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUserAgent(params, "Jakarta-HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);
        
        httpexecutor = new HttpRequestExecutor();
        httpexecutor.setParams(params);
        // Required protocol interceptors
        httpexecutor.addInterceptor(new RequestContent());
        httpexecutor.addInterceptor(new RequestTargetHost());
        // Recommended protocol interceptors
        httpexecutor.addInterceptor(new RequestConnControl());
        httpexecutor.addInterceptor(new RequestUserAgent());
        httpexecutor.addInterceptor(new RequestExpectContinue());
	}
	
	public String[] fetch(String host, int port, String[] files) throws IOException {
		HttpHost hhost = new HttpHost(host, port);
        HttpClientConnection conn = new DefaultHttpClientConnection(hhost);
        ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();
        String[] results = new String[files.length];
        try {
			for (int i = 0; i < files.length; i++) {
				HttpGet request = new HttpGet(files[i]);
				System.out.println(">> Request URI: "
						+ request.getRequestLine().getUri());
				HttpResponse response = httpexecutor.execute(request, conn);
				System.out.println("<< Response: " + response.getStatusLine());
				String responseBody = EntityUtils
						.toString(response.getEntity());
				System.out.println(responseBody);
				System.out.println("==============");
				if (!connStrategy.keepAlive(response)) {
					conn.close();
				} else {
					System.out.println("Connection kept alive...");
				}
				results[i] = responseBody;
			}    
			return results;
        } catch (HttpException e) {
			throw new IOException("HttpException: {"+e.getMessage()+"}");
		} finally {
            conn.close();
        }		
	}
	
	public String fetch(URL url) throws IOException {
		int port = url.getPort() != -1?url.getPort():url.getDefaultPort();
        HttpHost host = new HttpHost(url.getHost(), port);
        HttpClientConnection conn = new DefaultHttpClientConnection(host);
        ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();
        try {          
            HttpGet request = new HttpGet(url.getFile());
//            System.out.println(">> Request URI: " + request.getRequestLine().getUri());
            HttpResponse response = httpexecutor.execute(request, conn);
//            System.out.println("<< Response: " + response.getStatusLine());
            String responseBody = EntityUtils.toString(response.getEntity());
//			System.out.println(responseBody);
//            System.out.println("==============");
            if (!connStrategy.keepAlive(response)) {
                conn.close();
            } else {
//                System.out.println("Connection kept alive...");
            }
            return responseBody;
        } catch (HttpException e) {
			throw new IOException("Error connecting to :" + url + ", exception: {"+e.getMessage()+"}");
		} finally {
            conn.close();
        }
	}
}
