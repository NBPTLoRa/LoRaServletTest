package lora.mainservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lora.servletdo.DeviceADD;
import lora.servletdo.DeviceDEL;
import lora.servletdo.DeviceQueue;
import lora.servletdo.GatewayADD;
import lora.servletdo.GatewayDEL;
import lora.servletdo.Instructions;
import lora.servletdo.MG_queuePOST;
import lora.sqloperation.Sql;

/**
 * Servlet implementation class doIns
 */
public class doIns extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//开发模式
	//开发阶段时可以写为True，跳过检测主服务器IP地址
	private static boolean devMode=false;
    /**
     * @see HttpServlet#HttpServlet()
     */
	Sql sql;
    public doIns() {
        super();
        // TODO Auto-generated constructor stub
        sql=new Sql();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		String doOper=request.getParameter("doOper");	//要做的操作
		
		String devEui=request.getParameter("devEui");	//设备ID
		String ProfName=request.getParameter("ProfName");//设备配置
		String descrip=request.getParameter("descrip");	//设备备注
		String devName=request.getParameter("devName");	//设备名称
		String userID=request.getParameter("userID");	//用户ID
		String appKey=request.getParameter("appKey");	//三元组密码1
		String nwkKey=request.getParameter("nwkKey");	//三元组密码3
		
		String gatewayID=request.getParameter("gatewayID");			//网关ID
		
		String retString="e:Create";//
		
		//返回的Json
		JsonObject retJ=new JsonObject();
		JsonParser jsonParser=new JsonParser();
		
		String addr=doIns.getIpAddr(request);
		try
		{
			if(doOper==null)
				doOper="";
			//根据操作类决定函数
			
			//转换成小写的
			switch (doOper.toLowerCase()) 
			{//增加节点，由MainServer直接操作，不经过用户
				case "deviceadd":
					retString=DeviceADD.deviceAdd(response, request, sql, descrip, devEui, ProfName, devName,appKey,nwkKey,devMode);
					System.out.println("[ServerMessage]DistServer: do-deviceAdd. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//删除节点，由MainServer直接操作，不经过用户
				case "devicedel":
					retString=DeviceDEL.devicedel(response, request, sql, devEui, devMode);
					System.out.println("[ServerMessage]DistServer: do-deviceDel. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//增加网关，由MainServer直接操作，不经过用户
				case "gatewayadd":
					retString=GatewayADD.gatewayAdd(response, request, sql, gatewayID, descrip, devName, devMode);
					System.out.println("[ServerMessage]DistServer: do-gatewayAdd. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//删除网关，由MainServer直接操作，不经过用户	
				case "gatewaydel":
					retString=GatewayDEL.gatewayDel(response, request, sql, gatewayID,devMode);
					System.out.println("[ServerMessage]DistServer: do-gatewayDel. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//增加队列
				case "queueadd":
					retString=DeviceQueue.queueAdd(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-queueAdd. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//队列获取
				case "queueget":
					retString=DeviceQueue.queueGet(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-queueGet. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				case "queuedel":
					retString=DeviceQueue.queueDel(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-queueDel. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				case "getuplinklastpackage":
					retString=GetUplinkRXLast(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-getUplinkLastPackage. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				case "getuplinklastpackage2":
					retString=GetUplinkRXLast2(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-getUplinkLastPackage2. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
					
				case "getdeviceuplinklastpackage":
					retString=GetDeviceUplinkRXLast(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-getdeviceuplinklastpackage. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				case "getdeviceuplinklastpackage2":
					retString=GetDeviceUplinkRXLast2(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-getdeviceuplinklastpackage2. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
					
				case "multicast-group-queue-post":
					retString=MG_queuePOST.queuePOST(response,request,sql, devMode);
					System.out.println("[ServerMessage]DistServer: do-MulticastGroupQueuePost. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//OPT
				case "opt":
					Instructions.insDo(request, sql, out);
					break;
			default:
				System.out.println("[ServerMessage]DistServer: do-default????. IP="+addr+" Query="+request.getQueryString());
				out.print("e:000This is not any enforceable act。");
				break;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("e:"+e.getMessage());
		}
		
		
		
	}

	
	private String GetDeviceUplinkRXLast2(HttpServletResponse response, HttpServletRequest request, Sql sql2,
			boolean devMode2) {
		String retString="e:createGetUplinkRXLastDist";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		String count=request.getParameter("count");
		
		//sql获取总服务器的ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的话获取数据
			retString=sql.getDeviceUplinkRXLast(devEui,count);
		
		}else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		
		return retString;
	}

	private String GetDeviceUplinkRXLast(HttpServletResponse response, HttpServletRequest request, Sql sql2,
			boolean devMode2) {
		String retString="e:createGetDeviceUplinkRXLastDist";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
		//sql获取总服务器的ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的话获取数据
			retString=sql.getDeviceUplinkRXLast(devEui, "1");
		
		}else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		
		return retString;
	}

	private static String GetUplinkRXLast(HttpServletResponse response, HttpServletRequest request, Sql sql,
			boolean devMode)
	{
		String retString="e:createGetUplinkRXLastDist";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
		//sql获取总服务器的ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的话获取数据
			retString=sql.getUplinkRXLast(devEui);
		
		}else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		
		return retString;
	}
	private static String GetUplinkRXLast2(HttpServletResponse response, HttpServletRequest request, Sql sql,
			boolean devMode)
	{
		String retString="e:createGetUplinkRXLastDist";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		String count=request.getParameter("count");
		
		//sql获取总服务器的ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//如果报错获取总服务器IP报错
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//是总服务器的话获取数据
			retString=sql.getUplinkRXLast2(devEui,count);
		
		}else
		{//如果不是
			retString="e:Your address has no permission.YouAddr:"+addr;
		}
		
		return retString;
	}

	public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            String localIp = "127.0.0.1";
            String localIpv6 = "0:0:0:0:0:0:0:1";
            if (ipAddress.equals(localIp) || ipAddress.equals(localIpv6)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        String ipSeparate = ",";
        int ipLength = 15;
        if (ipAddress != null && ipAddress.length() > ipLength) {
            if (ipAddress.indexOf(ipSeparate) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(ipSeparate));
            }
        }
        return ipAddress;
    }
	
	public static boolean getDevMode() {
		return devMode;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
