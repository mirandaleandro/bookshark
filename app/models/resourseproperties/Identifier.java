package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.IdentifierAssign;
import play.data.validation.Required;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Identifier")
public class Identifier extends FolksonomyProperty
{
    //@Required
    public String identifier;

    public Identifier(User creator)
    {
        super(creator);
        this.identifier = this._generateID();
    }

    public Identifier(String identifier, User creator)
    {
        super(creator);
        this.identifier = identifier;
    }

    public String _generateID()
    {
        return UUID.randomUUID().toString();
    }

    public boolean isAssignedToResource(Resource resource)
    {
        IdentifierAssign assign = IdentifierAssign.find("byIdentifierAndResource",this,resource).first();
        return assign != null;
    }
}
