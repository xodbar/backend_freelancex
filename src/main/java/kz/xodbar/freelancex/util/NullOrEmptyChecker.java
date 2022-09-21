package kz.xodbar.freelancex.util;

public class NullOrEmptyChecker {

    public static Boolean listDontContainNullOrEmptyString(String[] strings) {
        for (String s : strings)
            if (isNullOrBlank(s))
                return false;

        return true;
    }

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}
