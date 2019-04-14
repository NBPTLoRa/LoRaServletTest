package lora.servletdo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.Device;
import HTTPAPI.NativeAPI.Internal;
import HTTPAPI.NativeAPI.ResponseModel;
import lora.mainservlet.doIns;
import lora.sqloperation.Sql;

public class DeviceQueue {
	public static String queueAdd(HttpServletResponse response,HttpServletRequest request,Sql sql,boolean devMode) {
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		String data=request.getParameter("data");
		
		//sql获取总服务器的ip、
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		
		
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的调用增加API
			//获取LoraServer的地址与用户
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//登陆LoraServere后获取Token
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			HTTPAPI.NativeAPI.DeviceQueue queue=new HTTPAPI.NativeAPI.DeviceQueue(loraAddr+":8080");
			
			ResponseModel responseModel=queue.queuePost(token, devEui, data);
			
			if(responseModel.getCode().equals("200"))
			{//通过
				retString="1";
			}else
			{//不通过
				retString="e:"+responseModel.getBody();
			}
		}
		else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
	
	public static String queueGet(HttpServletResponse response,HttpServletRequest request,Sql sql,boolean devMode) {
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
		//sql获取总服务器的ip、
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		
		
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的调用增加API
			//获取LoraServer的地址与用户
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//登陆LoraServere后获取Token
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			HTTPAPI.NativeAPI.DeviceQueue queue=new HTTPAPI.NativeAPI.DeviceQueue(loraAddr+":8080");
			
			ResponseModel responseModel=queue.queueGet(token, devEui);
			
			if(responseModel.getCode().equals("200"))
			{//通过
				retString=responseModel.getBody();
			}else
			{//不通过
				retString="e:"+responseModel.getBody();
			}
		}
		else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
		
	}
	
	public static String queueDel(HttpServletResponse response,HttpServletRequest request,Sql sql,boolean devMode) {
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
		//sql获取总服务器的ip、
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		
		
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的调用增加API
			//获取LoraServer的地址与用户
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//登陆LoraServere后获取Token
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			HTTPAPI.NativeAPI.DeviceQueue queue=new HTTPAPI.NativeAPI.DeviceQueue(loraAddr+":8080");
			
			ResponseModel responseModel=queue.queueDel(devEui, token);
			
			if(responseModel.getCode().equals("200"))
			{//通过
				retString="1";
			}else
			{//不通过
				retString="e:"+responseModel.getBody();
			}
		}
		else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
	
}
