package phoenix.AM_PM.mainservice.domain.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import phoenix.AM_PM.mainservice.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  public Optional<User> findById(Integer id);
  public Optional<User> findByUserId(String user_id);

  public Optional<User> findByEmail(String email);
  List<User> findByNicknameContaining(String nickname);
}
