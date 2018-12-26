package HTTPAPI.NativeAPI;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
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
		
		JsonObject resj=httpGetApi(token);
		return resj;
		
	}
}