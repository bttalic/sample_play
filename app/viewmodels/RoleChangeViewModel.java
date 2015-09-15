package viewmodels;

import org.hibernate.validator.constraints.Email;
import play.data.validation.Constraints;

/**
 * Created by benjamin on 15/09/15.
 */
public class RoleChangeViewModel {


    @Email
    @Constraints.Required
    public String email;

    @Constraints.Required
    public int roleId;

}
