package com.my.network.auth.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the users database table.
 *
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String userId;

	private Timestamp createdOn;

	private boolean isApproved;

	private boolean isDeleted;

	private boolean isLocked;

	private Timestamp lastUpdatedOn;

	private String name;

	@Column(name = "userAccessToken")
	private String accessToken;

	@Column(name = "userEmail")
	private String email;

	private String userName;

	@Column(name = "userPassword")
	private String password;

	@Column(name = "userPhoneNumber")
	private String contactNumber;

	@Column(name = "userRefreshToken")
	private String refreshToken;

	private String userSalt;

	private String userWebsite;

	@Column(name = "gcmToken")
	private String gcmToken;

	@Transient
	private String id;

	// bi-directional many-to-one association to UserAddress
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UserAddress> userAddresses;


	// bi-directional many-to-one association to UsersTypes
	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UsersTypes> usersTypes;

	//TODO add relation for Profiles.

	public Users() {
	}



	public Users(String id) {
		this.userId = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public boolean getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean getIsLocked() {
		return this.isLocked;
	}

	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Timestamp getLastUpdatedOn() {
		return this.lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUserSalt() {
		return this.userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public String getUserWebsite() {
		return this.userWebsite;
	}

	public void setUserWebsite(String userWebsite) {
		this.userWebsite = userWebsite;
	}

	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}

	public String getGcmToken() {
		return gcmToken;
	}

	public List<UserAddress> getUserAddresses() {
		return this.userAddresses;
	}

	public void setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public UserAddress addUserAddress(UserAddress userAddress) {
		getUserAddresses().add(userAddress);
		userAddress.setUser(this);

		return userAddress;
	}

	public UserAddress removeUserAddress(UserAddress userAddress) {
		getUserAddresses().remove(userAddress);
		userAddress.setUser(null);

		return userAddress;
	}

	public List<UsersTypes> getUsersTypes() {
		return this.usersTypes;
	}

	public void setUsersTypes(List<UsersTypes> usersTypes) {
		this.usersTypes = usersTypes;
	}

	public UsersTypes addUsersType(UsersTypes usersType) {
		getUsersTypes().add(usersType);
		usersType.setUser(this);

		return usersType;
	}

	public UsersTypes removeUsersType(UsersTypes usersType) {
		getUsersTypes().remove(usersType);
		usersType.setUser(null);

		return usersType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}