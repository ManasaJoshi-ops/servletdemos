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
    private Statement statement =null;
    public void init() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost/my_db", "root","MYPASS");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try(
				 Statement statement=connection.createStatement();
				ResultSet result=statement.executeQuery("select * from user");) {
				PrintWriter out=response.getWriter();
				response.setContentType("text/html");
				
				out.print("<table>");
				out.print("<tr>");
				out.print("<th>First name</th>");
				out.print("<th>Last name</th>");
				out.print("<th>Email id</th>");
				out.print("</tr>");
				while(result.next()) {
					String firstname = result.getString(1);
					String lastname = result.getString(2);
					String email = result.getString(3);
					out.print("<tr>");
					out.print("<td>"+firstname+"</th>");
					out.print("<td>"+lastname+"</td>");
					out.print("<td>"+email+"</td>");
					out.print("</tr>");
					
				}
				out.print("</table>");
				out.println("<a href=\"index.html\">Home</a>");
			
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

	
	