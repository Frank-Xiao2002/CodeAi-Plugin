package top.frankxxj.codeai.plugin.entity;

import lombok.*;
import org.jetbrains.annotations.NonNls;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"token"})
public class AppUser {
    @NonNls
    public static final String ROLE_SPLIT = ",";
    private Long id;
    private String username;
    private String roles;
    private String token;

    private static final AppUser EMPTY_USER = new AppUser(0L, "", "", "");

    public static AppUser empty() {
        return EMPTY_USER;
    }
}
