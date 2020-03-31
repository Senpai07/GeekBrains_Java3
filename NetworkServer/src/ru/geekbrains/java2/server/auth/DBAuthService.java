package ru.geekbrains.java2.server.auth;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class DBAuthService implements AuthService {

    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;
    private static final List<DBAuthService.UserData> USER_DATA =
            List.of(new DBAuthService.UserData("login1", "pass1", "username1"),
                    new DBAuthService.UserData("login2", "pass2", "username2"),
                    new DBAuthService.UserData("login3", "pass3", "username3"));

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
            DBAuthService.UserData userData = (DBAuthService.UserData) obj;
            return Objects.equals(login, userData.login)
                    && Objects.equals(password, userData.password)
                    && Objects.equals(username, userData.username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(login, password, username);
        }
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        try {
            resSet = statmt.executeQuery("SELECT * FROM users");
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String login1 = resSet.getString("login");
                String password1 = resSet.getString("password");
                String username1 = resSet.getString("username");
                if (login1.equals(login) && password1.equals(password)) {
                    return username1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void createDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text, 'username' text);");

        System.out.println("Таблица создана или уже существует.");
    }

    private static void writeDB() throws SQLException {
        resSet = statmt.executeQuery("SELECT count(*) FROM users");
        int count = 0;
        while (resSet.next()) {
            count = resSet.getInt(1);
        }
        if (count == 0) {
            for (DBAuthService.UserData userDatum : USER_DATA) {
                statmt.execute(String.format("INSERT INTO 'users' ('login', 'password', 'username') VALUES ('%s', '%s', '%s'); ",
                        userDatum.login, userDatum.password, userDatum.username));
            }
            System.out.println("Таблица заполнена");
        }
    }

    @Override
    public void start() {
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:USERS.s3db");
            createDB();
            writeDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        try {
            resSet.close();
            statmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Сервис аутентификации оставлен");
    }
}
