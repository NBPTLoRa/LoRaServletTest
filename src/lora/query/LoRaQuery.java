package lora.query;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import lora.influx.influxDBQuery;

/**
 * Servlet implementation class LoRaQuery
 */
public class LoRaQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

    influxDBQuery iDB;
    /**
     * Default constructor. 
     */
    public LoRaQuery() {
        // TODO Auto-generated constructor stub
    }
    public void init() {
			try {
				iDB=new influxDBQuery();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();//
			}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String table;
		table=request.getParameter("t");
		JsonObject jsonObject=iDB.queryAllinTable(table);
		PrintWriter out=response.getWriter();
		out.println(jsonObject);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
