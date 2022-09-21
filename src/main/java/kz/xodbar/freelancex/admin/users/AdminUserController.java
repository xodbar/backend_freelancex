package kz.xodbar.freelancex.admin.users;

import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.useCase.user.admin.block.AdminBlockUserUseCase;
import kz.xodbar.freelancex.useCase.user.admin.block.AdminBlockUserUseCaseInput;
import kz.xodbar.freelancex.useCase.user.admin.block.AdminBlockUserUseCaseOutput;
import kz.xodbar.freelancex.util.AuthenticationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminBlockUserUseCase adminBlockUserUseCase;

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);
        if (token == null)
            return ResponseEntity.ok(null);

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);
        if (token == null)
            return ResponseEntity.ok(null);

        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/changeIsBlocked")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminBlockUserUseCaseOutput> changeIsBlocked(
            @Validated @RequestBody AdminBlockUserUseCaseInput body,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);
        if (token == null)
            return ResponseEntity.ok(new AdminBlockUserUseCaseOutput(
                    null,
                    "Token is not present"
            ));

        return ResponseEntity.ok(adminBlockUserUseCase.handle(body, token));
    }
}
