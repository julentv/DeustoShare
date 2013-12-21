package com.appspot.deustosharing.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchResources extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String VIEW_URL="/pages/jsp/searchResources.jsp";
	private static final String SHOW_RESOURCE_URL="/pages/jsp/resourceRead.jsp";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if(req.getServletPath().equals("/start/search_resources")){
			//relative url for display jsp page
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(VIEW_URL);

		    rd.forward(req, resp);
		}else if(req.getServletPath().equals("/start/resource")){
			showResource(req, resp);
		}
		 
		
	}
	private void showResource(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(SHOW_RESOURCE_URL);

	    rd.forward(req, resp);
	}

}
