package HTTPAPI.NativeAPI;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MulticastGroup extends APIObject{
	public MulticastGroup(String url)
	{
		this.classify="multicast-groups";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	public JsonObject queuePOSTofID(String token,String ID)
	{
		String deviceObj="{\r\n" + 
				"  \"multicastQueueItem\": {\r\n" + 
				"    \"data\": \"MDY0MA==\",\r\n" + 
				"    \"fCnt\": 0,\r\n" + 
				"    \"fPort\": 10,\r\n" + 
				"    \"multicastGroupID\": \"e4bc7bf7-6cc5-45bd-b3c5-d2911c1012a9\"\r\n" + 
				"  }\r\n" + 
				"}";
		JsonObject dObject=new JsonParser().parse(deviceObj).getAsJsonObject();
		JsonObject retJ=new JsonParser().parse(httpPostApi(dObject, ID+"/queue", null, token).getBody()).getAsJsonObject();
		return retJ;
	}
}
