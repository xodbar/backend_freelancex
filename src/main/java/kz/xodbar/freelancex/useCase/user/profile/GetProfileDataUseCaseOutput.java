package kz.xodbar.freelancex.useCase.user.profile;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetProfileDataUseCaseOutput {
    private String username;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private List<String> roles;

    private String errorMessage;
}
