package HTTPAPI.NativeAPI;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Gateway extends APIObject{

	/**
	 * 该函数用于实例化Gateway类，指明API定向的url
	 * @author JZF
	 * @param url LoRa服务器的url***.***.***.***:端口号
	 * @return Gateway
	 * */
	public Gateway(String url)
	{
		this.classify="gateways";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	public JsonObject gatewayadd(String gateID,String description,String gateName,String token)
	{
		String gatewayObj="{\r\n" + 
				"  \"gateway\": {\r\n" + 
				"    \"description\": \""+description+"\",\r\n" + 
				"    \"discoveryEnabled\": false,\r\n" + 
				"    \"gatewayProfileID\": \"086001a7-c014-4096-8d96-1631306a3efa\",\r\n" + 
				"    \"id\": \""+gateID+"\",\r\n" + 
				"    \"location\": {\r\n" + 
				"      \"accuracy\": 0,\r\n" + 
				"      \"altitude\": 0,\r\n" + 
				"      \"latitude\": 0,\r\n" + 
				"      \"longitude\": 0,\r\n" + 
				"      \"source\": \"UNKNOWN\"\r\n" + 
				"    },\r\n" + 
				"    \"name\": \""+gateName+"\",\r\n" + 
				"    \"networkServerID\": \"1\",\r\n" + 
				"    \"organizationID\": \"1\"\r\n" + 
				"  }\r\n" + 
				"}";
		JsonObject dObject=new JsonParser().parse(gatewayObj).getAsJsonObject();
		JsonObject retJ=new JsonParser().parse(httpPostApi(dObject, null, null, token).getBody()).getAsJsonObject();
		return retJ;
	}

	public JsonObject gatewaydel(String gatewayID,String token)
	{
		String ret=httpDelApi(gatewayID, token).getBody();
		JsonObject retJ=new JsonParser().parse(ret).getAsJsonObject();
		return retJ;
	}
}
