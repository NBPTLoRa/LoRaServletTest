package HTTPAPI.doMethod;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.Device;
import HTTPAPI.NativeAPI.Internal;

public class TestExample {
		public static void main(String[] args)
		{
			Internal internal=new Internal("47.101.172.221:8080");
			
			JsonObject jsonObject=internal.login("admin", "admin");
			System.out.println(jsonObject);
			
			String token=jsonObject.get("jwt").getAsString();
			Device device=new Device("47.101.172.221:8080");
			
			JsonObject jsonObject2=device.deviceCount(token);
			System.out.println(jsonObject2);
			
			jsonObject2=device.deviceList(token, "999");
			System.out.println(jsonObject2);
			
			
			jsonObject2=device.deviceAdd("1", "Test", "1234567890000000", "71bebddb-6ce9-4e19-b02d-62433bdc4254", "TestDev", token);
			System.out.println(jsonObject2);
			
		}
}


