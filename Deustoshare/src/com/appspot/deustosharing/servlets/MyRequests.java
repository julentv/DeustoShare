package com.appspot.deustosharing.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyRequests extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String VIEW_URL="/pages/jsp/myRequests.jsp";
	private final String REQUEST_URL="/pages/jsp/request.jsp";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		if(req.getServletPath().equals("/start/my_requests")){
			//relative url for display jsp page
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(this.VIEW_URL);

		    rd.forward(req, resp);
		}else if(req.getServletPath().equals("/start/request")){
			newRequest(req,resp);
		}
	}
	private void newRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//relative url for display jsp page
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(this.REQUEST_URL);
	    rd.forward(req, resp);
	}

}
