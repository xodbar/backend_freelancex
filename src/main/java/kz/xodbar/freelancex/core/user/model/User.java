package kz.xodbar.freelancex.core.user.model;

import java.util.Collection;
import java.util.List;
import kz.xodbar.freelancex.core.role.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@Getter
public class User implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Boolean isBlocked;
    private List<Role> roles;

    @Override
    public Collection<Role> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isBlocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isBlocked;
    }

    @Override
    public boolean isEnabled() {
        return !isBlocked;
    }

    @Override
    public String toString() {
        return "id: " + id + " | username: " + username + " | password: " + password + " | " + name + " | surname: "
                + surname + " | isBlocked: " + isBlocked + " roles: " + roles;
    }
}
