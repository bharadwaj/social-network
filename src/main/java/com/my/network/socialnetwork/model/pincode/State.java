package com.my.network.socialnetwork.model.pincode;

import javax.persistence.*;
import java.util.List;

@Entity(name = "md_State")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable=false)
    private String name;

    @OneToMany(mappedBy = "state")
    private List<District> districts;

    public State(String name) {
        this.name = name;
    }

    public State() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
