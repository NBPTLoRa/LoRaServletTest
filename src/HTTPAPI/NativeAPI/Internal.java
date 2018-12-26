package HTTPAPI.NativeAPI;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Internal extends APIObject{

	/**
	 * �ú�������ʵ����Internal�ۣ�ָ��API�����url
	 * @author JZF
	 * @param url LoRa��������url***.***.***.***:�˿ں�
	 * @return Internal
	 * */
	public Internal(String url)
	{
		this.classify="internal";
		this.url=this.protH+"://"+url+"/api/";
	}
	
	/**
	 * ʹ�øú�����¼�û�������JWTToken�����ڽ����������в���
	 * @author JZF
	 * @param user LoRa���������û�
	 * @param pwd LoRa���������û�������
	 * @return JsonObject JWTToken{"jwt":"��Ҫ��token"}������JsonObject������Ϣ
	 * */
	public JsonObject login(String user,String pwd)
	{
		String method="login";
		JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty("password",pwd);
		jsonObject.addProperty("username",user);
		JsonObject res=new JsonParser().parse(httpPostApi(jsonObject, method, null,null)).getAsJsonObject();
		if(!res.has("jwt")&&res.has("ERROR"))
		{
			String error=res.get("ERROR").toString();
			if(error.indexOf("401 for URL")!=-1)
			{
				res.addProperty("ERRORCH", "�û����������");
			}
		}
		return res;
		
	}
}
