package com.appspot.deustosharing.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deustosharing.dao.AppUserDAO;
import com.appspot.deustosharing.dao.RequestsDAO;
import com.appspot.deustosharing.dao.ResourcesDAO;
import com.appspot.deustosharing.domainClasses.AppUser;
import com.appspot.deustosharing.domainClasses.Request;
import com.appspot.deustosharing.domainClasses.Resource;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class MyRequests extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// paths to accept as input
	private static final String PATH_MY_REQUESTS = "/start/my_requests";
	private static final String PATH_NEW_REQUESTS_PAGE = "/start/request";
	private static final String PATH_NEW_REQUESTS_CREATE = "/start/request/create";
	private static final String PATH_NEW_REQUESTS_SHOW = "/start/request/show";
	// jsp pages to load
	private final String VIEW_URL = "/pages/jsp/myRequests.jsp";
	private final String REQUEST_URL = "/pages/jsp/request.jsp";
	private final String REQUEST_READ_URL = "/pages/jsp/requestRead.jsp";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (req.getServletPath().equals(PATH_MY_REQUESTS)) {
			myRequests(req, resp);
		} else if (req.getServletPath().equals(PATH_NEW_REQUESTS_PAGE)) {
			newRequestPage(req, resp);
		} else if (req.getServletPath().equals(PATH_NEW_REQUESTS_CREATE)) {
			newRequestCreate(req, resp);
		} else if (req.getServletPath().equals(PATH_NEW_REQUESTS_SHOW)) {
			showRequest(req, resp);
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (req.getServletPath().equals(PATH_MY_REQUESTS)) {
			myRequests(req, resp);
		} else if (req.getServletPath().equals(PATH_NEW_REQUESTS_PAGE)) {
			newRequestPage(req, resp);
		} else if (req.getServletPath().equals(PATH_NEW_REQUESTS_CREATE)) {
			newRequestCreate(req, resp);
		} else if (req.getServletPath().equals(PATH_NEW_REQUESTS_SHOW)) {
			showRequest(req, resp);
		}
	}

	private void myRequests(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// obtain the requests of the user
		UserService userService = UserServiceFactory.getUserService();
		String email = userService.getCurrentUser().getEmail();
		AppUserDAO userDAO = new AppUserDAO();
		AppUser currentUser = userDAO.GetByPrimaryKey(email);
		req.setAttribute("requests", currentUser.getRequestList());

		// relative url for display jsp page
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(this.VIEW_URL);
		rd.forward(req, resp);
	}

	/**
	 * Opens the page for the creation of a new request.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void newRequestPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// obtain the resource
		ResourcesDAO rdao = new ResourcesDAO();
		Resource resource = null;
		try {
			long keyLong = Long.parseLong(req.getParameter("resourceid"));
			String resourceOwnerEmail = req.getParameter("ownerEmail");

			resource = rdao.getByPrimaryKey(keyLong, resourceOwnerEmail);

			// load the page with the resource
			req.setAttribute("resource", resource);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(REQUEST_URL);
			rd.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			myRequests(req, resp);
		}
	}

	private void newRequestCreate(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		// obtain the resource
		ResourcesDAO rdao = new ResourcesDAO();
		Resource resource = null;
		try {
			long keyLong = Long.parseLong(req.getParameter("resourceid"));
			String resourceOwnerEmail = req.getParameter("ownerEmail");
			resource = rdao.getByPrimaryKey(keyLong, resourceOwnerEmail);

			// obtain the logged user
			UserService userService = UserServiceFactory.getUserService();
			String email = userService.getCurrentUser().getEmail();
			AppUserDAO userDAO = new AppUserDAO();
			AppUser currentUser = userDAO.GetByPrimaryKey(email);

			// obtain the dates
			SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
			Date startDate = formatter.parse(req.getParameter("from"));
			Date enDate = formatter.parse(req.getParameter("to"));
			

			// create the request
			Request newRequest = new Request(currentUser, resource.getKey(),
					startDate, enDate);

			// save the request
			userDAO.addRequestToUser(email, newRequest);

			// open the my Requests page
			myRequests(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			// open the my Requests page
			myRequests(req, resp);
		}

	}

	private void showRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		// obtain the request
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
		RequestDispatcher rd = sc.getRequestDispatcher(REQUEST_READ_URL);
		rd.forward(req, resp);

	}

}
