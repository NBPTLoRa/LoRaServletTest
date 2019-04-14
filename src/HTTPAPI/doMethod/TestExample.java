package HTTPAPI.doMethod;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.DeviceQueue;
import HTTPAPI.NativeAPI.Internal;

public class TestExample {
		public static void main(String[] args)
		{
			Internal internal=new Internal("47.101.172.221:8080");
			
			//登陆获取token
			JsonObject jsonObject=internal.login("admin", "admin");
			System.out.println(jsonObject);
			
			//获取token
			String token=jsonObject.get("jwt").getAsString();
			
			//节点类
			//Device device=new Device("47.101.172.221:8080");
			
			//节点数量
			//JsonObject jsonObject2=device.deviceCount(token);
			//System.out.println(jsonObject2);
			
			//节点列表
			//jsonObject2=device.deviceList(token, "999");
			//System.out.println(jsonObject2);
			
			//增加节点
			//jsonObject2=device.deviceAdd("1", "Test", "1234567890000000", "71bebddb-6ce9-4e19-b02d-62433bdc4254", "TestDev", token);
			//System.out.println(jsonObject2);
			
			
			//增加网关
			//Gateway gateway=new Gateway("47.101.172.221:8080");
			//JsonObject jobj=gateway.gatewayadd("0000000000000001", "TestDesc", "TestGateway", token);
			//System.out.print(jobj);
			//System.out.println("0123456789");
			//System.out.println(DeviceQueue.encodeBase64("0123456789"));
			
			System.out.println("aa".split("1").length+" "+"aa".split("1")[0]);
		}
}


