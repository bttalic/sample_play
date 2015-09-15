package controllers;

import controllers.helpers.RequestHelper;
import controllers.helpers.SessionHelper;
import models.Role;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import viewmodels.UserLoginViewModel;
import viewmodels.UserViewModel;
import views.html.user.login;
import views.html.user.register;

/**
 * Created by benjamin on 14/09/15.
 */
public class UsersController extends Controller {

    private static Form<UserViewModel> registerForm = Form.form(UserViewModel.class);
    private static Form<UserLoginViewModel> loginForm = Form.form(UserLoginViewModel.class);


    public Result registerView() {
        return ok(register.render(registerForm));
    }

    private void loginUser(User u) {
        session().clear();
        session().put("user", String.valueOf(u.email));
    }

    public Result registerUser() {
        Form<UserViewModel> submited = registerForm.bindFromRequest();
        if (submited.hasErrors()) {
            if (RequestHelper.isAjax())
                return badRequest(submited.errorsAsJson());
            return badRequest(register.render(submited));
        }
        if (RequestHelper.isAjax())
            return ok();
        User u = User.userFromView(submited.get());
        u.role = Role.getUserRole();
        u.save();
        loginUser(u);
        return redirect(routes.Application.index());
    }

    public Result loginView() {
        return ok(login.render(loginForm));
    }

    public Result loginUser() {
        Form<UserLoginViewModel> submited = loginForm.bindFromRequest();
        if (submited.hasErrors()) {
            if (RequestHelper.isAjax())
                return badRequest(submited.errorsAsJson());
            return badRequest(login.render(submited));
        }
        loginUser(User.authenticate(submited.get()));

        if (RequestHelper.isAjax())
            return ok();
        return redirect(routes.Application.index());
    }

    public Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }

    public Result profile(String email) {
        return TODO;
    }

    public Result logedinProfile() {
        if (SessionHelper.isAuthenticated()) {
            return redirect(routes.UsersController.profile(SessionHelper.getCurrentUserName()));
        } else {
            return badRequest("Don't try this anymore");
        }
    }
}