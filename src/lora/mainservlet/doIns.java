package lora.mainservlet;

import java.io.IOException;
import java.io.PrintWriter;

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
			//���ݲ������������
			switch (doOper) {
			case "deviceADD"://���ӽڵ㣬��MainServerֱ�Ӳ������������û�
				//sql��ȡ�ܷ�������ip��
				String mainServer=sql.getServerIP();
				if(mainServer.substring(0,1).equals("e"))
				{//��������ȡ�ܷ�����IP����
					out.print(mainServer+"-->MainServerGetIPERROR");
					break;
				}
				
				if(!request.getRemoteAddr().equals(mainServer))
				{//���ܷ������ĵ���
					DeviceADD deviceADD=new DeviceADD();
					deviceADD.add();
				}
				else
				{//�������
					out.print("e:Your address has no permission.YouAddr:"+request.getRemoteAddr());
				}
				break;

			default:
				doOper="error";
				break;
			}
			
		}
		catch (Exception e)
		{
			
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
