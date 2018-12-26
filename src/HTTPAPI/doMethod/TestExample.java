package HTTPAPI.doMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import HTTPAPI.NativeAPI.Device;
import HTTPAPI.NativeAPI.Internal;

public class TestExample {
		public static void main(String[] args)
		{
			Internal internal=new Internal("47.101.172.221:8080");
			JsonObject jsonObject=internal.login("admin", "admin");
			System.out.println(jsonObject);
			Device device=new Device("47.101.172.221:8080");
			JsonObject jsonObject2=device.deviceCount(jsonObject.get("jwt").getAsString());
			System.out.println(jsonObject2);
			
		}
}


