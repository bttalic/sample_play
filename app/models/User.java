package models;

import com.avaje.ebean.Model;
import models.utils.Hash;
import org.hibernate.validator.constraints.Email;
import play.data.validation.Constraints;
import viewmodels.UserLoginViewModel;
import viewmodels.UserViewModel;

import javax.persistence.*;
import java.util.List;

/**
 * Created by benjamin on 14/09/15.
 */
@Entity
@Table(name = "ApplicationUser")
public class User extends Model {


    @Id
    @Email
    @Constraints.Required
    public String email;

    @Constraints.Required
    @Constraints.MinLength(6)
    public String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Post> posts;

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Role role;


    private static Finder<String, User> finder = new Finder<String, User>(User.class);

    @Override
    public void save() {
        this.password = Hash.createPassword(this.password);
        super.save();
    }

    public static User findByEmail(String email) {
        return finder.byId(email);
    }

    public static User userFromView(UserViewModel register) {
        User u = new User();
        u.email = register.email;
        u.password = register.password;
        return u;
    }

    public static boolean authenticate(String email, String password) {
        User u = User.findByEmail(email);
        if (u == null)
            return false;
        if (Hash.checkPassword(password, u.password))
            return true;
        return false;
    }

    public static User authenticate(UserLoginViewModel login) {
        User u = User.findByEmail(login.email);
        if (Hash.checkPassword(login.password, u.password))
            return u;
        return null;
    }

    public boolean isAdmin() {
        return this.role.id == 2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User other = (User)obj;
            if(other.email.equals(email))
                return true;
            return false;
        }
        return false;
    }

    public static List<User> all(){
        return finder.all();
    }


    public void update(){
        if(!canDelete(email))
            return;
        super.update();
    }

    public static boolean deleteUser(String email){
        if(!canDelete(email))
            return false;
        finder.deleteById(email);
        return true;
    }

    public static boolean canDelete(String email){
        int adminCount = finder.where().eq("role_id", 2).findRowCount();
        return adminCount > 1 || finder.byId(email).isAdmin() == false;
    }
}
