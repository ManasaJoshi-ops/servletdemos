package com.web.interServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
    private PreparedStatement preparedStatement;
    public void init(ServletConfig config) {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		ServletContext context=config.getServletContext();
			String dburl = context.getInitParameter("dburl");
			String dbuser = context.getInitParameter("dbuser");
			String dbpassword = context.getInitParameter("dbpassword");
			connection =DriverManager.getConnection(dburl, dbuser,dbpassword);
			preparedStatement=connection.prepareStatement("select * from user where email=? and password=?");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		if(!isValid(username,false)||!isValid(password, false)) {
			out.println("Please Enter a Valid Input");
			return;
		}
		ResultSet resultSet=null;
		
		try {
			preparedStatement.setString(1,username);
			preparedStatement.setString(2,password);
			boolean result=preparedStatement.execute();
			if (result) {
				resultSet=preparedStatement.getResultSet();
			}
			if(resultSet.next()) {
				out.println("Successfully logged in..");
				RequestDispatcher rd=request.getRequestDispatcher("homePage");
				Object welcomeMessage="Welcome to Home page"+username+"!!";
				request.setAttribute("message", welcomeMessage);
				rd.include(request, response);
				
			}else {
				out.println("<h4>User not found..</h4>");
				RequestDispatcher rd=request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}			
	}
	
	private boolean isValid(String inputValue, boolean isNumber) {
		if(inputValue==null || inputValue.isBlank()|| inputValue.isEmpty()) {
			return false;
		}else if(isNumber) {
			try {
				Integer.parseInt(inputValue);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}else {
			return true;
		}
	}
	
	public void destroy() {
		try {
			if(connection !=null) {
				connection.close();
			}
			if(preparedStatement!=null) {
				preparedStatement.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}


