package HTTPAPI.NativeAPI;


import com.google.gson.JsonObject;

public class Internal extends APIObject{

	/**
	 * 该函数用于实例化Internal累，指明API定向的url
	 * @author JZF
	 * @param url LoRa服务器的url***.***.***.***:端口号
	 * @return Internal
	 * */
	public Internal(String url)
	{
		this.classify="internal";
		this.url="https://"+url+"/api/";
	}
	
	/**
	 * 使用该函数登录用户，返回JWTToken，用于接下来的所有操作
	 * @author JZF
	 * @param user LoRa服务器的用户
	 * @param pwd LoRa服务器该用户的密码
	 * @return JsonObject JWTToken{"jwt":"需要的token"}或者是JsonObject错误信息
	 * */
	public JsonObject login(String user,String pwd)
	{
		String method="login";
		JsonObject jsonObject=new JsonObject();
		
		
		return null;
	}
}
