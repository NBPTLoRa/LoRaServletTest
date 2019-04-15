package HTTPAPI.NativeAPI;

import java.io.UnsupportedEncodingException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class DeviceQueue extends APIObject{
	public DeviceQueue(String url)
	{
		this.classify="devices";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	public ResponseModel queuePost(String token,String devEui,String data,String confirmed){
		String queueObj="{\r\n" + 
				"  \"deviceQueueItem\": {\r\n" + 
				"    \"confirmed\": "+confirmed+",\r\n" + 
				"    \"data\": \""+encodeBase64(data)+"\",\r\n" + 
				"    \"devEUI\": \""+devEui+"\",\r\n" + 
				"    \"fCnt\": 0,\r\n" + 
				"    \"fPort\": 10\r\n" + 
				"  }\r\n" + 
				"}";
		JsonObject dObject=new JsonParser().parse(queueObj).getAsJsonObject();
		return httpPostApi(dObject, devEui+"/queue", null, token);
		
	}
	
	public ResponseModel queueGet(String token,String devEui) {
		ResponseModel responseModel=httpGetApi(devEui+"/queue",null,token);
		responseModel.setBody(APIObject.strTo16(responseModel.getBody()));
		return responseModel;
	}
	public ResponseModel queueDel(String devEui,String token) {
		return httpDelApi(devEui+"/queue", token);
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
