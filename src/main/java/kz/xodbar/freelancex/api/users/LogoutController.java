package kz.xodbar.freelancex.api.users;

import io.swagger.annotations.Api;
import kz.xodbar.freelancex.swagger.api.LogoutControllerTags;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/logout")
@Api(tags = LogoutControllerTags.LOGOUT_CONTROLLER_TAG)
public class LogoutController {

}
