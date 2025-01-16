package com.RobertoPalMol.TribeMe_Backend.Entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tribe_members")
public class TribeMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_member")
    private Long idMember;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_tribe", nullable = false)
    private Tribe tribe;

    @Enumerated(EnumType.STRING)
    private Role roll = Role.MIEMBRO;

    @Column(name = "union_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime unionDate;

    public enum Role {
        ADMIN, MIEMBRO
    }

    // Getters and Setters

    public Long getIdMember() {
        return idMember;
    }

    public void setIdMember(Long idMember) {
        this.idMember = idMember;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tribe getTribe() {
        return tribe;
    }

    public void setTribe(Tribe tribe) {
        this.tribe = tribe;
    }

    public Role getRoll() {
        return roll;
    }

    public void setRoll(Role roll) {
        this.roll = roll;
    }

    public LocalDateTime getUnionDate() {
        return unionDate;
    }

    public void setUnionDate(LocalDateTime unionDate) {
        this.unionDate = unionDate;
    }
}

