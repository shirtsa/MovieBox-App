package bg.moviebox.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class MovieBoxUserDetails extends User {

    // MovieBoxUserDetails extends User who extends UserDetails
    //In every Controller we can inject this MovieBoxUserDetails and
    //do what we need with whatever is return from the UserDetailsService
    private final Long id;
    private final UUID uuid;
    private final String firstName;
    private final String lastName;

    public MovieBoxUserDetails(
            Long id,
            UUID uuid,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String firstName,
            String lastName
    ) {
        super(username, password, authorities);
        this.id = id;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) {
            fullName.append(firstName);
        }
        if (lastName != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }

        return fullName.toString();
    }
}
