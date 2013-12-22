package com.appspot.deustosharing.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.appspot.deustosharing.dao.AppUserDAO;
import com.appspot.deustosharing.domainClasses.AppUser;
import com.appspot.deustosharing.domainClasses.Resource;
import com.appspot.deustosharing.domainClasses.Type;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Manages the "My Resources part of the application" including the "New Resource page"
 * @author Julen
 *
 */
public class MyResources extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String RESOURCES_URL="/pages/jsp/myResources.jsp";
	private static final String NEW_RESOURCE_URL="/pages/jsp/resource.jsp";
	private static final String START_PAGE_URL="/";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if(req.getServletPath().equals("/start/my_resources")){
			myResourcesPage(req,resp);
		    
		}else if(req.getServletPath().equals("/start/my_resources/new")){
			newResourcePage(req,resp);
		}else if(req.getServletPath().equals("/start/my_resources/new/save")){
			newResourceSavePage(req,resp);
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if(req.getServletPath().equals("/start/my_resources")){
			myResourcesPage(req,resp);
		    
		}else if(req.getServletPath().equals("/start/my_resources/new")){
			newResourcePage(req,resp);
		}else if(req.getServletPath().equals("/start/my_resources/new/save")){
			newResourceSavePage(req,resp);
		}
	}
	
	public void myResourcesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//get the user
		UserService userService = UserServiceFactory.getUserService();
		String email = userService.getCurrentUser().getEmail();
		AppUserDAO userDAO = new AppUserDAO();
		AppUser currentUser = userDAO.GetByPrimaryKey(email);
		if(currentUser==null){
			//redirect to the start page
			ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(START_PAGE_URL);	
		    rd.forward(req, resp);
		}else{
			//pass the resource list of the user to the jsp
			req.setAttribute("myResources", currentUser.getResourceList());
			//redirect to the jsp
			ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(RESOURCES_URL);	
		    rd.forward(req, resp);
		}
		
		
	}
	public void newResourcePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//redirect to the jsp
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(NEW_RESOURCE_URL);
	    rd.forward(req, resp);
	}
	
	private void newResourceSavePage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException{		
		//create the new resource
		Resource newResource= new Resource("","",null,null);
		String name=(String) req.getParameter("name");
		if(name!=null){
			newResource.setTitle(name);
		}
		String description=(String) req.getParameter("description");
		if(description!=null){
			newResource.setDescription(description);
		}
		String stringType=(String)req.getParameter("type");
		if(stringType!=null){
			try{
				Type type=Type.valueOf(stringType);
				newResource.setType(type);
			}catch(Exception e){
				System.out.println("Can't create the Type from the string. "+e.getMessage());
			}
			
			
		}
		if(req.getParameter("visible")!=null){
			newResource.setVisible(true);
		}else{
			newResource.setVisible(false);
		}
		
		//save the new resource
		UserService userService = UserServiceFactory.getUserService();
		String email = userService.getCurrentUser().getEmail();
		AppUserDAO userDAO=new AppUserDAO();
		userDAO.addResourceToUser(email, newResource);
		
		//open the "MyResources" page
		myResourcesPage(req, resp);
		
		
	}

}
