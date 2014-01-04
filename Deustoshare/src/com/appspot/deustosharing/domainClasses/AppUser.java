package com.appspot.deustosharing.domainClasses;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Clase de usuarios de la aplicacion
 * @author Julen
 *
 */
@PersistenceCapable
public class AppUser {
	@PrimaryKey
	private String email;
	@Persistent
	private String name;
	@Persistent(mappedBy = "owner")
	private ArrayList<Resource> resourceList;
	@Persistent(mappedBy = "requester")
	private ArrayList<Request> requestList;

	public AppUser(String email) {
		this.email=email;
		this.name="";
		this.resourceList=new ArrayList<Resource>();
		this.requestList= new ArrayList<Request>();

	}

	public boolean addResource(Resource resource){
		if(this.resourceList==null){
			this.resourceList=new ArrayList<Resource>();
		}
		return this.resourceList.add(resource);
	}
	public boolean addRequest(Request request){
		if(this.requestList==null){
			this.requestList=new ArrayList<Request>();
		}
		return this.requestList.add(request);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(ArrayList<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Request> getRequestList() {
		return requestList;
	}

	public void setRequestList(ArrayList<Request> requestList) {
		this.requestList = requestList;
	}
	public String toString(){
		String result="User name: "+this.name+", email: "+this.email;
		return result;
	}
	public ArrayList<Request> getRequestsByFilter(String filter){
		ArrayList<Request> requests= new ArrayList<Request>();
		RequestState filterState=null;
		try{
			filterState=RequestState.valueOf(filter);
		}catch(Exception e){
			//no filter to add

		}
		if(filterState==null){
			requests=this.requestList;
		}else{
			for(Request req:this.requestList){
				if(req.getState().equals(filterState)){
					requests.add(req);
				}
			}
		}
		
		
		return requests;
	}
}
