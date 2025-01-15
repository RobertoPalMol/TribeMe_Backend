package com.RobertoPalMol.TribeMe_Backend.Entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tribe")
public class Tribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer members;

    private String photo;

    private Boolean isPrivate;

    @Column(name = "private_event")
    private Boolean privateEvent;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    // Getters, Setters y Overrides de equals/hashCode
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getPrivateEvent() {
        return privateEvent;
    }

    public void setPrivateEvent(Boolean privateEvent) {
        this.privateEvent = privateEvent;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tribe tribe = (Tribe) o;
        return id.equals(tribe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
