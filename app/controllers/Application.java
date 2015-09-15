package controllers;

import models.Post;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

public class Application extends Controller {


    public Result index() {
        return ok(index.render(Post.getPosts(0)));
    }


}
