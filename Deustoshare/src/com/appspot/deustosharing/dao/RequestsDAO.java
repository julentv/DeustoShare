package com.appspot.deustosharing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.appspot.deustosharing.domainClasses.AppUser;
import com.appspot.deustosharing.domainClasses.Request;
import com.appspot.deustosharing.domainClasses.RequestState;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RequestsDAO {
	private PersistenceManagerFactory pmf;
	public RequestsDAO(){
		this.pmf= PMF.get();
	}
	
	public Request getByPrimaryKey(Long keyLong, String requesterEmail){
		Request request=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		
		try{
			Key parentKey=KeyFactory.createKey(AppUser.class.getSimpleName(), requesterEmail);
			Key key=KeyFactory.createKey(parentKey,Request.class.getSimpleName(),keyLong);
			request=pm.getObjectById(Request.class, key);
			request.getRequester();
		}catch(Exception e){
			System.out.println("Can't load the Request. "+e.getMessage());
			e.printStackTrace();
		}finally{
			pm.close();
		}
		return request;
	}
	
	public void delete(Long keyLong, String requesterEmail){
		PersistenceManager pm=this.pmf.getPersistenceManager();
		Request request=null;
		try{
			Key parentKey=KeyFactory.createKey(AppUser.class.getSimpleName(), requesterEmail);
			Key key=KeyFactory.createKey(parentKey,Request.class.getSimpleName(),keyLong);
			request=pm.getObjectById(Request.class, key);
			pm.deletePersistent(request);
		}catch(Exception e){
			System.out.println("Can't delete the Request. "+e.getMessage());
			e.printStackTrace();
		}finally{
			pm.close();
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public List<Request> getByKeyResource(Key resourceKey){
		List<Request> resourceList= new ArrayList<Request>();
		PersistenceManager pm=this.pmf.getPersistenceManager();
		String queryStr = "select from " + Request.class.getName() + 
		          " where resource == :p1";
		Query query = pm.newQuery(queryStr);
		try{
		    resourceList=(List<Request>)query.execute(resourceKey);
		}catch(Exception e){
			System.out.println("Can't delete the Request. "+e.getMessage());
			e.printStackTrace();
		}finally{
			pm.close();
		}
		return resourceList;
	}
	public void updateRequestState(Long keyLong, String requesterEmail, RequestState newState){
		Request request=null;
		PersistenceManager pm=this.pmf.getPersistenceManager();
		
		try{
			Key parentKey=KeyFactory.createKey(AppUser.class.getSimpleName(), requesterEmail);
			Key key=KeyFactory.createKey(parentKey,Request.class.getSimpleName(),keyLong);
			request=pm.getObjectById(Request.class, key);
			request.setState(newState);
		}catch(Exception e){
			System.out.println("Can't load the Request. "+e.getMessage());
			e.printStackTrace();
		}finally{
			pm.close();
		}
	}

}
