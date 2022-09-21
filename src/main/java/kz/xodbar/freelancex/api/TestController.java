package kz.xodbar.freelancex.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/check-auth")
    private ResponseEntity<String> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentAuthorizationUsername = authentication.getName();

        return ResponseEntity.ok("Authenticated as " + currentAuthorizationUsername);
    }
}
