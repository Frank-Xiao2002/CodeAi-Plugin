package top.frankxxj.codeai.plugin.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUser {
    private String username;
    private String roles;
    private String token;

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
