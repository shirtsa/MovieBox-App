package bg.moviebox.service.impl;

import bg.moviebox.model.entities.User;
import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MovieBoxUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public MovieBoxUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {

    return userRepository
        .findByEmail(email)
        .map(MovieBoxUserDetailsService::map)
        .orElseThrow(
            () -> new UsernameNotFoundException("User with email " + email + " not found!"));
  }

  private static UserDetails map(User user) {
    return new MovieBoxUserDetails(
        user.getId(),
        user.getUuid(),
        user.getEmail(),
        user.getPassword(),
        user.getRoles().stream().map(UserRoleEntity::getRoles).map(MovieBoxUserDetailsService::map).toList(),
        user.getFirstName(),
        user.getLastName()
    );
  }

  private static GrantedAuthority map(UserRoleEnum role) {
    return new SimpleGrantedAuthority(
        "ROLE_" + role
    );
  }
}
