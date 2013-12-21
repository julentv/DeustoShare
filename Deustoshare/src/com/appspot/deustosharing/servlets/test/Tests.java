package com.appspot.deustosharing.servlets.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deustosharing.dao.AppUserDAO;
import com.appspot.deustosharing.domainClasses.AppUser;

public class Tests extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String viewUrl="/pages/jsp/home.jsp";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		saveUser();
		
		PrintWriter out = resp.getWriter();
	    out.println("Hello World");
	    out.println(getUser());
	    
	}
	
	/*____________PRUEBAS CON USUARIOS____________*/
	private String userEmail="prueba3@dominio.com";
	private void saveUser(){
		AppUser user= new AppUser(userEmail);
		user.setName("Nombre de prueba");
		AppUserDAO uDAO= new AppUserDAO();
		uDAO.insert(user);
	} 
	private AppUser getUser(){
		AppUserDAO uDAO= new AppUserDAO();
		return uDAO.GetByPrimaryKey(this.userEmail);
	}


}
