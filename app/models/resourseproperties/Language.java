package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.LanguageAssign;
import play.data.validation.Required;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Language")
public class Language extends FolksonomyProperty
{
    //@Required
    public String language;

    public Language(String language, User user)
    {
        super(user);
        this.language = language;
    }

    public boolean isAssignedToResource(Resource resource)
    {
        LanguageAssign assign = LanguageAssign.find("byLanguageAndResource",this,resource).first();
        return assign != null;
    }
}
