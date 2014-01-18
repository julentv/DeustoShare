package com.appspot.deustosharing.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.jasper.util.Enumerator;

import com.appspot.deustosharing.dao.AppUserDAO;
import com.appspot.deustosharing.dao.ResourcesDAO;
import com.appspot.deustosharing.domainClasses.AppUser;
import com.appspot.deustosharing.domainClasses.Resource;
import com.appspot.deustosharing.domainClasses.Type;
import com.appspot.deustosharing.otherApis.TwitterConnection;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Manages the "My Resources part of the application" including the "New Resource page"
 * @author Julen
 *
 */
public class MyResources extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// paths to accept as input
	private static final String PATH_MY_REQUESTS = "/start/my_resources";
	private static final String PATH_NEW_RESOURCE_PAGE = "/start/my_resources/new";
	private static final String PATH_NEW_RESOURCE_SAVE= "/start/my_resources/new/save";
	private static final String PATH_REQUEST_EDIT= "/start/my_resources/edit";
	private static final String PATH_REQUEST_EDIT_SAVE = "/start/my_resources/edit/save";
	private static final String PATH_REQUEST_EDIT_IMAGE = "/start/my_resources/edit/resourceImageEdit";
	private static final String PATH_REQUEST_IMAGE = "/start/my_resources/resourceImage";
	
	// jsp pages to load
	private static final String RESOURCES_URL="/pages/jsp/myResources.jsp";
	private static final String NEW_RESOURCE_URL="/pages/jsp/resource.jsp";
	private static final String EDIT_RESOURCE_URL="/pages/jsp/resourceEdit.jsp";
	private static final String START_PAGE_URL="/";
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	/**
	 * These first two methods receive the request and act as a dispatcher to the rest.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if(req.getServletPath().equals(PATH_MY_REQUESTS)){
			myResourcesPage(req,resp);
		}else if(req.getServletPath().equals(PATH_NEW_RESOURCE_PAGE)){
			newResourcePage(req,resp);
		}else if(req.getServletPath().equals(PATH_NEW_RESOURCE_SAVE)){
			newResourceSavePage(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_EDIT)){
			editResourcePage(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_EDIT_SAVE)){
			editResourceSavePage(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_IMAGE)){
			serveImage(req,resp);
		}
		
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if(req.getServletPath().equals(PATH_MY_REQUESTS)){
			myResourcesPage(req,resp);
		}else if(req.getServletPath().equals(PATH_NEW_RESOURCE_PAGE)){
			newResourcePage(req,resp);
		}else if(req.getServletPath().equals(PATH_NEW_RESOURCE_SAVE)){
			newResourceSavePage(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_EDIT)){
			editResourcePage(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_EDIT_SAVE)){
			editResourceSavePage(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_EDIT_IMAGE)){
			resourceImageEdit(req,resp);
		}else if(req.getServletPath().equals(PATH_REQUEST_IMAGE)){
			serveImage(req,resp);
		}
	}
	
	/**
	 * Method that manages the page "My resources"
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
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
	
	/**
	 * Method that manages the page of the new resource creation.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void newResourcePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//redirect to the jsp
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(NEW_RESOURCE_URL);
	    rd.forward(req, resp);
	}
	
	/**
	 * Method that manages the creation of the new resources.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
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
		
		//send tweet if the option selected
		if(req.getParameter("tweet")!=null){
			TwitterConnection.sendTweet("I have Share a new Resource at 'http://deustosharing.appspot.com/'");
		}
		
		//save the new resource
		UserService userService = UserServiceFactory.getUserService();
		String email = userService.getCurrentUser().getEmail();
		AppUserDAO userDAO=new AppUserDAO();
		userDAO.addResourceToUser(email, newResource);
		
		//open the "MyResources" page
		myResourcesPage(req, resp);
	}
	private void editResourcePage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException{
		//obtain the resource
		ResourcesDAO rdao= new ResourcesDAO();
		Resource resource=null;
		try{
			long keyLong= Long.parseLong(req.getParameter("resourceid"));
			UserService userService = UserServiceFactory.getUserService();
			resource=rdao.getByPrimaryKey(keyLong,userService.getCurrentUser().getEmail());
			
			//load the page with the resource
			req.setAttribute("resource", resource);
			String url="/images/userIcon.png";
			if(resource.getImage()!=null){
				url="start/my_resources/resourceImage?blob-key="+resource.getImage().getKeyString();
			}
			
			req.setAttribute("url", url);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(EDIT_RESOURCE_URL);
			rd.forward(req, resp);
			
		}catch(Exception e){
			e.printStackTrace();
			myResourcesPage(req,resp);
		}
		
	}
	
	private void editResourceSavePage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException{		
		//colect the data into a resource
		Resource newResource= new Resource("","",null,null);
		String name=(String)req.getParameter("name");
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
		
		//edit the resource
		try{
			UserService userService = UserServiceFactory.getUserService();
			String email = userService.getCurrentUser().getEmail();
			ResourcesDAO rDAO=new ResourcesDAO();
			long keyLong= Long.parseLong(req.getParameter("resourceid"));
			rDAO.updateByPrimaryKey(keyLong, email, newResource);
		}catch(Exception e){
			System.out.println("The resource was not edited. "+e.getMessage());
		}finally{
			//open the "MyResources" page
			myResourcesPage(req, resp);
		}
	}
	
	private void resourceImageEdit (HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException{
		

	    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
	    List<BlobKey> blobKeyList = blobs.get("photoimg");
	    BlobKey key= blobKeyList.get(0);
	    String url="start/my_resources/resourceImage?blob-key="+key.getKeyString();
	    
	    //obtain the resource
	    String resourceId=(String) req.getParameter("resourceid");
  		ResourcesDAO rdao= new ResourcesDAO();
  		Resource resource=null;
  		String response="";
  		try{
  			long keyLong= Long.parseLong(resourceId);
  			UserService userService = UserServiceFactory.getUserService();
  			resource=rdao.getByPrimaryKey(keyLong,userService.getCurrentUser().getEmail());
  			//if the resource already has an image delete it from data store
  			if(resource.getImage()!=null){
  				blobstoreService.delete(resource.getImage());
  			}
  			//update the resource
  			resource.setImage(key);
  			rdao.updateByPrimaryKey(keyLong,userService.getCurrentUser().getEmail(), resource);
  			response="<img id='resource-image' src='"+url+"' onclick=\"window.open('"+url+"','_blank');\" alt='resource-image'>";
  		}catch(Exception e){
  			response="<li id=\"preview\"><img id=\"resource-image\"src=\"images/userIcon.png\" alt=\"resour\"></li>";
  		}
	  	
		//response="OK!";
		resp.setContentType("text/html");
		resp.getWriter().write(response);
	    
	}
	
	private void serveImage (HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        blobstoreService.serve(blobKey, resp);
	}
	

}
