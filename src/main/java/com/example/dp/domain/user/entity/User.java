package com.example.dp.domain.user.entity;

import com.example.dp.domain.model.TimeEntity;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_USER")
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String introduceMessage;

    @Column
    private String imageName;

    @Column
    private String imagePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    private User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.introduceMessage = "";
        this.status = UserStatus.ACTIVE;
    }

    public void updateUsername(final String username) {
        this.username = username;
    }

    public void updateIntroduceMessage(final String introduceMessage) {
        this.introduceMessage = introduceMessage;
    }

    public void updatePassword(final String password) {
        this.password = password;
    }

    public void updateImage(final String imageName, final String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public void updateUserRole(UserRole role) {
        this.role = role;
    }

    public void updateUserStatus(UserStatus status) {
        this.status = status;
    }
}
