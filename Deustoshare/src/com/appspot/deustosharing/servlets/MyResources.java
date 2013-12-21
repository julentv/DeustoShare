package com.appspot.deustosharing.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyResources extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String RESOURCES_URL="/pages/jsp/myResources.jsp";
	private static final String NEW_RESOURCE_URL="/pages/jsp/resource.jsp";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if(req.getServletPath().equals("/start/my_resources")){
			myResourcesPage(req,resp);
		    
		}else if(req.getServletPath().equals("/start/my_resources/new")){
			newResourcePage(req,resp);
		}
		
		
	}
	public void myResourcesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//get the user
		
		//redirect to the jsp
		ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(RESOURCES_URL);	
	    rd.forward(req, resp);
	}
	public void newResourcePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		
		
		//redirect to the jsp
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(NEW_RESOURCE_URL);
	    rd.forward(req, resp);
	}

}
