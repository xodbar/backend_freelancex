package kz.xodbar.freelancex.util;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationResolver {

    public static String getAuthToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        if (!authToken.startsWith("Bearer "))
            return null;

        return authToken.substring(7);
    }
}
