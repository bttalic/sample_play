package controllers;


import controllers.helpers.RequestHelper;
import controllers.helpers.SessionHelper;
import models.Image;
import models.Post;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import views.html.post._list;
import views.html.post.create;
import views.html.post.show;

import java.io.File;
import java.util.List;

/**
 * Created by benjamin on 15/09/15.
 */


public class PostsController extends Controller {

    private static int POST_COUNT = 20;

    private static Form<Post> postForm = Form.form(Post.class);


    public Result createPostView() {
        Post p = new Post();
        p.title = "title 3";
        p.content = "Some content long";
        postForm = postForm.fill(p);

        return ok(create.render(postForm));
    }

    public Result show(int id){
        Post p = Post.find(id);
        if(p == null)
            return notFound("hello"+p.id);
        else
            return ok(show.render(p));
    }

    public Result createPost() {
        Form<Post> submitted = postForm.bindFromRequest();
        if (submitted.hasErrors()) {
            if (RequestHelper.isAjax())
                return badRequest(submitted.errorsAsJson());
            return badRequest(create.render(submitted));
        }

        if (RequestHelper.isAjax())
            return ok();

        MultipartFormData body = request().body().asMultipartFormData();
        MultipartFormData.FilePart filePart = body.getFile("image");
        Logger.debug("Content type: " + filePart.getContentType());
        Logger.debug("Key: " + filePart.getKey());
        File image = filePart.getFile();


        Post p = submitted.get();
        p.image = Image.create(image);
        p.user = SessionHelper.getCurrentUser();
        p.save();

        return redirect(routes.Application.index());
    }


    public Result posts(int offset) {
        List<Post> postList = Post.getPosts(offset);
        if (postList.size() < 1)
            return notFound();
        return ok(_list.render(postList));
    }


    public Result delete(int id) {
        Post.find(id).delete();
        if (RequestHelper.isAjax())
            return ok();
        return redirect(routes.Application.index());
    }

}
