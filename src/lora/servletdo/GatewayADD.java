package lora.servletdo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.Gateway;
import HTTPAPI.NativeAPI.Internal;
import lora.mainservlet.doIns;
import lora.sqloperation.Sql;

public class GatewayADD {
	public static String gatewayAdd(HttpServletResponse response,HttpServletRequest request,Sql sql,String gateID,String description,String gateName,boolean devMode)
	{
		String retString="e:create";
		//response.setContentType("html/text;charset=UTF-8");
		
		//sql��ȡ�ܷ�������ip��
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//��������ȡ�ܷ�����IP����
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//���ܷ������ĵ�������API
			JsonObject addReturn=new JsonObject();
			//��ȡLoraServer�ĵ�ַ���û�
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//��½LoraServere���ȡToken
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			
			//��ȡtoken��Ĳ���
			Gateway gateway=new Gateway(loraAddr+":8080");
			addReturn=gateway.gatewayadd(gateID, description, gateName, token);
			
			if(addReturn.toString().equals("{}"))
			{//ͨ��
				retString="1";
			}else
			{//��ͨ��
				retString="e:"+addReturn.toString();
			}
		}
		else
		{//�������
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
}
