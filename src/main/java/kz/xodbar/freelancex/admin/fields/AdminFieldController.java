package kz.xodbar.freelancex.admin.fields;

import kz.xodbar.freelancex.useCase.field.CreateNewFieldUseCase;
import kz.xodbar.freelancex.useCase.field.CreateNewFieldUseCaseInput;
import kz.xodbar.freelancex.useCase.field.CreateNewFieldUseCaseOutput;
import kz.xodbar.freelancex.util.AuthenticationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/fields")
public class AdminFieldController {

    private final CreateNewFieldUseCase createNewFieldUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateNewFieldUseCaseOutput> createNewField(
            @Validated @RequestBody CreateNewFieldUseCaseInput body,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);
        if (token == null)
            ResponseEntity.ok(new CreateNewFieldUseCaseOutput(
                    null,
                    "Token is not present"
            ));

        return ResponseEntity.ok(createNewFieldUseCase.handle(body, token));
    }
}
