package controllers.helpers;

import models.Post;
import models.User;
import play.mvc.Http;

/**
 * Created by benjamin on 14/09/15.
 */
public class SessionHelper {

    private static User getUserFromSession() {
        Http.Context ctx = Http.Context.current();
        String email = ctx.session().get("user");
        if (email != null && !email.isEmpty()) {
            User u = User.findByEmail(email);
            return u;
        }
        return null;
    }

    public static User getCurrentUser() {
        return getUserFromSession();
    }

    public static String getCurrentUserName() {
        User u = getUserFromSession();
        if (u == null)
            return "Anonymous";
        return u.email;
    }

    public static boolean isRole(String role) {
        User u = getUserFromSession();
        if (u == null)
            return false;
        return u.role.name.equals(role);
    }


    public static boolean canDeletePost(int id){
        User u = getUserFromSession();
        if(u == null)
            return false;
        if(isRole("admin") || Post.find(id).user.equals(u)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAuthenticated() {
        return getUserFromSession() != null;
    }


}
