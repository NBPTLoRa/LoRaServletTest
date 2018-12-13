package HTTPAPI.doMethod;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.Internal;

public class TestExample {
		public static void main(String[] args)
		{
			Internal internal=new Internal("66.42.65.86:8080");
			JsonObject jsonObject=internal.login("admin", "admin");
			System.out.println(jsonObject);
		}
}


