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
		
		//����ID
		String operID=request.getParameter("operID");
		//����Key
		String operKey=request.getParameter("operKey");
		//����
		String doOper=request.getParameter("doOper");
		//���ص�Json
		JsonObject retJ=new JsonObject();
		JsonParser jsonParser=new JsonParser();
		try
		{
			if(doOper==null)
				doOper="";
			//���ݲ������������
			switch (doOper) {
			case "deviceADD"://���ӽڵ㣬��MainServerֱ�Ӳ������������û�
				//response.setContentType(arg0);
				//sql��ȡ�ܷ�������ip��
				String mainServer=sql.getServerIP();
				if(mainServer.substring(0,1).equals("e"))
				{//��������ȡ�ܷ�����IP����
					out.print("e:"+mainServer+"-->MainServerGetIPERROR");
					break;
				}
				String addr=getIpAddr(request);
				if(addr.equals(mainServer))
				{//���ܷ������ĵ���
					out.print("AA");
					//DeviceADD deviceADD=new DeviceADD();
					//deviceADD.add();
				}
				else
				{//�������
					out.print("e:Your address has no permission.YouAddr:"+addr);
				}
				
				break;

			default:
				out.print("e:000This is not any enforceable act��");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
