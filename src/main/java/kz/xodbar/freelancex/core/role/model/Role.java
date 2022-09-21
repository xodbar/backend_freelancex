package kz.xodbar.freelancex.core.role.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public class Role implements GrantedAuthority {
    private Long id;
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return "id: " + id + " | role: " + role;
    }
}
