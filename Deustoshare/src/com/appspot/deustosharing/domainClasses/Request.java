package com.appspot.deustosharing.domainClasses;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * Clase para la gestion de las peticiones de los usuarios sobre determinados recursos.
 * @author Julen
 *
 */
@PersistenceCapable
public class Request {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent (dependent = "true")
	private AppUser requester;
	@Persistent
	private Key resource;
	@Persistent
	private Date startDate;
	@Persistent
	private Date endDate;
	
	public Request(AppUser requester, Key resource, Date start, Date end) {
		this.requester=requester;
		this.resource=resource;
		this.startDate=start;
		this.endDate=end;
	}
	
	public void setKey(Key key) {
        this.key = key;
    }

	public AppUser getRequester() {
		return requester;
	}

	public void setRequester(AppUser requester) {
		this.requester = requester;
	}

	public Key getResource() {
		return resource;
	}

	public void setResource(Key resource) {
		this.resource = resource;
	}

	public Key getKey() {
		return key;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

}
