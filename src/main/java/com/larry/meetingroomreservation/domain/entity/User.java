package com.larry.meetingroomreservation.domain.entity;

import com.larry.meetingroomreservation.domain.entity.support.AbstractEntity;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity
public class User extends AbstractEntity{

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    // 패스워드 커스텀 어노테이션 만들어보기
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    public User() {

    }

    @Builder
    public User(String userId, String password, String name, String email, RoleName roleName) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roleName = roleName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                roleName == user.roleName;
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password, name, email, roleName);
    }
}
