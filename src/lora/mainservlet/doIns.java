package lora.mainservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Case;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lora.servletdo.DeviceADD;
import lora.servletdo.DeviceDEL;
import lora.servletdo.DeviceQueue;
import lora.servletdo.GatewayADD;
import lora.servletdo.GatewayDEL;
import lora.servletdo.Instructions;
import lora.sqloperation.Sql;

/**
 * Servlet implementation class doIns
 */
public class doIns extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//����ģʽ
	//�����׶�ʱ����дΪTrue�����������������IP��ַ
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
		
		String doOper=request.getParameter("doOper");	//Ҫ���Ĳ���
		
		String devEui=request.getParameter("devEui");	//�豸ID
		String ProfName=request.getParameter("ProfName");//�豸����
		String descrip=request.getParameter("descrip");	//�豸��ע
		String devName=request.getParameter("devName");	//�豸����
		String userID=request.getParameter("userID");	//�û�ID
		
		String gatewayID=request.getParameter("gatewayID");			//����ID
		
		String retString="e:Create";//
		
		//���ص�Json
		JsonObject retJ=new JsonObject();
		JsonParser jsonParser=new JsonParser();
		
		String addr=doIns.getIpAddr(request);
		try
		{
			if(doOper==null)
				doOper="";
			//���ݲ������������
			
			//ת����Сд��
			switch (doOper.toLowerCase()) 
			{//���ӽڵ㣬��MainServerֱ�Ӳ������������û�
				case "deviceadd":
					retString=DeviceADD.deviceAdd(response, request, sql, descrip, devEui, ProfName, devName,devMode);
					System.out.println("[ServerMessage]DistServer: do-deviceAdd. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//ɾ���ڵ㣬��MainServerֱ�Ӳ������������û�
				case "devicedel":
					retString=DeviceDEL.devicedel(response, request, sql, devEui, devMode);
					System.out.println("[ServerMessage]DistServer: do-deviceDel. IP="+addr+" userID="+userID+" ret="+retString);
					out.print(retString);
					break;
				//�������أ���MainServerֱ�Ӳ������������û�
				case "gatewayadd":
					System.out.println("[ServerMessage]DistServer: do-gatewayAdd. IP="+addr+" userID="+userID);
					retString=GatewayADD.gatewayAdd(response, request, sql, gatewayID, descrip, devName, devMode);
					out.print(retString);
					break;
				//ɾ�����أ���MainServerֱ�Ӳ������������û�	
				case "gatewaydel":
					System.out.println("[ServerMessage]DistServer: do-gatewayDel. IP="+addr+" userID="+userID);
					retString=GatewayDEL.gatewayDel(response, request, sql, gatewayID,devMode);
					out.print(retString);
					break;
				//���Ӷ���
				case "queueadd":
					System.out.println("[ServerMessage]DistServer: do-queueAdd. IP="+addr+" userID="+userID);
					retString=DeviceQueue.queueAdd(response,request,sql, devMode);
					out.print(retString);
					break;
				case "queueget":
					System.out.println("[ServerMessage]DistServer: do-queueGet. IP="+addr+" userID="+userID);
					retString=DeviceQueue.queueGet(response,request,sql, devMode);
					out.print(retString);
					break;
				case "queuedel":
					System.out.println("[ServerMessage]DistServer: do-queueDel. IP="+addr+" userID="+userID);
					retString=DeviceQueue.queueDel(response,request,sql, devMode);
					out.print(retString);
					break;
				case "getuplinklastpackage":
					System.out.println("[ServerMessage]DistServer: do-getUplinkLastPackage. IP="+addr+" userID="+userID);
					retString=GetUplinkRXLast(response,request,sql, devMode);
					out.print(retString);
					break;
				case "getuplinklastpackage2":
					System.out.println("[ServerMessage]DistServer: do-getUplinkLastPackage. IP="+addr+" userID="+userID);
					retString=GetUplinkRXLast2(response,request,sql, devMode);
					out.print(retString);
					break;
					
				//OPT
				case "opt":
					Instructions.insDo(request, sql, out);
					break;
			default:
				System.out.println("[ServerMessage]DistServer: do-default????. IP="+addr+" Query="+request.getQueryString());
				out.print("e:000This is not any enforceable act��");
				break;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("e:"+e.getMessage());
		}
		
		
		
	}

	
	private static String GetUplinkRXLast(HttpServletResponse response, HttpServletRequest request, Sql sql,
			boolean devMode)
	{
		String retString="e:createGetUplinkRXLastDist";
		response.setContentType("html/text;charset=UTF-8");
		
		String devEui=request.getParameter("devEui");
		
		//sql��ȡ�ܷ�������ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//��������ȡ�ܷ�����IP����
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//���ܷ������Ļ���ȡ����
			retString=sql.getUplinkRXLast(devEui);
		
		}else
		{//�������
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
		
		//sql��ȡ�ܷ�������ip
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//��������ȡ�ܷ�����IP����
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			return retString;
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||devMode)
		{//���ܷ������Ļ���ȡ����
			retString=sql.getUplinkRXLast2(devEui,count);
		
		}else
		{//�������
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
                // ��������ȡ�������õ�IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // ����ͨ�����������������һ��IPΪ�ͻ�����ʵIP,���IP����','�ָ�
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
