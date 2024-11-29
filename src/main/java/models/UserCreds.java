package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreds {
  private String email;
  private String password;

  public static UserCreds credsFromUser(User user) {
    return new UserCreds(user.getEmail(), user.getPassword());
  }
}
