package com.appspot.deustosharing.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deustosharing.dao.AppUserDAO;
import com.appspot.deustosharing.dao.RequestsDAO;
import com.appspot.deustosharing.dao.ResourcesDAO;
import com.appspot.deustosharing.domainClasses.Request;
import com.appspot.deustosharing.domainClasses.RequestState;
import com.appspot.deustosharing.domainClasses.Resource;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ReceivedRequests extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// paths to accept as input
	private static final String PATH_RECEIVED_REQUESTS = "/start/received_requests";
	private static final String PATH_SHOW_RECEIVED_REQUEST = "/start/received_requests/show";
	private static final String PATH_STATE_RECEIVED_REQUEST = "/start/received_requests/changeState";
	// jsp pages to load
	private final String VIEW_URL = "/pages/jsp/receivedRequests.jsp";
	private final String SHOW_RECEIVED_REQUEST = "/pages/jsp/requestReceived.jsp";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		if (req.getServletPath().equals(PATH_RECEIVED_REQUESTS)) {
			openReceivedRequestsPage(req, resp);
		} else if (req.getServletPath().equals(PATH_SHOW_RECEIVED_REQUEST)) {
			showRequest(req,resp);
		} else if (req.getServletPath().equals(PATH_STATE_RECEIVED_REQUEST)) {
			changeRequestState(req,resp);
		}

	}

	private void openReceivedRequestsPage(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		//obtain the email of the logged user
		UserService userService = UserServiceFactory.getUserService();
		String email = userService.getCurrentUser().getEmail();
		//obtain the requests
		AppUserDAO userDAO= new AppUserDAO();
		List<Request> requestList=userDAO.getUserReceivedRequests(email);
		
		//open the jsp page with the list of Requests
		req.setAttribute("requests", requestList);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(this.VIEW_URL);
		rd.forward(req, resp);
	}
	
	private void showRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//obtain the request
		long keyLong = Long.parseLong(req.getParameter("requestid"));
		String requesterEmail = req.getParameter("requesterEmail");
		RequestsDAO rDAO= new RequestsDAO();
		Request request=rDAO.getByPrimaryKey(keyLong, requesterEmail);
		
		// obtain the resource
		ResourcesDAO resourcesDao= new ResourcesDAO();
		Resource resource=resourcesDao.getByPrimaryKey(request.getResource());

		// load the page with the request
		req.setAttribute("request", request);
		req.setAttribute("resource", resource);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(SHOW_RECEIVED_REQUEST);
		rd.forward(req, resp);
	}
	
	private void changeRequestState(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			//change state
			RequestState newState = RequestState.valueOf(req.getParameter("newState"));
			long keyLong = Long.parseLong(req.getParameter("requestid"));
			String requesterEmail = req.getParameter("requesterEmail");
			RequestsDAO rDAO= new RequestsDAO();
			rDAO.updateRequestState(keyLong, requesterEmail,newState);
		}catch(Exception e){
			System.out.println("The state of the request couldn't be changed");
			e.printStackTrace();
		}
		

		// load the page with the request
		openReceivedRequestsPage(req,resp);
	}

}
