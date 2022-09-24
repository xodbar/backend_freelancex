package kz.xodbar.freelancex.api.users.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserInput {
    private String newName;
    private String newSurname;
    private String newEmail;
    private String newPhone;
}
