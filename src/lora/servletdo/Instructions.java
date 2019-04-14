package lora.servletdo;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import lora.mainservlet.doIns;
import lora.sqloperation.Sql;

public class Instructions {
	public static String insDo(HttpServletRequest request,Sql sqlOp,PrintWriter out) {
		
		String insID=request.getParameter("insID");		//操作ID
		String insKey=request.getParameter("insKey");	//操作Key
		
		//判断ID和Key时候符合且没有被使用过
		String hasIns="e:CreateHasIns";
		hasIns=sqlOp.hasSameInsPara(insID,insKey);
		if(hasIns.equals("0")||hasIns.equals("1"))
		{//已完成或不存在
			//{"error":"Operational instructions do not exist!"}
			out.println("{\"error\":\"Operational instructions do not exist-"+hasIns+"!\"}");
			return null;
		}else if(hasIns.substring(0,2).equals("e:"))
		{
			out.println("{\"error\":\"Operational Instruction Query Error"+hasIns+"\"}");
			return null;
		}
		
		switch (hasIns) {
			case "deviceRX1":
				String IP=doIns.getIpAddr(request);
				deviceRX1(insID,insKey,sqlOp,out,IP);
				System.out.println("[ServerMessage]DistServer: do-deviceRX1. IP="+IP+" insID="+insID);
				break;
			case "deviceRX2":
				String IP3=doIns.getIpAddr(request);
				deviceRX2(insID,insKey,sqlOp,out,IP3);
				System.out.println("[ServerMessage]DistServer: do-deviceRX2. IP="+IP3+" insID="+insID);
				break;
			case "uplinkRX1":
				String IP2=doIns.getIpAddr(request);
				uplinkRX1(insID,insKey,sqlOp,out,IP2);
				System.out.println("[ServerMessage]DistServer: do-uplinkRX1. IP="+IP2+" insID="+insID);
				break;
			case "uplinkRX2":
				String IP24=doIns.getIpAddr(request);
				uplinkRX2(insID,insKey,sqlOp,out,IP24);
				System.out.println("[ServerMessage]DistServer: do-uplinkRX2. IP="+IP24+" insID="+insID);
				break;
			default:
				out.println("{\"error\":\"e:000This is not any enforceable act.\"}");
				break;
		}
		return "";

	}
	
	private static void deviceRX1(String insID,String insKey,Sql sqlOp,PrintWriter out,String IP) {
		int retPointCount=0;
		String retError="e:CreateDeviceRX1Error";
		ArrayList<String> data=new ArrayList<>();
		data=sqlOp.getDeviceRX1(insID,insKey);
		//首条数据
		String firstData=data.get(0).substring(0, 1);
		String outString="RushB!!!!";
		if(firstData.equals("e"))
		{//获取报错
			retError=data.get(0);
			out.println(outString="{\"pointCount\":\"-1\",\"error\":\""+retError+"\"}");
		}else if(firstData.equals("0"))
		{//空数据
			retPointCount=0;
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
		}else 
		{//有数据
			retPointCount=data.size();
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
			
			String[] tempArr;
			for(int i=0;i<data.size();i++)
			{
				tempArr=data.get(i).split(",");
				out.println("{\"time\":\""+tempArr[0]+"\",\"dev_eui\":\""+tempArr[1]+"\",\"device_name\":\""+tempArr[2]+"\",\"value\":\""+tempArr[3]+"\"}");
			}
		}
		sqlOp.setInsWorked(insID,insKey," "+IP+" "+outString);
	}
	
	private static void deviceRX2(String insID,String insKey,Sql sqlOp,PrintWriter out,String IP) {
		int retPointCount=0;
		String retError="e:CreateDeviceRX1Error";
		ArrayList<String> data=new ArrayList<>();
		data=sqlOp.getDeviceRX2(insID,insKey);
		//首条数据
		String firstData=data.get(0).substring(0, 1);
		String outString="RushB!!!!";
		if(firstData.equals("e"))
		{//获取报错
			retError=data.get(0);
			out.println(outString="{\"pointCount\":\"-1\",\"error\":\""+retError+"\"}");
		}else if(firstData.equals("0"))
		{//空数据
			retPointCount=0;
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
		}else 
		{//有数据
			retPointCount=data.size();
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
			
			String[] tempArr;
			for(int i=0;i<data.size();i++)
			{
				tempArr=data.get(i).split(",");
				out.println("{\"time\":\""+tempArr[0]+"\",\"dev_eui\":\""+tempArr[1]+"\",\"device_name\":\""+tempArr[2]+"\",\"value\":\""+tempArr[3]+"\"}");
			}
		}
		sqlOp.setInsWorked(insID,insKey," "+IP+" "+outString);
	}
	
	private static void uplinkRX1(String insID,String insKey,Sql sqlOp,PrintWriter out,String IP) {
		int retPointCount=0;
		String retError="e:CreateUplinkRXError";
		ArrayList<String> data=new ArrayList<>();
		data=sqlOp.getUplinkRX1(insID,insKey);
		//首条数据
		String firstData=data.get(0).substring(0, 1);
		String outString="RushB!!!!";
		if(firstData.equals("e"))
		{//获取报错
			retError=data.get(0);
			out.println(outString="{\"pointCount\":\"-1\",\"error\":\""+retError+"\"}");
		}else if(firstData.equals("0"))
		{//空数据
			retPointCount=0;
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
		}else 
		{//有数据
			retPointCount=data.size();
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
			
			String[] tempArr;
			for(int i=0;i<data.size();i++)
			{
				tempArr=data.get(i).split(",");
				out.println("{\"time\":\""+tempArr[0]
						+"\",\"dev_eui\":\""+tempArr[1]
						+"\",\"device_name\":\""+tempArr[2]
						+"\",\"dr\":\""+tempArr[3]
						+"\",\"frequency\":\""+tempArr[4]
						+"\",\"rssi\":\""+tempArr[5]
						+"\",\"snr\":\""+tempArr[6]
								+"\"}");
			}
		}
		sqlOp.setInsWorked(insID,insKey," "+IP+" "+outString);
	}
	private static void uplinkRX2(String insID,String insKey,Sql sqlOp,PrintWriter out,String IP) {
		int retPointCount=0;
		String retError="e:CreateUplinkRXError";
		ArrayList<String> data=new ArrayList<>();
		data=sqlOp.getUplinkRX2(insID,insKey);
		//首条数据
		String firstData=data.get(0).substring(0, 1);
		String outString="RushB!!!!";
		if(firstData.equals("e"))
		{//获取报错
			retError=data.get(0);
			out.println(outString="{\"pointCount\":\"-1\",\"error\":\""+retError+"\"}");
		}else if(firstData.equals("0"))
		{//空数据
			retPointCount=0;
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
		}else 
		{//有数据
			retPointCount=data.size();
			retError="";
			out.println(outString="{\"pointCount\":\""+retPointCount+"\",\"error\":\""+retError+"\"}");
			
			String[] tempArr;
			for(int i=0;i<data.size();i++)
			{
				tempArr=data.get(i).split(",");
				out.println("{\"time\":\""+tempArr[0]
						+"\",\"dev_eui\":\""+tempArr[1]
						+"\",\"device_name\":\""+tempArr[2]
						+"\",\"dr\":\""+tempArr[3]
						+"\",\"frequency\":\""+tempArr[4]
						+"\",\"rssi\":\""+tempArr[5]
						+"\",\"snr\":\""+tempArr[6]
								+"\"}");
			}
		}
		sqlOp.setInsWorked(insID,insKey," "+IP+" "+outString);
	}
}
