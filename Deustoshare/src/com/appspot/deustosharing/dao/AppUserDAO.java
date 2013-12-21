package com.appspot.deustosharing.dao;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.appspot.deustosharing.domainClasses.AppUser;

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

}