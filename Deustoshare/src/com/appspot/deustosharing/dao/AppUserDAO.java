package com.appspot.deustosharing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.appspot.deustosharing.domainClasses.AppUser;
import com.appspot.deustosharing.domainClasses.Request;
import com.appspot.deustosharing.domainClasses.Resource;

/**
 * Class to manage the DDBB operations of the AppUser class
 * @author Julen
 *
 */
public class AppUserDAO {
	private PersistenceManagerFactory pmf;
	public AppUserDAO(){
		this.pmf= PMF.get();
	}
	/**
	 * Inserts a new user into the DB
	 * @param user to be inserted
	 * @return whether the user has been inserted.
	 */
	public boolean insert(AppUser user){
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean saved=false;
		try{
			pm.makePersistent(user);
			saved=true;
		}catch(Exception e){
			System.out.println("Can't save the User. "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			pm.close();
		}
		return saved;
	}
	/**
	 * Obtains the user of the database with the email indicated
	 * or null if there is not any user with that email.
	 * @param email
	 * @return
	 */
	public AppUser GetByPrimaryKey(String email){
		AppUser user=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		try{
			user=pm.getObjectById(AppUser.class, email);
			ArrayList<Request>listReq=user.getRequestList();
			pm.retrieveAll(listReq);
			ArrayList<Resource>listRes=user.getResourceList();
			pm.retrieveAll(listRes);
			
			
		}catch(Exception e){
			System.out.println("Can't get the User. "+e.getMessage());
			e.printStackTrace();
			
		}finally{
			pm.close();
		}
		return user;
	}
	
	/**
	 * Updates the user with the email of the user pass as a parameter with the
	 * values in that user.
	 * @param newUser the user containing the new values to be updated and the email.
	 */
	public void update (AppUser newUser){
		AppUser oldUser=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		try{
			oldUser=pm.getObjectById(AppUser.class, newUser.getEmail());
			oldUser.setName(newUser.getName());
		}finally{
			pm.close();
		}
	}
	public boolean delete(AppUser user){
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean deleted=false;
		try{
			pm.deletePersistent(user);
			deleted=true;
		}finally{
			pm.close();
		}
		return deleted;
	}
	/**
	 * Adds a new resource to one user
	 * @param userEmail email of the owner user of the resource to add.
	 * @param resource
	 * @return
	 */
	public boolean addResourceToUser(String userEmail, Resource resource){
		AppUser user=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean added=false;
		Transaction tx = pm.currentTransaction();
		try{
			tx.begin();
			user=pm.getObjectById(AppUser.class, userEmail);
			resource.setOwner(user);
			pm.makePersistent(resource);
			added=user.addResource(resource);
			tx.commit();
		}finally{
			if(tx.isActive()){
				tx.rollback();
			}
			pm.close();
		}
		return added;
	}
	
	public boolean addRequestToUser(String userEmail, Request request){
		AppUser user=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean added=false;
		Transaction tx = pm.currentTransaction();
		try{
			tx.begin();
			user=pm.getObjectById(AppUser.class, userEmail);
			request.setRequester(user);
			pm.makePersistent(request);
			added=user.addRequest(request);
			tx.commit();
		}finally{
			if(tx.isActive()){
				tx.rollback();
			}
			pm.close();
		}
		return added;
	}
	
	@SuppressWarnings("unchecked")
	public List<Request>getUserReceivedRequests(String email){
		List<Request> requests= new ArrayList<Request>();
		AppUser user=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		try{
			user=pm.getObjectById(AppUser.class, email);
			ArrayList<Resource>listRes=user.getResourceList();
			pm.retrieveAll(listRes);
			
			//take all the requests of the resources
			for(Resource resource:listRes){
				String queryStr = "select from " + Request.class.getName() + 
				          " where resource == :p1";
				Query query = pm.newQuery(queryStr);
				List<Request> requestsFromResource= new ArrayList<Request>();
				try{
					requestsFromResource=(List<Request>)query.execute(resource.getKey());
					for(Request request:requestsFromResource){
						requests.add(request);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}catch(Exception e){
			System.out.println("Can't get the User. "+e.getMessage());
			e.printStackTrace();
			
		}finally{
			pm.close();
		}
		
		
		return requests;
	}

}
