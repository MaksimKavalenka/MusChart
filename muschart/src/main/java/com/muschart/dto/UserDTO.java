package com.muschart.dto;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import com.muschart.constants.RoleConstants;
import com.muschart.entity.UserEntity;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 5788306146372527542L;

    private String            login;
    private boolean           isAdmin;

    public UserDTO() {
    }

    public UserDTO(String login, boolean isAdmin) {
        this.login = login;
        this.isAdmin = isAdmin;
    }

    public UserDTO(UserEntity user) {
        login = user.getLogin();
        isAdmin = false;
        for (GrantedAuthority role : user.getRoles()) {
            if (RoleConstants.ROLE_ADMIN.name().equals(role.getAuthority())) {
                isAdmin = true;
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = (prime * result) + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserDTO other = (UserDTO) obj;
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[login:" + login + ",admin:" + isAdmin + "]";
    }

}