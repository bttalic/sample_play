package controllers;

import controllers.helpers.RequestHelper;
import models.Post;
import models.Role;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import viewmodels.RoleChangeViewModel;
import views.html.user.list;

/**
 * Created by benjamin on 15/09/15.
 */
public class AdminController extends Controller {

    public Result showPosts() {
        return ok(views.html.post.list.render(Post.all()));
    }

    public Result showUsers() {
        return ok(list.render(User.all(), Role.all()));
    }


    public Result showRoles(){
        return ok(views.html.role.list.render(Role.all()));
    }

    public Result updateRole(){
        Form<Role> submitted = Form.form(Role.class).bindFromRequest();
        if(submitted.hasErrors()){
            if(RequestHelper.isAjax())
                return badRequest(submitted.errorsAsJson());
        }
        if(RequestHelper.isAjax())
            return ok();

        Role r = submitted.get();
        r.update();
        return redirect(routes.AdminController.showRoles());
    }


    public Result changeRole() {
        Form<RoleChangeViewModel> submitted = Form.form(RoleChangeViewModel.class).bindFromRequest();
        if (submitted.hasErrors()) {
            if (RequestHelper.isAjax())
                return badRequest(submitted.errorsAsJson());
            else
                return badRequest();
        }
        if (RequestHelper.isAjax())
            return ok();
        RoleChangeViewModel rc = submitted.get();
        User u = User.findByEmail(rc.email);
        Role r = Role.find(rc.roleId);
        if (r == null) {
            return notFound("No such role");
        }
        u.role = r;
        u.update();
        return redirect(routes.AdminController.showUsers());
    }

    public Result deleteUser(String email) {
        if (User.deleteUser(email))
            return redirect(routes.AdminController.showUsers());
        else
            return badRequest("Can't delete last admin");
    }

}
