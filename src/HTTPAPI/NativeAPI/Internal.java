package HTTPAPI.NativeAPI;


import com.google.gson.JsonObject;

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
		this.url="https://"+url+"/api/";
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
		
		
		return null;
	}
}
