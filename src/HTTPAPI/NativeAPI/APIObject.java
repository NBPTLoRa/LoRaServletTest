package HTTPAPI.NativeAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


class APIObject {
	//https://66.42.65.86:8080/api/internal/login
	//����������:internal
	protected String classify;
	//url:https://66.42.65.86:8080/api/
	protected String url;
	
	
	
	
	
	
	
	
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
    
	protected JsonObject httpapi(JsonObject jsonObject,String method,String[] parameters)
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
            trustAllHosts();
            URL realUrl = new URL(url+classify+"/"+method);
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
}