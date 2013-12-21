package com.appspot.deustosharing.domainClasses;

import java.util.ArrayList;

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
	@Persistent
	private AppUser owner;
	@Persistent
	private AppUser currentUser;
	@Persistent
	private Type type;
	@Persistent
	private boolean visible;
	@Persistent(mappedBy = "resource")
	private ArrayList<Request> requestList;

	public Resource(String title, String description, AppUser owner, Type type) {
		this.title = title;
		this.description = description;
		this.owner = owner;
		this.currentUser = null;
		this.requestList = new ArrayList<Request>();
		this.type = type;
		this.visible=true;
	}

	public Resource(String title, String description, AppUser owner,
			AppUser currentUser, ArrayList<Request> requestList, Type type, boolean visible) {
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

	public boolean insertRequest(Request request) {
		if (this.requestList == null) {
			requestList = new ArrayList<Request>();
		}
		return requestList.add(request);
	}

	public ArrayList<Request> getRequestList() {
		if (this.requestList == null) {
			this.requestList = new ArrayList<Request>();
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

	public void setRequestList(ArrayList<Request> requestList) {
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
	

}
