package lora.mainservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lora.setInsDo.SetDeviceRX;
import lora.setInsDo.SetUplinkRX;
import lora.sqloperation.Sql;

/**
 * Servlet implementation class SetInstruction
 */
public class SetInstruction extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	Sql sql;
    public SetInstruction() {
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
		
		String hwOPT=request.getParameter("hwOPT");		//������
		String userID=request.getParameter("userID");		//�û�ID
		
		String retString="e:Create";
		
		//sql��ȡ�ܷ�������ip��
		String mainServer=sql.getServerIP();
		if(mainServer.substring(0,1).equals("e"))
		{//��������ȡ�ܷ�����IP����
			retString="e:"+mainServer+"-->MainServerGetIPERROR";
			out.println(retString);
		}
		String addr=doIns.getIpAddr(request);
		if(addr.equals(mainServer)||doIns.getDevMode())
		{//�����������������趨
			try
			{
				if(hwOPT==null)
					hwOPT="";
				//���ݲ������������
			
				//ת����Сд��
				switch (hwOPT.toLowerCase()) 
				{//���ӽڵ㣬��MainServerֱ�Ӳ������������û�
					case "devicerx1":
						retString=SetDeviceRX.deviceRX(request,sql);
						System.out.println("[ServerMessage]DistServer: set-deviceRX1. IP="+addr+" userID="+userID);
						out.print(retString);
						break;
					case "devicerx2":
						retString=SetDeviceRX.deviceRX(request,sql);
						System.out.println("[ServerMessage]DistServer: set-deviceRX2. IP="+addr+" userID="+userID);
						out.print(retString);
						break;
					case "uplinkrx1":
						retString=SetUplinkRX.uplinkRX(request,sql);
						System.out.println("[ServerMessage]DistServer: set-uplinkRX1. IP="+addr+" userID="+userID);
						out.print(retString);
						break;
					case "uplinkrx2":
						retString=SetUplinkRX.uplinkRX(request,sql);
						System.out.println("[ServerMessage]DistServer: set-uplinkRX2. IP="+addr+" userID="+userID);
						out.print(retString);
						break;
					default:
						out.print("e:000This is not any enforceable act to setInstruction��");
						break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				out.print("e:"+e.getMessage());
			}
		}
		else
		{//���������������
			retString="e:Your address has no permission.YouAddr:"+addr;
			out.println(retString);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	public static String randomHexString(int len)  {
		try {
			StringBuffer result = new StringBuffer();
			for(int i=0;i<len;i++) {
				result.append(Integer.toHexString(new Random().nextInt(16)));
			}
			return result.toString().toUpperCase();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return null;
	}
}
