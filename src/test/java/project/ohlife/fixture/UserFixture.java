package project.ohlife.fixture;

import project.ohlife.domain.user.User;

public class UserFixture {
  public static User get(String email, String password) {
    return User.builder()
        .email(email)
        .password(password)
        .build();
  }

}
