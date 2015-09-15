package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by benjamin on 14/09/15.
 */
@Entity
public class Role extends Model {

    @Id
    public int id;

    public String name;


    private static Finder<Integer, Role> finder = new Finder<Integer, Role>(Role.class);

    public static Role getUserRole() {
        return finder.where().eq("name", "user").findUnique();
    }

    public static List<Role> all(){
        return finder.all();
    }

    public static Role find(int id){
        return finder.byId(id);
    }

    public static void deleteRole(int id){
        finder.deleteById(id);
    }
}
