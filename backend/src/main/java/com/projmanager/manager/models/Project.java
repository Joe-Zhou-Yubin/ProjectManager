package com.projmanager.manager.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projNumber;
    private String projTitle;
    private String projDetail;
    private boolean projStatus = false;

    public Project() {
        this.projNumber = generateUnique8DigitString();
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

    public String getProjTitle() {
        return projTitle;
    }

    public void setProjTitle(String projTitle) {
        this.projTitle = projTitle;
    }

    public String getProjDetail() {
        return projDetail;
    }

    public void setProjDetail(String projDetail) {
        this.projDetail = projDetail;
    }

    public boolean isProjStatus() {
        return projStatus;
    }

    public void setProjStatus(boolean projStatus) {
        this.projStatus = projStatus;
    }

    // Generate a unique 8-digit alphanumeric string for projNumber using UUID
    private String generateUnique8DigitString() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "").substring(0, 8);
        return uuidStr;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", projNumber=" + projNumber + ", projTitle=" + projTitle + ", projDetail="
                + projDetail + ", projStatus=" + projStatus + "]";
    }
}
