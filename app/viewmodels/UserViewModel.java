package viewmodels;

import models.Image;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin on 14/09/15.
 */
public class UserViewModel {

    @Constraints.Required
    public String email;

    @Constraints.Required
    @Constraints.MinLength(6)
    public String password;

    public String passwordConfirm;

    public Image image;


    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (!this.password.equals(this.passwordConfirm)) {
            errors.add(new ValidationError("password", "Passwords must match."));
        }
        return errors.isEmpty() ? null : errors;
    }

}
