package com.my.network.auth.model;

import com.my.network.auth.model.MstTypes;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "type_role")
@NamedQuery(name = "TypeRole.findAll", query = "SELECT t FROM TypeRole t")
public class TypeRole {

    @Id
    @Column(name="typeRoleId")
    private int typeRoleId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="typeId")
    private MstTypes typeId;

    @Column(name="createdOn")
    private Timestamp createdOn;

    @Column(name="lastUpdatedOn")
    private Timestamp lastUpdatedOn;

    public TypeRole() {
        super();
    }

    public TypeRole(int typeRoleId) {
        this.typeRoleId = typeRoleId;
    }

    public TypeRole(int typeRoleId, String name, String description, MstTypes typeId, Timestamp createdOn, Timestamp lastUpdatedOn) {
        this.typeRoleId = typeRoleId;
        this.name = name;
        this.description = description;
        this.typeId = typeId;
        this.createdOn = createdOn;
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public int getTypeRoleId() {
        return typeRoleId;
    }

    public void setTypeRoleId(int typeRoleId) {
        this.typeRoleId = typeRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MstTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(MstTypes typeId) {
        this.typeId = typeId;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
