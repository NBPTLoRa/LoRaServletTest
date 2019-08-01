package HTTPAPI.NativeAPI;

import java.io.UnsupportedEncodingException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class MulticastGroup extends APIObject{
	public MulticastGroup(String url)
	{
		this.classify="multicast-groups";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	public JsonObject queuePOSTofID(String token,String data,String ID)
	{
		String deviceObj="{\r\n" + 
				"  \"multicastQueueItem\": {\r\n" + 
				"    \"data\": \""+encodeBase64(data)+"\",\r\n" + 
				"    \"fCnt\": 0,\r\n" + 
				"    \"fPort\": 10,\r\n" + 
				"    \"multicastGroupID\": \""+ID+"\"\r\n" + 
				"  }\r\n" + 
				"}";
		JsonObject dObject=new JsonParser().parse(deviceObj).getAsJsonObject();
		JsonObject retJ=new JsonParser().parse(httpPostApi(dObject, ID+"/queue", null, token).getBody()).getAsJsonObject();
		return retJ;
	}
	
	public static String encodeBase64(String s){
		String base64Sign = null;
		try {
			base64Sign = Base64.encode(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Sign;
	}
}
