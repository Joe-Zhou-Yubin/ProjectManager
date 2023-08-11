package com.projmanager.manager.models;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "proj_comp",
        joinColumns = @JoinColumn(name = "comp_compId"),
        inverseJoinColumns = @JoinColumn(name = "proj_projNumber"))
    private Set<Project> projects = new HashSet<>();
    
    private String projNumber;
    private String compNumber;
    private String compTitle;
    private String compDetail;

    public Component() {
        this.compNumber = generateUnique8CharString();
    }

    public Component(String compNumber, String compTitle, String compDetail) {
        this.compNumber = generateUnique8CharString();
        this.compTitle = compTitle;
        this.compDetail = compDetail;
    }

    private String generateUnique8CharString() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "").substring(0, 8);
        return uuidStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getProjNumber() {
		return projNumber;
	}

	public void setProjNumber(String projNumber) {
		this.projNumber = projNumber;
	}

	public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getCompNumber() {
        return compNumber;
    }

    public void setCompNumber(String compNumber) {
        this.compNumber = compNumber;
    }

    public String getCompTitle() {
        return compTitle;
    }

    public void setCompTitle(String compTitle) {
        this.compTitle = compTitle;
    }

    public String getCompDetail() {
        return compDetail;
    }

    public void setCompDetail(String compDetail) {
        this.compDetail = compDetail;
    }
}
