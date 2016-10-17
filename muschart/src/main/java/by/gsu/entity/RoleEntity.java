package by.gsu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class RoleEntity extends AbstractEntity implements GrantedAuthority {

    private static final long serialVersionUID = -2838272686668080339L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String            name;

    @JsonIgnore
    @ManyToMany(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false))
    private List<UserEntity>  users;

    public RoleEntity() {
        super();
    }

    public RoleEntity(final String name) {
        super();
        this.name = name;
    }

    public RoleEntity(final String name, final List<UserEntity> users) {
        super();
        this.name = name;
        this.users = users;
    }

    public RoleEntity(final long id, final String name, final List<UserEntity> users) {
        super(id);
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + "]";
    }

}
