package kz.xodbar.freelancex.api.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.xodbar.freelancex.swagger.api.ProfileControllerTags;
import kz.xodbar.freelancex.useCase.user.profile.GetProfileDataUseCase;
import kz.xodbar.freelancex.useCase.user.profile.GetProfileDataUseCaseOutput;
import kz.xodbar.freelancex.util.AuthenticationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Api(tags = ProfileControllerTags.PROFILE_CONTROLLER_TAG)
public class ProfileController {

    private final GetProfileDataUseCase getProfileDataUseCase;

    @GetMapping
    @ApiOperation(value = ProfileControllerTags.GET_DATA__OPERATION)
    public ResponseEntity<?> getProfileInfo(HttpServletRequest request) {

        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        GetProfileDataUseCaseOutput result = getProfileDataUseCase.handle(request, token);
        return ResponseEntity.ok(result);
    }
}
