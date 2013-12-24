package com.appspot.deustosharing.dao;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.appspot.deustosharing.domainClasses.AppUser;
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
	public boolean insert(AppUser user){
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean saved=false;
		try{
			pm.makePersistent(user);
			saved=true;
		}catch(Exception e){
			System.out.println("Can't save the User. "+e.getMessage());
		}
		finally{
			pm.close();
		}
		return saved;
	}
	public AppUser GetByPrimaryKey(String email){
		AppUser user=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		try{
			user=pm.getObjectById(AppUser.class, email);
			user.getRequestList();
			ArrayList<Resource>listRes=user.getResourceList();
			pm.retrieveAll(listRes);
			
//			for(Resource res:listRes){
//				String tit=res.getTitle();
//				tit.length();
//			}
			
		}catch(Exception e){
			System.out.println("Can't get the User. "+e.getMessage());
			
		}finally{
			pm.close();
		}
		return user;
	}
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
	public boolean addResourceToUser(String userEmail, Resource resource){
		AppUser user=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean added=false;
		try{
			user=pm.getObjectById(AppUser.class, userEmail);
			resource.setOwner(user);
			pm.makePersistent(resource);
			added=user.addResource(resource);
		}finally{
			pm.close();
		}
		return added;
	}

}
