package com.projmanager.manager.models;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String detNumber;
    private String compNumber;

    private String detTitle;
    private String detDetail;

    @NotNull
    @Pattern(regexp = "High|Medium|Low")
    private String priority;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private boolean detStatus = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "comp_det",
        joinColumns = @JoinColumn(name = "det_id"),
        inverseJoinColumns = @JoinColumn(name = "comp_id"))
    private Set<Component> components = new HashSet<>();

    @NotNull
    private String username;

    public Detail() {
        this.detNumber = generateUnique8CharString();
    }

    public Long getId() {
        return id;
    }

    public String getDetNumber() {
        return detNumber;
    }

    public String getDetTitle() {
        return detTitle;
    }

    public void setDetTitle(String detTitle) {
        this.detTitle = detTitle;
    }

    public String getDetDetail() {
        return detDetail;
    }

    public void setDetDetail(String detDetail) {
        this.detDetail = detDetail;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDetStatus() {
        return detStatus;
    }

    public void setDetStatus(boolean detStatus) {
        this.detStatus = detStatus;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String generateUnique8CharString() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "").substring(0, 8);
        return uuidStr;
    }

	public String getCompNumber() {
		return compNumber;
	}

	public void setCompNumber(String compNumber) {
		this.compNumber = compNumber;
	}
}
