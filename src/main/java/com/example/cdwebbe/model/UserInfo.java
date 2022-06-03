package com.example.cdwebbe.model;

import java.util.HashSet;
import java.util.Set;

public class UserInfo {
    private Long id;
    private String username;
    private String name;

    private Set<Role> roles = new HashSet<>();

    public UserInfo(Long id, String username, String name, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
