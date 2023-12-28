package project.ohlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
