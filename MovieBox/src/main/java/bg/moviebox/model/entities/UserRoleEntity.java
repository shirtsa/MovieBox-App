package bg.moviebox.model.entities;

import bg.moviebox.model.enums.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum roles;

    public UserRoleEnum getRoles() {
        return roles;
    }

    public UserRoleEntity setRoles(UserRoleEnum userRole) {
        this.roles = userRole;
        return this;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "roles=" + roles +
                '}';
    }
}
