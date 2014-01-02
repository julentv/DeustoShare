package com.appspot.deustosharing.dao;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import com.appspot.deustosharing.domainClasses.Request;

public class RequestsDAO {
	private PersistenceManagerFactory pmf;
	public RequestsDAO(){
		this.pmf= PMF.get();
	}
	/* NOT USED!!!!*/
	public boolean insertRequest(Request request){
		PersistenceManager pm=this.pmf.getPersistenceManager();
		boolean saved=false;
		try{
			pm.makePersistent(request);
			saved=true;
		}catch(Exception e){
			System.out.println("Can't save the Request. "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			pm.close();
		}
		return saved;
	}

}
