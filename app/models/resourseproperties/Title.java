package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.TitleAssign;
import play.data.validation.Required;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Title")
public class Title extends FolksonomyProperty
{
    //@Required
    public String title;

    public Title(String title, User user)
    {
        super(user);
        this.title = title;
    }

    public boolean isAssignedToResource(Resource resource)
    {
        TitleAssign assign = TitleAssign.find("byTitleAndResource",this,resource).first();
        return assign != null;
    }
}
