package models;

import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.db.jpa.Model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class LoginUser extends Model {

    //@Required
    @MaxSize(15)
    @MinSize(4)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;

    //@Required
    @MaxSize(15)
    @MinSize(5)
    public String password;

    //@Required
    @MaxSize(100)
    public String name;

    public LoginUser(String name, String password, String username) {
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String toString()  {
        return "User(" + username + ")";
    }

}
