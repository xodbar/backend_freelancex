package kz.xodbar.freelancex.jwt;

import io.jsonwebtoken.*;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import kz.xodbar.freelancex.core.role.model.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final PasswordEncoder defaultPasswordEncoder;

    private final JwtUserDetailsService userDetailsService;

    @Value("${security.jwt.token.secret}")
    private String secret;

    @Value("${security.jwt.token.expiration}")
    private int expiration;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String generateToken(String username, List<Role> authorities) {
        logger.info("Trying to generate token for " + username + " with roles " + authorities);

        try {
            Claims claims = Jwts.claims().setSubject(username);

            if (getRoleNames(authorities) == null)
                throw new Exception("Error while generating token because roles is empty!");

            claims.put("roles", getRoleNames(authorities));

            Date now = new Date();
            Date validity = new Date(now.getTime() + expiration);

            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();

        } catch (Exception e) {
            logger.error("Failed to generate jwt token for username " + username);
            e.printStackTrace();
            return null;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String jwtToken) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        logger.info("Resolving token: " + bearerToken);

        if (bearerToken == null || !bearerToken.startsWith("Bearer "))
            return null;

        return bearerToken.substring(7);
    }

    public boolean tokenIsValid(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        if (userRoles.isEmpty())
            return null;

        return userRoles.stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
    }
}
