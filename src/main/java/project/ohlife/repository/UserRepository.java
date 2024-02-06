package project.ohlife.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.user.User;
import project.ohlife.repository.querydsl.SearchRepository;

public interface UserRepository extends JpaRepository<User, Long>, SearchRepository {

  User findByEmailAndPassword(String email, String password);

  boolean existsByEmail(String email);

  boolean existsByPassword(String password);

  boolean existsByEmailAndPassword(String email, String password);

  boolean existsByPhoneNumber(String phoneNumber);

  User findByEmail(String email);
}
