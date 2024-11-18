package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.entities.Production;
import bg.moviebox.model.entities.User;
import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.UserRepository;
import bg.moviebox.repository.UserRoleRepository;
import bg.moviebox.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        boolean isFirstUser = userRepository.count() == 0;
        User user = map(userRegistrationDTO);

        List<UserRoleEntity> allRoles = userRoleRepository.findAll();
        UserRoleEntity adminRole = allRoles.stream()
                .filter(role -> role.getRoles() == UserRoleEnum.ADMIN)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        UserRoleEntity userRole = allRoles.stream()
                .filter(role -> role.getRoles() == UserRoleEnum.USER)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User role not found"));

        if (isFirstUser) {
            user.setRoles(List.of(adminRole, userRole));
        } else {
            user.setRoles(List.of(userRole)); // Assign only USER role
        }

        userRepository.save(user);
    }

    @Override
    public Optional<MovieBoxUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof MovieBoxUserDetails movieBoxUserDetails) {
            return Optional.of(movieBoxUserDetails);
        }
        return Optional.empty();
    }

    @Override
    public Set<Production> getUserPlaylist(MovieBoxUserDetails movieBoxUserDetails) {
        User user = userRepository.findById(movieBoxUserDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPlaylist();
    }

    private User map(UserRegistrationDTO userRegistrationDTO) {
        User mappedUser = modelMapper.map(userRegistrationDTO, User.class);
        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        return mappedUser;
    }
}
