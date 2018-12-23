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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lora.servletdo.DeviceADD;
import lora.sqloperation.Sql;

/**
 * Servlet implementation class doIns
 */
public class doIns extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		//操作ID
		String operID=request.getParameter("operID");
		//操作Key
		String operKey=request.getParameter("operKey");
		//操作
		String doOper=request.getParameter("doOper");
		//返回的Json
		JsonObject retJ=new JsonObject();
		JsonParser jsonParser=new JsonParser();
		try
		{
			if(doOper==null)
				doOper="";
			//根据操作类决定函数
			switch (doOper) {
			case "deviceADD"://增加节点，由MainServer直接操作，不经过用户
				//response.setContentType(arg0);
				//sql获取总服务器的ip、
				String mainServer=sql.getServerIP();
				if(mainServer.substring(0,1).equals("e"))
				{//如果报错获取总服务器IP报错
					out.print("e:"+mainServer+"-->MainServerGetIPERROR");
					break;
				}
				String addr=getIpAddr(request);
				if(addr.equals(mainServer))
				{//是总服务器的调用
					out.print("AA");
					//DeviceADD deviceADD=new DeviceADD();
					//deviceADD.add();
				}
				else
				{//如果不是
					out.print("e:Your address has no permission.YouAddr:"+addr);
				}
				
				break;

			default:
				out.print("e:000This is not any enforceable act。");
				break;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
