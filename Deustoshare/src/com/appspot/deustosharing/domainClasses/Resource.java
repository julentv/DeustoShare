package com.appspot.deustosharing.domainClasses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * Clase para los recursos que se comparten mediante la aplicacion. Cada uno de
 * ellos tiene un propietario, ademas de otros datos.
 * 
 * @author Julen
 * 
 */
@PersistenceCapable
public class Resource {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private String title;
	@Persistent
	private String description;
	@Persistent (dependent = "true")
	private AppUser owner;
	@Persistent
	private AppUser currentUser;
	@Persistent
	private Type type;
	@Persistent
	private boolean visible;
	@Persistent
	private ArrayList<Key> requestList;

	public Resource(String title, String description, AppUser owner, Type type) {
		this.title = title;
		this.description = description;
		this.owner = owner;
		this.currentUser = null;
		this.requestList = new ArrayList<Key>();
		this.type = type;
		this.visible=true;
	}

	public Resource(String title, String description, AppUser owner,
			AppUser currentUser, ArrayList<Key> requestList, Type type, boolean visible) {
		super();
		this.title = title;
		this.description = description;
		this.owner = owner;
		this.currentUser = currentUser;
		this.requestList = requestList;
		this.type = type;
		this.visible=visible;
	}
	
	public void setKey(Key key) {
        this.key = key;
    }
	public Key getKey() {
		return key;
	}

	public boolean insertRequest(Key request) {
		if (this.requestList == null) {
			requestList = new ArrayList<Key>();
		}
		return requestList.add(request);
	}

	public ArrayList<Key> getRequestList() {
		if (this.requestList == null) {
			this.requestList = new ArrayList<Key>();
		}
		return this.requestList;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public void setCurrentUser(AppUser currentUser) {
		this.currentUser = currentUser;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setRequestList(ArrayList<Key> requestList) {
		this.requestList = requestList;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public AppUser getOwner() {
		return owner;
	}

	public AppUser getCurrentUser() {
		return currentUser;
	}

	public Type getType() {
		return type;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	/**
	 * Calculates whether the resource is been used or not.
	 * @param requestList list of the requests made to the resource.
	 * @return true if is available
	 */
	public boolean isAvailable(List<Request>requestList){
		boolean available=true;
		Date currentDate= new Date();
		for(int i=0,ii=requestList.size();i<ii&&available;i++){
			if(requestList.get(i).getStartDate().compareTo(currentDate)<0&&requestList.get(i).getEndDate().compareTo(currentDate)>0){
				available=false;
			}
		}
		
		return available;
	}

}
