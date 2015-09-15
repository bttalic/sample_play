package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by benjamin on 14/09/15.
 */
@Entity
public class Post extends Model {

    @Id
    public int id;

    @Constraints.Required
    public String title;

    @Constraints.Required
    @Constraints.MinLength(10)
    public String content;

    @ManyToOne
    public User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    public Image image;

    private static Finder<Integer, Post> finder = new Finder<Integer, Post>(Post.class);

    private static int PAGE_SIZE = 10;

    public static List<Post> getPosts(int offset) {
        return finder.order("id desc").findPagedList(offset, PAGE_SIZE).getList();
    }

    public static Post find(int id) {
        return finder.byId(id);
    }

    public String shortContent(){
        return this.content.substring(0, 10);
    }

    public static List<Post> all(){
        return finder.all();
    }

    @Override
    public void save() {
        if(this.user == null)
            this.user = User.all().get(0);
        super.save();
    }

    @Override
    public void delete() {
        this.image.deleteImage();
        super.delete();
    }
}
