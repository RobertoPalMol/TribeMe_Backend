package com.RobertoPalMol.TribeMe_Backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tribe")
public class Tribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tribe")
    private Long idTribe;

    private String name;

    private String description;

    @Column(name = "max_members")
    private Integer maxMembers;

    @Lob
    private byte[] photo;

    @Column(name = "private_tribe")
    private Boolean privateTribe = false;

    @Column(name = "private_event")
    private Boolean privateEvent = false;

    @ManyToOne
    @JoinColumn(name = "id_creator", nullable = false)
    private User creator;

    @Column(name = "creation_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

    // Getters and Setters

    public Long getIdTribe() {
        return idTribe;
    }

    public void setIdTribe(Long idTribe) {
        this.idTribe = idTribe;
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

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Boolean getPrivateTribe() {
        return privateTribe;
    }

    public void setPrivateTribe(Boolean privateTribe) {
        this.privateTribe = privateTribe;
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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
