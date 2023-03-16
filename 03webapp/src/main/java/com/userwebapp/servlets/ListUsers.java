package com.userwebapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListUsers")
public class ListUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost/my_db","root","MYPASS");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		
		
		try( Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery("select * from user");) {
			    PrintWriter out=response.getWriter();
			    response.setContentType("Text/Html");
			    
				out.println("<table border=1>");
				out.println("<tr>");
				out.println("<th>firstname</th>");
				out.println("<th>lastname</th>");
				out.println("<th>email</th>");
				out.println("</tr");
				
				while(result.next()) {
					String firstname = result.getString(1);
					String lastname = result.getString(2);
					String email = result.getString(3);
					out.println("<tr>");
					out.println("<td>"+firstname+"</td>");
					out.println("<td>"+lastname+"</td>");
					out.println("<td>"+email+"</td>");
					out.println("</tr");
							
			} 
				out.println("<table>");
				out.println("<a href=\"index.html\">Home</a>");
				
				
		}catch (SQLException e) {
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
