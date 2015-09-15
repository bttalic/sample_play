package controllers.helpers;

import play.mvc.Http;

/**
 * Created by benjamin on 14/09/15.
 */
public class RequestHelper {

    public static boolean isAjax() {
        Http.Request request = Http.Context.current().request();
        if (request.getHeader("X-Requested-With") != null) {
            return true;
        }
        return false;
    }

}
