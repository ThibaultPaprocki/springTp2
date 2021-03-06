package fr.paris8univ.iut.csid.csidwebrepositorybase.core.dao;

import javax.persistence.*;

@Entity
@Table(name = "repository")
public class GitRepositoryEntity {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "owner")
    private String owner;

    @Column(name = "open_issues")
    private Integer open_issues;

    @Column(name = "forks")
    private Integer forks;

    public GitRepositoryEntity() {
    }

    public GitRepositoryEntity(String name, String owner, Integer open_issues, Integer forks) {
        this.name = name;
        this.owner = owner;
        this.open_issues = open_issues;
        this.forks = forks;
    }

    public Integer getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(Integer open_issues) {
        this.open_issues = open_issues;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
