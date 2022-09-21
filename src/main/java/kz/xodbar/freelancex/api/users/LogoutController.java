package kz.xodbar.freelancex.api.users;

import io.swagger.annotations.Api;
import kz.xodbar.freelancex.swagger.api.LogoutControllerTags;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logout")
@Api(tags = LogoutControllerTags.LOGOUT_CONTROLLER_TAG)
public class LogoutController {
    // TODO IMPLEMENT
}
