package kz.xodbar.freelancex.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private final List<String> ignoringPaths = Arrays.asList(
            "/api/",
            "/api/home",
            "/api/about",
            "/api/support",
            "/api/login",
            "/api/login/auth",
            "/api/register",
            "/api/orders",
            "/api/orders/orderBy",
            "/api/orders/filter/byPrice",
            "/api/orders/filter/byField",
            "/health",
            "/error",
            "/error/",
            "/api/fields",
            "/api/fields/",
            "/swagger-ui/",
            "/swagger-ui/springfox.css",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/springfox.js",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-ui-standalone-preset.js"
    );

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            HttpServletRequest request = (HttpServletRequest) req;

            if (isIgnoredPath(request)) {
                chain.doFilter(req, response);
                return;
            }

            String token = jwtTokenProvider.resolveToken(request);

            if (token == null || !jwtTokenProvider.tokenIsValid(token))
                throw new IOException("Token is invalid or expired!");

            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (authentication == null)
                throw new IOException("Authentication can not be null!");

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(req, response);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            httpServletResponse.sendRedirect("https://www.google.com/");
        }
    }

    // TODO ADD REDIRECT FOR EXPIRED

    private boolean isIgnoredPath(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        System.out.println("REQUESTED URI: " + requestUri);

        if (requestUri.contains("/api/orders")
                && !requestUri.contains("/create")
                && !requestUri.contains("/updateStatus"))
                return true;

        return ignoringPaths.contains(requestUri);
    }
}
