package kz.xodbar.freelancex.swagger.api;

public class LoginControllerTags {

    public static final String LOGIN_CONTROLLER_TAG = "The controller is used to log in to the system." +
            " If the username and password data" +
            " are correct, it generates a JWT token for subsequent requests.";

    public static final String AUTH__OPERATION = "Endpoint for user authentication - after successful authorization," +
            " it returns the user name and the generated JWT token.";
}
