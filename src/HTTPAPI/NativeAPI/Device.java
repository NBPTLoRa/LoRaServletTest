package HTTPAPI.NativeAPI;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.moshi.Json;

public class Device extends APIObject {
	public Device(String url)
	{
		this.classify="devices";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	public JsonObject deviceCount(String token)
	{
		String method="";
		//String res=sendHttp(this.url+method, param, headers, GetorPost, timeOut)
		
		JsonObject resj=new JsonParser().parse("").getAsJsonObject();
		return resj;
		
	}
}