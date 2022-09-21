package kz.xodbar.freelancex.jwt;

import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.model.UserModel;

public class JwtUserFactory {

    public static User createJwtUser(UserModel model) {
        if (!model.getIsBlocked())
            return model.toDto();
        else return null;
    }
}
