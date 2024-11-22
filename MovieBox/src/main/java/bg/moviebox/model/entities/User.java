package bg.moviebox.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

import static org.hibernate.type.SqlTypes.VARCHAR;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @UuidGenerator
    @JdbcTypeCode(VARCHAR)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    @Email
    @NotEmpty
    private String email;

    private String password;

    @Column(nullable = false)
    @Size(min = 2, max = 20)
    @NotEmpty
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2, max = 20)
    @NotEmpty
    private String lastName;

//    @Column
//    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRoleEntity> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_playlist",
            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "production_id"))
            inverseJoinColumns = @JoinColumn(name = "external_id", referencedColumnName = "external_id"))
    private Set<Production> playlist;

    public User() {
        this.roles = new ArrayList<>();
        this.playlist = new HashSet<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public User setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public User setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Production> getPlaylist() {
        return playlist;
    }

    public User setPlaylist(Set<Production> playlist) {
        this.playlist = playlist;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", email='" + email + '\'' +
                ", password='" + password + (password == null ? "N/A" : "[PROVIDED]") +'\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                ", playlist=" + playlist +
                '}';
    }
}
