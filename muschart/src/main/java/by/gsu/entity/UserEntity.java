package by.gsu.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserEntity extends AbstractEntity implements UserDetails {

    private static final long      serialVersionUID = 7372820574885171442L;

    @JsonIgnore
    private boolean                accountNonExpired;

    @JsonIgnore
    private boolean                accountNonLocked;

    @JsonIgnore
    private boolean                credentialsNonExpired;

    @JsonIgnore
    private boolean                enabled;

    @Column(name = "login", unique = true, nullable = false, length = 255)
    private String                 login;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String                 password;

    @JsonIgnore
    @ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
    private List<GrantedAuthority> roles;

    @JsonIgnore
    @ManyToMany(targetEntity = TrackEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_track", joinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private List<TrackEntity>      tracks;

    @JsonIgnore
    @ManyToMany(targetEntity = ArtistEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_artist", joinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false))
    private List<ArtistEntity>     artists;

    @JsonIgnore
    @ManyToMany(targetEntity = GenreEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_genre", joinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false))
    private List<GenreEntity>      genres;

    public UserEntity() {
        super();
    }

    public UserEntity(final String login, final String password,
            final List<GrantedAuthority> roles) {
        this(true, true, true, true, login, password, roles);
    }

    public UserEntity(final boolean accountNonExpired, final boolean accountNonLocked,
            final boolean credentialsNonExpired, final boolean enabled, final String login,
            final String password, final List<GrantedAuthority> roles) {
        super();
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(final List<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public List<TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(final List<TrackEntity> tracks) {
        this.tracks = tracks;
    }

    public List<ArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(final List<ArtistEntity> artists) {
        this.artists = artists;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(final List<GenreEntity> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", login=" + login + "]";
    }

}
