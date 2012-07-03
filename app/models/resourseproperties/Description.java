package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.ArchivingFileAssign;
import models.assignment.DescriptionAssign;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Description")
public class Description extends FolksonomyProperty
{
    //@Required
    @Column(columnDefinition="TEXT")
    public String description;

    public Description(String description, User creator)
    {
        super(creator);
        this.description = description;
    }

    public boolean isAssignedToResource(Resource resource)
    {
        DescriptionAssign assign = DescriptionAssign.find("byDescriptionAndResource",this,resource).first();
        return assign != null;
    }


}
