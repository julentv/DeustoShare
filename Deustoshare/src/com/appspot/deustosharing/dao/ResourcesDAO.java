package com.appspot.deustosharing.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.appspot.deustosharing.domainClasses.AppUser;
import com.appspot.deustosharing.domainClasses.Resource;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ResourcesDAO {
	private PersistenceManagerFactory pmf;
	public ResourcesDAO(){
		this.pmf= PMF.get();
	}
	public Resource getByPrimaryKey(Long keyLog, String userEmail){
		Resource resource=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		try{
			Key parentKey=KeyFactory.createKey(AppUser.class.getSimpleName(), userEmail);
			Key key=KeyFactory.createKey(parentKey,Resource.class.getSimpleName(),keyLog);
			resource=pm.getObjectById(Resource.class, key);
			resource.getCurrentUser();
		}catch(Exception e){
			System.out.println("Can't load the Resource. "+e.getMessage());
		}finally{
			pm.close();
		}
		
		return resource;		
	}
	public boolean updateByPrimaryKey(Long keyLong, String userEmail,Resource resourceChanges){
		Resource resource=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean updated=false;
		try{
			//obtain the resource
			Key parentKey=KeyFactory.createKey(AppUser.class.getSimpleName(), userEmail);
			Key key=KeyFactory.createKey(parentKey,Resource.class.getSimpleName(),keyLong);
			resource=pm.getObjectById(Resource.class, key);
			//edit the resource
			resource.setDescription(resourceChanges.getDescription());
			resource.setTitle(resourceChanges.getTitle());
			resource.setType(resourceChanges.getType());
			resource.setVisible(resourceChanges.isVisible());
			updated=true;
		}catch(Exception e){
			System.out.println("Can't update the resource. "+e.getMessage());
		}finally{
			pm.close();
		}
		return updated;
	}
	/**
	 * ONLY USED FOR TEST
	 * @return
	 */
	public List getAllResurces(){
		PersistenceManager pm=this.pmf.getPersistenceManager();
		Query q = pm.newQuery(Resource.class);
	    List ids = (List) q.execute();
	    for(Object o:ids){
	    	
	    	((Resource)o).getOwner();
	    	System.out.println("Key id: "+((Resource)o).getKey().getId());
	    }
	    q = pm.newQuery(AppUser.class);
	    ids = (List) q.execute();
		for (Object o : ids) {

			((AppUser) o).getResourceList();
			System.out.println("Key id: " + ((AppUser) o).getEmail());
		}
	    return ids;
	}

}
