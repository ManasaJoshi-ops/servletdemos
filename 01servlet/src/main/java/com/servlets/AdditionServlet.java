package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AdditionServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String number1=req.getParameter("number1");
		String number2=req.getParameter("number2");
		PrintWriter out=res.getWriter();
		if(number1==null || number2==null || number1.isEmpty() || number2.isEmpty() || number1.isBlank() || number2.isBlank()) {
			out.println("Invalid Input");
		}
		else {
			try {
				int num1int=Integer.parseInt(number1);
				int num2int=Integer.parseInt(number2);
				int result=num1int+num2int;
				
				out.println("<p>The result is:"+(result)+"</p>");
			}catch(NumberFormatException e) {
				out.println("Invalid Input");
			}
		
		}
	}

}
