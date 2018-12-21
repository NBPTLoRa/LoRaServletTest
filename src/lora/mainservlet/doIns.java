package lora.mainservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

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
		String operID=request.getParameter("operID");
		String operKey=request.getParameter("operKey");
		String doOper;
		JsonObject retJ=new JsonObject();
		try
		{
			//从指令库中获取操作类-
			doOper="";
			//根据操作类决定函数
			switch (doOper) {
			case "deviceADD":
				//sql获取总服务器的ip、
				String mainServer="";
				if(!request.getRemoteAddr().equals(mainServer))
				{//是总服务器的调用
					DeviceADD deviceADD=new DeviceADD();
					deviceADD.add();
				}
				else
				{//如果不是
					
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
