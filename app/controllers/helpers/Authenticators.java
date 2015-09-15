package controllers.helpers;


import controllers.helpers.SessionHelper;
import controllers.routes;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;

/**
 * Created by benjamin on 14/09/15.
 */
public class Authenticators {

    public static class Admin extends Security.Authenticator {

        @Override
        public String getUsername(Http.Context context) {
            models.User u = SessionHelper.getCurrentUser();
            if (u == null)
                return null;

            if (u.isAdmin())
                return u.email;
            return null;
        }

        @Override
        public Result onUnauthorized(Http.Context context) {
            return Results.redirect(routes.UsersController.loginView());
        }
    }

    public static class User extends Security.Authenticator {

        @Override
        public String getUsername(Http.Context context) {
            models.User u = SessionHelper.getCurrentUser();
            if (u != null)
                return u.email;
            return null;
        }

        @Override
        public Result onUnauthorized(Http.Context context) {
            return redirect(routes.UsersController.loginView());
        }
    }
}
