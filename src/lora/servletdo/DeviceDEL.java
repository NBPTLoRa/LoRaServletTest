package lora.servletdo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import HTTPAPI.NativeAPI.Device;
import HTTPAPI.NativeAPI.Internal;
import lora.mainservlet.doIns;
import lora.sqloperation.Sql;

public class DeviceDEL {
	public static String devicedel(HttpServletResponse response,HttpServletRequest request,Sql sql,String devEui,boolean devMode)
	{
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		//sql��ȡ�ܷ�������ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//��������ȡ�ܷ�����IP����
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//���ܷ������ĵ���ɾ��API
			JsonObject addReturn=new JsonObject();
			//��ȡLoraServer�ĵ�ַ���û�
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//��½LoraServere���ȡToken
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			
			Device device=new Device(loraAddr+":8080");
			
			addReturn=device.devicedel(devEui, token);
			
			if(addReturn.toString().equals("{}"))
			{//ͨ��
				retString="1";
			}else
			{//��ͨ��
				retString="e:"+addReturn.toString();
			}
		}
		else
		{//�������������������������
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
}
