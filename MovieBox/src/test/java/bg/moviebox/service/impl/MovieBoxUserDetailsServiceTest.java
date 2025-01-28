package bg.moviebox.service.impl;

import bg.moviebox.model.entities.User;
import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieBoxUserDetailsServiceTest {

  private static final String TEST_EMAIL = "user@example.com";
  private static final String NOT_EXISTENT_EMAIL = "empty@example.com";
  private MovieBoxUserDetailsService toTest;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void setUp() {
    toTest = new MovieBoxUserDetailsService(mockUserRepository);
  }

  @Test
  void testLoadUserByUsername_UserFound() {
    // Arrange
    User testUser = new User()
        .setEmail(TEST_EMAIL)
        .setPassword("secret")
        .setFirstName("Elon")
        .setLastName("Musk")
        .setRoles(List.of(
            new UserRoleEntity().setRoles(UserRoleEnum.USER),
            new UserRoleEntity().setRoles(UserRoleEnum.ADMIN)
        ));

    when(mockUserRepository.findByEmail(TEST_EMAIL))
        .thenReturn(Optional.of(testUser));

    // Act
    UserDetails userDetails = toTest.loadUserByUsername(TEST_EMAIL);

    // Assert
    Assertions.assertInstanceOf(MovieBoxUserDetails.class, userDetails);

    MovieBoxUserDetails movieBoxUserDetails = (MovieBoxUserDetails) userDetails;

    Assertions.assertEquals(TEST_EMAIL, userDetails.getUsername());
    Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
    Assertions.assertEquals(testUser.getFirstName(), movieBoxUserDetails.getFirstName());
    Assertions.assertEquals(testUser.getLastName(), movieBoxUserDetails.getLastName());
    Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(),
        movieBoxUserDetails.getFullName());

    var expectedRoles = testUser.getRoles().stream().map(UserRoleEntity::getRoles).map(r -> "ROLE_" + r).collect(Collectors.toSet());
    var actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

    Assertions.assertEquals(expectedRoles, actualRoles);
  }

  @Test
  void testLoadUserByUsername_UserNotFound() {
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> toTest.loadUserByUsername(NOT_EXISTENT_EMAIL)
    );
  }
}
