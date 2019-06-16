package com.edu.nju.joshua.wedesign.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
public class User {
    public Double avatar_x;
    public Double avatar_y;

    public Integer getUserIsOnline() {
        return userIsOnline;
    }

    public void setUserIsOnline(Integer userIsOnline) {
        this.userIsOnline = userIsOnline;
    }

    public Integer userIsOnline;

    public Double getAvatar_x() {
        return avatar_x;
    }

    public void setAvatar_x(Double avatar_x) {
        this.avatar_x = avatar_x;
    }

    public Double getAvatar_y() {
        return avatar_y;
    }

    public void setAvatar_y(Double avatar_y) {
        this.avatar_y = avatar_y;
    }

    public Double getAvatar_w() {
        return avatar_w;
    }

    public void setAvatar_w(Double avatar_w) {
        this.avatar_w = avatar_w;
    }

    public Double getAvatar_h() {
        return avatar_h;
    }

    public void setAvatar_h(Double avatar_h) {
        this.avatar_h = avatar_h;
    }

    public Double avatar_w;
    public Double avatar_h;
    public String getBoardMessage() {
        return boardMessage;
    }

    public void setBoardMessage(String boardMessage) {
        this.boardMessage = boardMessage;
    }

    public String boardMessage;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String Description;

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    private String confirmationToken;
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String username;
    private String password;
    private String name;
    private Integer age;
    private Integer gender;
    private String avatar;
    private String email;
}

