package com.muschart.bean.entity;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import com.muschart.constants.RoleConstants;
import com.muschart.entity.UserEntity;

public class UserInfoEntity implements Serializable {

    private static final long serialVersionUID = -2797819737600363424L;

    private String            login;
    private boolean           isAdmin;

    public UserInfoEntity() {
    }

    public UserInfoEntity(final String login, final boolean isAdmin) {
        this.login = login;
        this.isAdmin = isAdmin;
    }

    public UserInfoEntity(final UserEntity user) {
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

    public void setLogin(final String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(final boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserInfoEntity other = (UserInfoEntity) obj;
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
