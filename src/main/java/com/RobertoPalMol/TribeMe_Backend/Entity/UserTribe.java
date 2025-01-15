package com.RobertoPalMol.TribeMe_Backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_tribe")
public class UserTribe {
    @EmbeddedId
    private UserTribeId id;

    @Column(name = "joined_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Embeddable
    public static class UserTribeId implements java.io.Serializable {
        private Long userId;
        private Long tribeId;

        // Getters, Setters y Overrides de equals/hashCode
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getTribeId() {
            return tribeId;
        }

        public void setTribeId(Long tribeId) {
            this.tribeId = tribeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserTribeId that = (UserTribeId) o;
            return userId.equals(that.userId) && tribeId.equals(that.tribeId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, tribeId);
        }
    }

    // Getters y Setters
    public UserTribeId getId() {
        return id;
    }

    public void setId(UserTribeId id) {
        this.id = id;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
