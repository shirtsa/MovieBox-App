package bg.moviebox.service.impl;

import bg.moviebox.model.entities.Production;
import bg.moviebox.model.entities.User;
import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.ProductionRepository;
import bg.moviebox.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductionServiceImplTest {

    @Mock
    private ProductionRepository mockProductionRepository;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private ProductionServiceImpl toTest;

    private static final String TEST_EMAIL = "user@example.com";

    @Test
    void removeFromPlaylist_ShouldRemoveProductionFromUserPlaylist() {
        // Arrange
        Long userId = 1L;
        UUID userUuid = UUID.randomUUID();
        Long productionId = 1L;
        User user = new User()
                .setEmail(TEST_EMAIL)
                .setPassword("secret")
                .setFirstName("Cristiano")
                .setLastName("Ronaldo")
                .setRoles(List.of(
                        new UserRoleEntity().setRoles(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setRoles(UserRoleEnum.USER)
                ));
        Production production = new Production();
        user.getPlaylist().add(production);

        MovieBoxUserDetails userDetails = new MovieBoxUserDetails(
                userId,
                userUuid,
                TEST_EMAIL,
                "secret",
                Collections.emptyList(),  // Authorities
                "Cristiano",
                "Ronaldo"
        );

        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mockProductionRepository.findProductionByExternalId(productionId))
                .thenReturn(production);

        // Act
        toTest.removeFromPlaylist(productionId, userDetails);

        // Assert
        verify(mockUserRepository).save(user);
        assertFalse(user.getPlaylist().contains(production));
    }
}
