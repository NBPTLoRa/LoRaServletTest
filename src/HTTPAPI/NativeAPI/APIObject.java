package HTTPAPI.NativeAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mysql.jdbc.StringUtils;
import com.squareup.moshi.JsonReader.Token;


class APIObject {
	
	//https://66.42.65.86:8080/api/internal/login
	
	protected String classify;			//����������:internal
	protected String url;				//url:https://66.42.65.86:8080/api/
	protected String protH="http";		//����Э�飺Ĭ��http
	
	
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
    
    
    //Post����
	protected JsonObject httpPostApi(JsonObject jsonObject,String method,String[] parameters)
	{
		
		/*
		curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \ 
		   "password": "admin", \ 
		   "username": "admin" \ 
		 }' 'https://66.42.65.86:8080/api/internal/login'
		 */
        JsonObject retj = new JsonObject();
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn;
        try {
            trustAllHosts();//httpsЭ����ʹ��
            if(!method.equals(""))
            {//���������ͬ����Ҳ����ϸ�ֺ���
            	method="/"+method;
            }
            URL realUrl = new URL(url+classify+method);
            
            //��һ�οճ����ĵط���url�������parameters
            
            
            // ͨ�������ַ�ж���������(http������https)
            if (realUrl.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) realUrl.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                        conn = https;
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // ����ͨ�õ���������
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset","utf-8");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
             out.print(jsonObject.toString());
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            	retj=new JsonParser().parse(line).getAsJsonObject();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            retj.addProperty("ERROR", e.toString());
        } finally {
        	// ʹ��finally�����ر��������������
            try {
                if (out != null) {
                	out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                //ex.printStackTrace();
                retj.addProperty("ERROR", ex.toString());
            }
        }
        return retj;
	}


	//Get������Ҳ���ǲ���Ҫ����Json
	protected JsonObject httpGetApi(String token)
	{
		
		/*
		curl -X GET 
		--header 'Accept: application/json' 
		--header 'Grpc-Metadata-Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJsb3JhLWFwcC1zZXJ2ZXIiLCJleHAiOjE1NDU4Mzg0ODcsImlzcyI6ImxvcmEtYXBwLXNlcnZlciIsIm5iZiI6MTU0NTc1MjA4Nywic3ViIjoidXNlciIsInVzZXJuYW1lIjoiYWRtaW4ifQ.2NjBycrupiY5SUkiFvrJ3N33L7GUjjw6Ih0pMRutxHk' 
		'http://47.101.172.221:8080/api/devices'
		 */
		
		JsonObject retJ=new JsonObject();
		Map<String, String> headers = new HashMap<>();
     headers.put("Accept", "application/json");
      headers.put("Content-Type", "application/json");
      headers.put("Authorization", "Bearer " + token);
      //  headers.put("Accept", "text/xml");
   //  headers.put("Content-Type", "application/x-www-form-urlencoded");
        try {
			retJ=new JsonParser().parse(sendHttp(this.url+"devices", null, headers, "GET", null)).getAsJsonObject();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return retJ;
	}
	
    public String sendHttp(String url, String param, Map<String, String> headers, String GetorPost, Integer timeOut) throws Exception {
    	GetorPost = GetorPost == null ? "GET" : GetorPost;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        if (!StringUtils.isNullOrEmpty(param) && "GET".equalsIgnoreCase(GetorPost)) {
            url = url + "?" + param;
        }
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        if (timeOut != null && timeOut > 0) {
            conn.setReadTimeout(timeOut * 1000);
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
        httpURLConnection.setRequestMethod(GetorPost.toUpperCase());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
        }
        conn.setDoInput(true);
        if (!StringUtils.isNullOrEmpty(param) && "POST".equalsIgnoreCase(GetorPost)) {
            conn.setDoOutput(true);
            out = new PrintWriter(conn.getOutputStream());

            out.print(param);
            out.flush();
        }

        httpURLConnection.connect();
        in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
