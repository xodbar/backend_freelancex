package kz.xodbar.freelancex.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.xodbar.freelancex.swagger.api.RegisterControllerTags;
import kz.xodbar.freelancex.useCase.user.register.RegisterNewUserUseCase;
import kz.xodbar.freelancex.useCase.user.register.RegisterNewUserUseCaseInput;
import kz.xodbar.freelancex.useCase.user.register.RegisterNewUserUseCaseOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
@Api(tags = RegisterControllerTags.REGISTER_CONTROLLER_TAG)
public class RegisterController {

    private final RegisterNewUserUseCase registerNewUserUseCase;

    @PreAuthorize(value = "isAnonymous()")
    @PostMapping
    @ApiOperation(value = RegisterControllerTags.REGISTER_NEW_USER__OPERATION)
    public ResponseEntity<RegisterNewUserUseCaseOutput> register(
            @Validated @RequestBody RegisterNewUserUseCaseInput body
    ) {
        RegisterNewUserUseCaseOutput result = registerNewUserUseCase.handle(body);
        return ResponseEntity.ok(result);
    }
}
