package kz.xodbar.freelancex.core.role.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;

    public Role toDto() {
        return new Role(id, role);
    }
}
