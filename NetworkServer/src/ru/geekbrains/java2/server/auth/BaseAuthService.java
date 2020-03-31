package ru.geekbrains.java2.server.auth;

import java.util.List;
import java.util.Objects;

public class BaseAuthService implements AuthService {

  private static class UserData {
    private String login;
    private String password;
    private String username;

    public UserData(String login, String password, String username) {
      this.login = login;
      this.password = password;
      this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      UserData userData = (UserData) obj;
      return Objects.equals(login, userData.login)
          && Objects.equals(password, userData.password)
          && Objects.equals(username, userData.username);
    }

    @Override
    public int hashCode() {
      return Objects.hash(login, password, username);
    }
  }

  private static final List<UserData> USER_DATA =
      List.of(
          new UserData("login1", "pass1", "username1"),
          new UserData("login2", "pass2", "username2"),
          new UserData("login3", "pass3", "username3"));

  @Override
  public String getUsernameByLoginAndPassword(String login, String password) {
    for (UserData userDatum : USER_DATA) {
      if (userDatum.login.equals(login) && userDatum.password.equals(password)) {
        return userDatum.username;
      }
    }
    return null;
  }

  @Override
  public void start() {
    System.out.println("Сервис аутентификации запущен");
  }

  @Override
  public void stop() {
    System.out.println("Сервис аутентификации оставлен");
  }
}
