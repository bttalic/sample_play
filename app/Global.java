import com.avaje.ebean.Ebean;
import com.cloudinary.Cloudinary;
import models.Image;
import models.Post;
import models.Role;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Play;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.notFound;

/**
 * Created by benjamin on 14/09/15.
 */
public class Global extends GlobalSettings {

    public static User loggedInUser;

    @Override
    public void onStart(Application application) {
        super.onStart(application);

        Image.cloudinary = new Cloudinary("cloudinary://"+ Play.application().configuration().getString("cloudinary.string"));
        if (Ebean.find(User.class).findRowCount() == 0) {
            Role userRole = new Role();
            userRole.id = 1;
            userRole.name = "user";

            Role adminRole = new Role();
            adminRole.id = 2;
            adminRole.name = "admin";

            userRole.save();
            adminRole.save();

            User admin = new User();
            admin.email = "admin@mail.com";
            admin.role = adminRole;
            admin.password = "password";
            admin.save();

            User user = new User();
            user.email = "user@mail.com";
            user.role = userRole;
            user.password = "password";
            user.save();

            User user2 = new User();
            user2.email = "user2@mail.com";
            user2.role = userRole;
            user2.password = "password";
            user2.save();


            for (int i = 0; i < 50; i++) {
                Image img = new Image();
                img.image_url = "http://res.cloudinary.com/bttalic/image/upload/v1430775380/bzdxm6wmh2h8id3bgrnp.jpg";
                img.public_id = "bzdxm6wmh2h8id3bgrnp";
                img.save();
                Post p = new Post();
                p.content = "Some random content " + i;
                p.title = "Title " + i;
                p.user = i % 2 == 0 ? user : user2;
                p.image = img;
                p.save();
            }
        }

    }

    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader requestHeader) {
        return F.Promise.<Result>pure(notFound(views.html.notfound.render()));
    }

    @Override
    public F.Promise<Result> onBadRequest(RequestHeader request, String error) {
        return F.Promise.<Result>pure(badRequest(views.html.notfound.render()));
    }


}
