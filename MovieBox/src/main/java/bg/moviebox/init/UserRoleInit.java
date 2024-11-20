package bg.moviebox.init;

import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Component
public class UserRoleInit implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public UserRoleInit(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userRoleRepository.count() == 0) {
            List<UserRoleEntity> roles = new ArrayList<>();
            Arrays.stream(UserRoleEnum.values())
                    .forEach(userRole -> {
                        UserRoleEntity userRoleEntity = new UserRoleEntity();
                        userRoleEntity.setRoles(userRole);
                        roles.add(userRoleEntity);
                    });
            this.userRoleRepository.saveAll(roles);
        }
    }
}
