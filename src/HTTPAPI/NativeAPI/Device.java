package HTTPAPI.NativeAPI;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Device extends APIObject {
	public Device(String url)
	{
		this.classify="devices";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	public JsonObject deviceCount(String token)
	{
		JsonObject resj=new JsonParser().parse(httpGetApi(null,null,token)).getAsJsonObject();
		return resj;
		
	}
	
	public JsonObject deviceList(String token,String limit)
	{
		Map<String, String> param=new HashMap<>();
		param.put("limit", limit);
		JsonObject resj=new JsonParser().parse(httpGetApi(null,param,token)).getAsJsonObject();
		return resj;
		
	}
	
	public JsonObject deviceAdd(String appID,String description,String devEui,String devPrfID,String devName,String token) {
		String deviceObj="{\r\n" + 
				"  \"device\": {\r\n" + 
				"    \"applicationID\": \""+appID+"\",\r\n" + 
				"    \"description\": \""+description+"\",\r\n" + 
				"    \"devEUI\": \""+devEui+"\",\r\n" + 
				"    \"deviceProfileID\": \""+devPrfID+"\",\r\n" + 
				"    \"name\": \""+devName+"\",\r\n" + 
				"    \"referenceAltitude\": 0,\r\n" + 
				"    \"skipFCntCheck\": true\r\n" + 
				"  }\r\n" + 
				"}";
		JsonObject dObject=new JsonParser().parse(deviceObj).getAsJsonObject();
		JsonObject retJ=new JsonParser().parse(httpPostApi(dObject, null, null, token)).getAsJsonObject();
		return retJ;
		
	}
}