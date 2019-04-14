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
			//��ȡLoraServer�ĵ�ַ���û�
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//��½LoraServere���ȡToken
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			HTTPAPI.NativeAPI.DeviceQueue queue=new HTTPAPI.NativeAPI.DeviceQueue(loraAddr+":8080");
			
			ResponseModel responseModel=queue.queuePost(token, devEui, data);
			
			if(responseModel.getCode().equals("200"))
			{//ͨ��
				retString="1";
			}else
			{//��ͨ��
				retString="e:"+responseModel.getBody();
			}
		}
		else
		{//�������
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
	
	public static String queueGet(HttpServletResponse response,HttpServletRequest request,Sql sql,boolean devMode) {
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
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
			//��ȡLoraServer�ĵ�ַ���û�
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//��½LoraServere���ȡToken
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			HTTPAPI.NativeAPI.DeviceQueue queue=new HTTPAPI.NativeAPI.DeviceQueue(loraAddr+":8080");
			
			ResponseModel responseModel=queue.queueGet(token, devEui);
			
			if(responseModel.getCode().equals("200"))
			{//ͨ��
				retString=responseModel.getBody();
			}else
			{//��ͨ��
				retString="e:"+responseModel.getBody();
			}
		}
		else
		{//�������
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
		
	}
	
	public static String queueDel(HttpServletResponse response,HttpServletRequest request,Sql sql,boolean devMode) {
		String retString="e:create";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
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
			//��ȡLoraServer�ĵ�ַ���û�
			String loraAddr=sql.getLoRaAddr();
			String idpwd=sql.getIDPWDofLoRa();
			String userID=idpwd.split(",")[0];
			String PWD=idpwd.split(",")[1];
			//��½LoraServere���ȡToken
			Internal internal=new Internal(loraAddr+":8080");
			String token=internal.login(userID, PWD).get("jwt").getAsString();
			HTTPAPI.NativeAPI.DeviceQueue queue=new HTTPAPI.NativeAPI.DeviceQueue(loraAddr+":8080");
			
			ResponseModel responseModel=queue.queueDel(devEui, token);
			
			if(responseModel.getCode().equals("200"))
			{//ͨ��
				retString="1";
			}else
			{//��ͨ��
				retString="e:"+responseModel.getBody();
			}
		}
		else
		{//�������
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		return retString;
	}
	
}
