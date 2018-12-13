package HTTPAPI.NativeAPI;

public class Device extends APIObject {
	public Device(String url)
	{
		this.classify="devices";
		this.url="https://"+url+"/api/";
	}
}
