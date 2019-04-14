package HTTPAPI.NativeAPI;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonObject;

import jdk.nashorn.internal.ir.RuntimeNode.Request;


class APIObject {
	
	//https://66.42.65.86:8080/api/internal/login
	
	protected String classify;			//工具类类型:internal
	protected String url;				//url:https://66.42.65.86:8080/api/
	protected String protH="http";		//连接协议：默认http
	
	
	private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[] {
        	new X509TrustManager(){
        					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        						return new java.security.cert.X509Certificate[] {};
        					}

            public void checkClientTrusted(X509Certificate[] chain, String authType) {}

            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
        }};
        
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    
    
    //Post函数
    protected ResponseModel httpPostApi(JsonObject jsonObject,String method,Map<String,String> param,String token)
	{
		ResponseModel responseModel=new ResponseModel();
		try {
	        if(method!=null)
	        {//如果不与类同名，也就是细分函数
	        	method="/"+method;
	        }
	        else
	        {
	        	method="";
	        }
			
	        //设定headers
			Map<String, String> headers = new HashMap<>();
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json;cahrset=utf-8");
			headers.put("connection", "keep-alive");
			headers.put("charset", "UTF-8");
			if(token!=null)
			{
				headers.put("Authorization", "Bearer " + token);
			}
			String inputPra="";
			if(param!=null)
			{
				inputPra+="?";
				for(Entry<String, String> vo : param.entrySet()){
				  	inputPra+=vo.getKey()+"="+vo.getValue()+"&";
				}
				  
			}
			responseModel=sendHttp(this.url+classify+method+inputPra, jsonObject.toString(), headers, "POST", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseModel.setBody("e:"+e.getMessage());
		}
		return responseModel;
	}


	//Get函数，也就是不需要输入Json
	protected ResponseModel httpGetApi(String method,Map<String,String> param,String token)
	{
		ResponseModel responseModel=new ResponseModel();
		try {
	        if(method!=null)
	        {//如果不与类同名，也就是细分函数
	        	method="/"+method;
	        }
	        else
	        {
	        	method="";
	        }
			
	        //设定headers
			Map<String, String> headers = new HashMap<>();
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			
			if(token!=null)
			{
				headers.put("Authorization", "Bearer " + token);
			}
			String inputPra="";
			if(param!=null)
			{
				inputPra+="?";
				for(Entry<String, String> vo : param.entrySet()){
				  	inputPra+=vo.getKey()+"="+vo.getValue()+"&";
				}
				  
			}
			responseModel=sendHttp(this.url+classify+method+inputPra, null, headers, "GET", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseModel.setBody("e:"+e.getMessage());
		}
		return responseModel;
	}
	
	protected ResponseModel httpDelApi(String method,String token)
	{
		ResponseModel responseModel=new ResponseModel();
		try {
	        if(method!=null)
	        {//如果不与类同名，也就是细分函数
	        	method="/"+method;
	        }
	        else
	        {
	        	method="";
	        }
			
	        //设定headers
			Map<String, String> headers = new HashMap<>();
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			
			if(token!=null)
			{
				headers.put("Authorization", "Bearer " + token);
			}
			responseModel=sendHttp(this.url+classify+method,null, headers, "DELETE", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseModel.setBody("e:"+e.getMessage());
		}
		return responseModel;
	}

	
    public ResponseModel sendHttp(String url, String json, Map<String, String> headers, String RequestMode, Integer timeOut) throws Exception {
    	ResponseModel responseModel=new ResponseModel();
        //trustAllHosts();//https协议下使用
    	RequestMode=RequestMode.toUpperCase();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        if (timeOut != null && timeOut > 0) {
            conn.setReadTimeout(timeOut * 1000);
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
        httpURLConnection.setRequestMethod(RequestMode.toUpperCase());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
        }
        httpURLConnection.setDoInput(true);
        if ("POST".equalsIgnoreCase(RequestMode)) {
        	httpURLConnection.setUseCaches(false);
        	httpURLConnection.setDoOutput(true);
        	
            byte[] writebytes = json.getBytes();
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
            OutputStream outwritestream = httpURLConnection.getOutputStream();
            outwritestream.write(json.getBytes());
            outwritestream.flush();
            outwritestream.close();
        }

        httpURLConnection.connect();
        
        if (httpURLConnection.getResponseCode() >= 400 ) {
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
        }
        else
        {
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        }
        
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        responseModel.setCode(httpURLConnection.getResponseCode()+"");
        responseModel.setBody(result);
        return responseModel;
    }
    
    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
