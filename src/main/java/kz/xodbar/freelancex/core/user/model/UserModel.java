package kz.xodbar.freelancex.core.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import kz.xodbar.freelancex.core.role.model.Role;
import kz.xodbar.freelancex.core.role.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "t_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean isBlocked;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleModel> roles;

    public User toDto() {
        List<Role> roles = new ArrayList<>();

        for (RoleModel roleModel : this.roles)
            roles.add(roleModel.toDto());

        return new User(
                this.id,
                this.username,
                this.password,
                this.name,
                this.surname,
                this.phone,
                this.email,
                this.isBlocked,
                roles
        );
    }
}

