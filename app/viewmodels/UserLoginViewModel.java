package viewmodels;

import models.User;
import org.hibernate.validator.constraints.Email;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin on 14/09/15.
 */
public class UserLoginViewModel {

    @Email
    public String email;
    public String password;


    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (User.authenticate(email, password) == false) {
            errors.add(new ValidationError("email", "Email/Password do not match"));
        }
        return errors.isEmpty() ? null : errors;
    }

}
