package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.AuthorAssign;
import play.data.validation.Required;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Author")
public class Author extends FolksonomyProperty
{
    //@Required
    public String author;

    public Author(String author, User user)
    {
        super(user);
        this.author = author;
    }

    public boolean isAssignedToResource(Resource resource)  
    {
        AuthorAssign assign = AuthorAssign.find("byAuthorAndResource",this,resource).first();
        return assign != null;
    }
}
