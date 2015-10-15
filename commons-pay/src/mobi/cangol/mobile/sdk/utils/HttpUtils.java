package mobi.cangol.mobile.sdk.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


public  class HttpUtils {
	public static final String TAG="HttpUtils";
	public static final boolean DEBUG=true;
	private HttpClient mHttpClient;
	private Context mContext;
	private static HttpUtils client=null;
	private HttpUtils(Context context){
		mContext=context;
		mHttpClient=getNewHttpClient();
	}
	public static HttpUtils getInstance(Context context){
		if(client==null){
			client=new HttpUtils(context);
		}
		return client;
	}
	
	public void execute(final String url,final String method,final HashMap<String, String> paramsMap,final OnDataLoader onDataLoader){
		AsyncTask<Void, Void, String> task=new AsyncTask<Void,Void,String>(){
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if(onDataLoader!=null)onDataLoader.onStart();
			}
			@Override
			protected String doInBackground(Void... params) {
				if("get".equals(method)){
					return httpGet(url,paramsMap);
				}else{
					return httpPost(url,paramsMap);
				}
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if(onDataLoader!=null)onDataLoader.onSuccess(result);
			}
		};
		task.execute();
	}
	public  String httpGet(String url,HashMap<String, String> params) {
		if (url == null || url.length() == 0) {
			Log.e(TAG, "httpGet, url is null");
			return null;
		}
		if(params!=null&&params.size()>0){
			url=url+(url.contains("?")?"":"?")+getParamsString(params);
		}
		HttpGet httpGet = new HttpGet(url);
		HttpEntity  httpEntity=null;
		try {
			HttpResponse resp = mHttpClient.execute(httpGet);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG, "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			httpEntity = resp.getEntity();
			String response = EntityUtils.toString(httpEntity, HTTP.UTF_8);
			Log.d(TAG, "response="+response);
			return response;

		} catch (Exception e) {
			Log.e(TAG, "httpGet exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}finally {
			if(httpEntity != null)
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public  String httpPost(String url, HashMap<String, String> map) {
		if (url == null || url.length() == 0) {
			Log.e(TAG, "httpPost, url is null");
			return null;
		}
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for(String key:map.keySet()){
			params.add(new BasicNameValuePair(key, map.get(key)));
		}
		HttpPost httpPost = new HttpPost(url);
		HttpEntity  httpEntity=null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			HttpResponse resp = mHttpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG, "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			httpEntity = resp.getEntity();
			String response = EntityUtils.toString(httpEntity, HTTP.UTF_8);
			Log.d(TAG, "response="+response);
			return response;
		} catch (Exception e) {
			Log.e(TAG, "httpPost exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if(httpEntity != null)
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public String executePost(final String url,String entity){
		if (url == null || url.length() == 0) {
			Log.e(TAG, "httpPost, url is null");
			return null;
		}
		HttpPost httpPost = new HttpPost(url);
		HttpEntity  httpEntity=null;
		try {
			httpPost.setEntity(new StringEntity(entity, HTTP.UTF_8));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			Log.d(TAG, "url="+url+"\nentity="+entity);
			HttpResponse resp = mHttpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG, "httpPost fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}
			httpEntity = resp.getEntity();
			String response = EntityUtils.toString(httpEntity, HTTP.UTF_8);
			Log.d(TAG, "response="+response);
			return response;
		} catch (Exception e) {
			Log.e(TAG, "httpPost exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if(httpEntity != null)
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public static boolean isConnection(Context context){
		 final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	     final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	     if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
	        	return false;
	      }
	     return true;
	}
	public static String getParamsString(HashMap<String,String> params) {
		StringBuilder buider=new StringBuilder();
		if(params!=null&&params.size()>0){
			for(String key:params.keySet()){
				buider.append(key+"&"+params.get(key));
			}
			return buider.toString();
		}else{
			return "";
		}
	}
	private static class SSLSocketFactoryEx extends SSLSocketFactory {      
	      
	    SSLContext sslContext = SSLContext.getInstance("TLS");      
	      
	    public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {      
	        super(truststore);      
	      
	        TrustManager tm = new X509TrustManager() {      
	      
	            public X509Certificate[] getAcceptedIssuers() {      
	                return null;      
	            }      
	      
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,	String authType) throws java.security.cert.CertificateException {
				}  
	        };      
	      
	        sslContext.init(null, new TrustManager[] { tm }, null);      
	    }      
	      
		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,	port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		} 
	}  

	private static HttpClient getNewHttpClient() { 
	   try { 
	       KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
	       trustStore.load(null, null); 

	       SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore); 
	       sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 

	       HttpParams params = new BasicHttpParams(); 
	       HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1); 
	       HttpProtocolParams.setContentCharset(params, HTTP.UTF_8); 

	       SchemeRegistry registry = new SchemeRegistry(); 
	       registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80)); 
	       registry.register(new Scheme("https", sf, 443)); 

	       ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry); 

	       return new DefaultHttpClient(ccm, params); 
	   } catch (Exception e) { 
	       return new DefaultHttpClient(); 
	   } 
	}
}
