package kz.xodbar.freelancex.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.xodbar.freelancex.api.users.request.UpdateUserInput;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.swagger.api.ProfileControllerTags;
import kz.xodbar.freelancex.useCase.user.profile.getData.GetProfileDataUseCase;
import kz.xodbar.freelancex.useCase.user.profile.getData.GetProfileDataUseCaseOutput;
import kz.xodbar.freelancex.useCase.user.profile.updatePassword.UpdatePasswordUseCase;
import kz.xodbar.freelancex.useCase.user.profile.updatePassword.UpdatePasswordUseCaseInput;
import kz.xodbar.freelancex.useCase.user.profile.updatePassword.UpdatePasswordUseCaseOutput;
import kz.xodbar.freelancex.util.AuthenticationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Api(tags = ProfileControllerTags.PROFILE_CONTROLLER_TAG)
public class ProfileController {

    private final GetProfileDataUseCase getProfileDataUseCase;

    private final UpdatePasswordUseCase updatePasswordUseCase;

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @ApiOperation(value = ProfileControllerTags.GET_DATA__OPERATION)
    public ResponseEntity<?> getProfileInfo(HttpServletRequest request) {

        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        GetProfileDataUseCaseOutput result = getProfileDataUseCase.handle(request, token);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updatePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePassword(
            @Validated @RequestBody UpdatePasswordUseCaseInput body,
            HttpServletRequest request
    ) {

        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        UpdatePasswordUseCaseOutput result = updatePasswordUseCase.handle(body, token);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> updateProfile(
            @Validated @RequestBody UpdateUserInput input,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.ok(null);

        String authorsUsername = jwtTokenProvider.getUsername(token);
        User user = userService.getByUsername(authorsUsername);

        return ResponseEntity.ok(userService.updateUser(new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                input.getNewName(),
                input.getNewSurname(),
                input.getNewPhone(),
                input.getNewEmail(),
                user.getIsBlocked(),
                user.getRoles()
        )));
    }
}
