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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Manages the Home page of the application
 * @author Julen
 *
 */
public class Home extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String viewUrl="/pages/jsp/home.jsp";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		//obtain the logged user
		UserService userService = UserServiceFactory.getUserService();
		String email=userService.getCurrentUser().getEmail();
		AppUserDAO userDAO= new AppUserDAO();
		AppUser currentUser=userDAO.GetByPrimaryKey(email);
		
		//if the user is not at the DB create it.
		if(currentUser==null){
			System.out.println("Creating the user");
			currentUser= new AppUser(email);
			currentUser.setName(emailToNick(userService.getCurrentUser().getNickname()));
			userDAO.insert(currentUser);
		}
		
		//add the User to the atributes of the request so the jsp page can use it.
		req.setAttribute("currentUser", currentUser);
		//redirect to the jsp page
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(this.viewUrl);
	    rd.forward(req, resp);
		
	}
	
	/**
	 * Obtain the nick name from a String with email format
	 * @param email
	 * @return the nick name
	 */
	private String emailToNick(String email){
		if(email.contains("@")){
			return email.split("@")[0];
		}else{
			return email;
		}
		
	}

}
