package com.my.network.auth.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the users_types database table.
 * 
 */
@Entity
@Table(name="users_types")
public class UsersTypes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private Timestamp createdOn;

	private Timestamp lastUpdatedOn;

	@ManyToOne
	@JoinColumn(name="typeId")
	private MstTypes mstType;

	//bi-directional many-to-one association to Users
	@ManyToOne
	@JoinColumn(name="userId")
	private Users user;

	public UsersTypes() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastUpdatedOn() {
		return this.lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public MstTypes getMstType() {
		return mstType;
	}

	public void setMstType(MstTypes mstType) {
		this.mstType = mstType;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}