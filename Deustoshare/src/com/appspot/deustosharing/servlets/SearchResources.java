package com.appspot.deustosharing.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deustosharing.dao.RequestsDAO;
import com.appspot.deustosharing.dao.ResourcesDAO;
import com.appspot.deustosharing.domainClasses.Request;
import com.appspot.deustosharing.domainClasses.Resource;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SearchResources extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//paths to accept as input
	private static final String PATH_SEARCH_PAGE="/start/search_resources_page";
	private static final String PATH_SHOW_RESOURCE="/start/resource";
	private static final String PATH_SEARCH_RESOURCES="/start/search_resources";
	
	//jsp pages to load
	private static final String VIEW_URL="/pages/jsp/searchResources.jsp";
	private static final String SHOW_RESOURCE_URL="/pages/jsp/resourceRead.jsp";
	private static final String SEARCH_RESULTS_URL="/pages/jsp/searchResult.jsp";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if(req.getServletPath().equals(PATH_SEARCH_PAGE)){
			searchPage(req,resp);
		}else if(req.getServletPath().equals(PATH_SHOW_RESOURCE)){
			showResource(req, resp);
		}else if(req.getServletPath().equals(PATH_SEARCH_RESOURCES)){
			searchResources(req, resp);
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if(req.getServletPath().equals(PATH_SEARCH_PAGE)){
			searchPage(req,resp);
		}else if(req.getServletPath().equals(PATH_SHOW_RESOURCE)){
			showResource(req, resp);
		}else if(req.getServletPath().equals(PATH_SEARCH_RESOURCES)){
			searchResources(req, resp);
		}
	}
	private void searchPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//load the page
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(VIEW_URL);
	    rd.forward(req, resp);
	}
	
	/**
	 * Loads one resource so it can be requested.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showResource(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//obtain the resource
		ResourcesDAO rdao = new ResourcesDAO();
		Resource resource = null;
		try{
			long keyLong= Long.parseLong(req.getParameter("resourceid"));
			String email= req.getParameter("ownerEmail");
			resource=rdao.getByPrimaryKey(keyLong,email);
			//look if is available now.
			boolean available=false;
			RequestsDAO reqDAO= new RequestsDAO();
			List<Request>resources=reqDAO.getByKeyResource(resource.getKey());
			available=resource.isAvailable(resources);
			
			//look if the owner is the same as the logged user
			UserService userService = UserServiceFactory.getUserService();
			String logedUserEmail = userService.getCurrentUser().getEmail();
			boolean isMine=false;
			if(email.equals(logedUserEmail)){
				isMine=true;
			}
			//load the page with the resource
			req.setAttribute("resource", resource);
			req.setAttribute("available", available);
			req.setAttribute("isMine", isMine);
			String url="/images/userIcon.png";
			if(resource.getImage()!=null){
				url="start/my_resources/resourceImage?blob-key="+resource.getImage().getKeyString();
			}
			
			req.setAttribute("url", url);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(SHOW_RESOURCE_URL);
			rd.forward(req, resp);
			
		}catch(Exception e){
			e.printStackTrace();
			searchPage(req,resp);
		}
	}
	
	/**
	 * Method that makes the search of the resources and displays them into a page.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchResources(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//obtain the data
		String name=(String) req.getParameter("name");
		String typeString=(String) req.getParameter("type");
		//search the resources
		ResourcesDAO rdao=new ResourcesDAO();
		List<Resource>resources=rdao.searchResources(name, typeString);
		//pass the resources to the page
		req.setAttribute("resources", resources);
		//load the page
		ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(SEARCH_RESULTS_URL);
	    rd.forward(req, resp);
	}

}
