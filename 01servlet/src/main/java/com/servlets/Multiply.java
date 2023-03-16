package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Multiply extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	     String Num1 =req.getParameter("num1");
	     String Num2=req.getParameter("num2");
	     PrintWriter out=res.getWriter();
	     if(Num1==null||Num2==null||Num1.isBlank()||Num2.isBlank()||Num1.isEmpty()||Num2.isEmpty()) {
	    	 out.println("Invalid Input");
	     }else {
	    	 try {
	     int num1int=Integer.parseInt(Num1);
	     int num2int=Integer.parseInt(Num2);
	     int result=num1int*num2int;
	   
	     out.println("Result= "+result);
	    	 }catch(NumberFormatException e) {
	    		 out.println("Enter an integer");
	    	 }
	     }
	}

}
