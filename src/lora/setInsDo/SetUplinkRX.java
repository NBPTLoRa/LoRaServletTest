package lora.setInsDo;

import javax.servlet.http.HttpServletRequest;

import lora.mainservlet.SetInstruction;
import lora.sqloperation.Sql;

public class SetUplinkRX {
	public static String uplinkRX(HttpServletRequest request,Sql sqlop) {
		String devEui=request.getParameter("devEui");	//设备ID
		String hwOPT=request.getParameter("hwOPT");		//操作名
		String userID=request.getParameter("userID");		//用户ID
		String t=request.getParameter("t");			//时间
		String et=request.getParameter("et");		//结束时间
		
		String insID="00000000000000000000000000000000";
		String insKey="00000000000000000000000000000000";
		String ret="";
		do {
			//设定未重复的指令码
			insID=SetInstruction.randomHexString(32);
			insKey=SetInstruction.randomHexString(32);
			ret=sqlop.hasSameInsPara(insID,insKey);
		}while(!ret.equals("0"));
		
		
		String sqlRet="e:setULRXsqlCRT";
		
		String retString="e:SetUplinkRXCreate";
		if(et==null)
		{//最后时长查询
			sqlRet=sqlop.setInsUplinkRX1(insID,insKey,userID,hwOPT,devEui,t);
		}
		else
		{//区间查询
			sqlRet=sqlop.setInsUplinkRX2(insID,insKey,userID,hwOPT,devEui,t,et);
		}
		if(!sqlRet.equals("1"))
		{
			retString=sqlRet;
		}
		else
		{
			retString="doOper=opt&insID="+insID+"&"+"insKey="+insKey;
		}
		
		return retString;
	}
}
