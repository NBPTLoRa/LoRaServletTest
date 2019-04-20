package lora.servletdo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.Device;
import HTTPAPI.NativeAPI.Internal;
import HTTPAPI.NativeAPI.MulticastGroup;
import lora.mainservlet.doIns;
import lora.sqloperation.Sql;

public class MG_queuePOST {
	public static String queuePOST(HttpServletResponse response,HttpServletRequest request,
			Sql sql,boolean devMode)
	{
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		String ID=request.getParameter("ID");		//操作ID
		
		
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
			JsonObject addReturn=new JsonObject();
			//获取LoraServer的地址与用户
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//登陆LoraServere后获取Token
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			
			MulticastGroup mGroup=new MulticastGroup(loraAddr+":8080");
			addReturn=mGroup.queuePOSTofID(token, ID);
			
			if(addReturn.toString().equals("{}"))
			{//通过
				retString="1";
			}else
			{//不通过
				retString="e:"+addReturn.toString();
			}
		}
		else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
}
