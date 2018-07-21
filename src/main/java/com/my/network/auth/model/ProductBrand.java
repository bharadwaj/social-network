package com.my.network.auth.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the product_brand database table.
 * 
 */
@Entity
@Table(name = "product_brand")
@NamedQuery(name = "ProductBrand.findAll", query = "SELECT p FROM ProductBrand p")
public class ProductBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String brandId;

	@Column(name = "brandDesc")
	private String desc;
	// @Column(name = "brandCategory")
	@Transient
	private String category;
	@Column(name = "brandImg")
	private String image;
	@Column(name = "brandName")
	private String name;

	private Timestamp createdOn;
	@Column(name = "isActive")
	private boolean active;

	private Timestamp lastUpdatedOn;

	private short position;

	@Transient
	private String id;


	/*
	 * @OneToMany(mappedBy = "productBrand", cascade = CascadeType.ALL) private
	 * List<ServiceCenterBrandMap> serviceCenterBrandMaps;
	 */

	// bi-directional many-to-one association to Product

	public ProductBrand() {
	}



	public ProductBrand(String id) {
		this.brandId = id;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public Timestamp getLastUpdatedOn() {
		return this.lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	/*
	 * public List<ServiceCenterBrandMap> getServiceCenterBrandMaps() { return
	 * serviceCenterBrandMaps; }
	 * 
	 * public void setServiceCenterBrandMaps(List<ServiceCenterBrandMap>
	 * serviceCenterBrandMaps) { this.serviceCenterBrandMaps =
	 * serviceCenterBrandMaps; }
	 */

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getPosition() {
		return position;
	}

	public void setPosition(short position) {
		this.position = position;
	}

}