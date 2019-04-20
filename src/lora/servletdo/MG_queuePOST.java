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
		
		String ID=request.getParameter("ID");		//����ID
		
		
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
			
			MulticastGroup mGroup=new MulticastGroup(loraAddr+":8080");
			addReturn=mGroup.queuePOSTofID(token, ID);
			
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
