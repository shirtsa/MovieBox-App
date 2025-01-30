package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.entities.User;
import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.repository.UserRepository;
import bg.moviebox.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl toTest;

  @Captor
  private ArgumentCaptor<User> userEntityCaptor;

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private UserRoleRepository mockUserRoleRepository;

  @Mock
  private PasswordEncoder mockPasswordEncoder;

  @BeforeEach
  void setUp() {
    toTest = new UserServiceImpl(
            mockUserRepository,
            mockUserRoleRepository,
            mockPasswordEncoder,
            new ModelMapper()
        );
  }

  @ParameterizedTest
  @CsvSource({
          "Elon, Musk, secret, musk@example.com, 0, 2",
          "Jeff, Bezos, mySecret, bezos@example.com, 1, 1"
          // If userCount == 0, it means this is the first user in the system, so they should be assigned both ADMIN and USER roles.
          // If userCount > 0, it means there are already users in the system, so the new user gets only the USER role.
  })
  void testUserRegistration(String firstName,
                            String lastName,
                            String password,
                            String email,
                            long userCount,
                            int expectedRoles) {
    // Arrange
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO()
            .setFirstName(firstName)
            .setLastName(lastName)
            .setPassword(password)
            .setEmail(email);

    UserRoleEntity adminRoleEntity = new UserRoleEntity().setRoles(UserRoleEnum.ADMIN);
    UserRoleEntity userRoleEntity = new UserRoleEntity().setRoles(UserRoleEnum.USER);

    when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
            .thenReturn(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword());
    when(mockUserRepository.count()).thenReturn(userCount);

    List<UserRoleEntity> allRoles = List.of(adminRoleEntity, userRoleEntity);
    when(mockUserRoleRepository.findAll()).thenReturn(allRoles);

    // Act
    toTest.registerUser(userRegistrationDTO);
    /* When calling registerUser DTO is passed, but there is no direct access
    to how it is transformed into an entity inside the method. */

    // Assert
    verify(mockUserRepository).save(userEntityCaptor.capture());
    /* With ArgumentCaptor, you can capture the exact entity that was passed to the repository
    and verify that all fields were mapped correctly from the DTO. */

    User actualSavedEntity = userEntityCaptor.getValue();

    assertNotNull(actualSavedEntity);
    assertEquals(userRegistrationDTO.getFirstName(), actualSavedEntity.getFirstName());
    assertEquals(userRegistrationDTO.getLastName(), actualSavedEntity.getLastName());
    assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword(),
            actualSavedEntity.getPassword());
    assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());

    // Check roles assignment
    assertEquals(expectedRoles, actualSavedEntity.getRoles().size());
    if (userCount == 0) {
      assertTrue(actualSavedEntity.getRoles().stream()
              .anyMatch(role -> role.getRoles() == UserRoleEnum.ADMIN));
    }
    assertTrue(actualSavedEntity.getRoles().stream()
            .anyMatch(role -> role.getRoles() == UserRoleEnum.USER));
  }
}
