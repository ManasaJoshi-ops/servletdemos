package com.userwebapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
    private Statement statement =null;
    public void init(ServletConfig config) {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		ServletContext context=config.getServletContext();
			String dburl = context.getInitParameter("dburl");
			String dbuser = context.getInitParameter("dbuser");
			String dbpassword = context.getInitParameter("dbpassword");
			connection =DriverManager.getConnection(dburl, dbuser,dbpassword);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String emailId = request.getParameter("emailId");
		String password = request.getParameter("password");
		
		
		try(
				 Statement statement=connection.createStatement();) {
			int result=statement.executeUpdate("insert into user values('"+ firstname +"','"+lastname+"','"+emailId+"','"+password+"')");
				PrintWriter out=response.getWriter();
				response.setContentType("text/html");
				if(result>0) {
					out.println("<h1>User Created Successfully</h1>");
					
				}else {
					out.println("<h1>Error Creating user</h1>");
				}
				out.print("<a href=\"index.html\">Home</a>");
			
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		
	}
	public void destroy() {
		if(connection !=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
