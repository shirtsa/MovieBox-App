package bg.moviebox.repository;

import bg.moviebox.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    Optional<User> findByUUID(UUID uuid); //to check if it's working properly
}
