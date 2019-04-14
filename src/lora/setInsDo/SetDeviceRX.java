package lora.setInsDo;

import javax.servlet.http.HttpServletRequest;

import lora.mainservlet.SetInstruction;
import lora.sqloperation.Sql;

public class SetDeviceRX {
	public static String deviceRX(HttpServletRequest request,Sql sqlop) {
		String devEui=request.getParameter("devEui");	//�豸ID
		String hwOPT=request.getParameter("hwOPT");		//������
		String userID=request.getParameter("userID");		//�û�ID
		String t=request.getParameter("t");			//ʱ��
		String et=request.getParameter("et");		//����ʱ��
		
		String insID="00000000000000000000000000000000";
		String insKey="00000000000000000000000000000000";
		String ret="";
		do {
			insID=SetInstruction.randomHexString(32);
			insKey=SetInstruction.randomHexString(32);
			ret=sqlop.hasSameInsPara(insID,insKey);
		}while(!(ret.equals("0")||ret.equals("1")));
		
		
		String sqlRet="e:setDRXsqlCRT";
		
		String retString="e:SetDeviceRXCreate";
		if(et==null)
		{//���ʱ����ѯ
			sqlRet=sqlop.setInsDeviceRX1(insID,insKey,userID,hwOPT,devEui,t);
		}
		else
		{//�����ѯ
			sqlRet=sqlop.setInsDeviceRX2(insID,insKey,userID,hwOPT,devEui,t,et);
		}
		if(!sqlRet.equals("1"))
		{
			retString=sqlRet;
		}
		else
		{
			//retString="doOper=opt&insID="+insID+"&"+"insKey="+insKey;
			retString=sqlop.getDeviceLastCommu(devEui)+";doOper=opt&insID="+insID+"&"+"insKey="+insKey;
		}
		
		return retString;
		
	}
}