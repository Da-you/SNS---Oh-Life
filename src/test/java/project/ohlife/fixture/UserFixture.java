package project.ohlife.fixture;

import lombok.Data;
import project.ohlife.domain.user.User;
import project.ohlife.domain.user.common.UserRole;

public class UserFixture {

  public static User get(String email, String password) {
    User user = User.createUser(email,password, UserRole.USER, "nickname",
        "010-1234-5678");
    return user;
  }



}
