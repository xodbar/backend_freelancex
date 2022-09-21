package kz.xodbar.freelancex.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.xodbar.freelancex.swagger.api.LoginControllerTags;
import kz.xodbar.freelancex.useCase.user.login.LoginUserUseCase;
import kz.xodbar.freelancex.useCase.user.login.LoginUserUseCaseInput;
import kz.xodbar.freelancex.useCase.user.login.LoginUserUseCaseOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Api(tags = LoginControllerTags.LOGIN_CONTROLLER_TAG)
public class LoginController {

    private final LoginUserUseCase loginUserUseCase;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ApiOperation(value = LoginControllerTags.AUTH__OPERATION)
    public ResponseEntity<LoginUserUseCaseOutput> auth(@Validated @RequestBody LoginUserUseCaseInput input) {
        LoginUserUseCaseOutput result = loginUserUseCase.handle(input);
        return ResponseEntity.ok(result);
    }

}
