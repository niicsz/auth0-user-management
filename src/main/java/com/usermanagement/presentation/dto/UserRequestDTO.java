package com.usermanagement.presentation.dto;

import java.util.HashSet;
import java.util.Set;

public class UserRequestDTO {
    private String email;
    private String name;
    private String pictureUrl;
    private Set<Long> roleIds;
    private boolean enabled;

    public UserRequestDTO() {
        this.roleIds = new HashSet<>();
        this.enabled = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
