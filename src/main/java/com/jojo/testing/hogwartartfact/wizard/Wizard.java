package com.jojo.testing.hogwartartfact.wizard;

import com.jojo.testing.hogwartartfact.artifacts.Artifact;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wizard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "owner")
    private List<Artifact> artifacts = new ArrayList<>();

    public Wizard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.artifacts.add(artifact);

    }

    public Integer getNumberOfArtifacts() {
        return this.artifacts != null ? this.artifacts.size() : 0;
    }
}
