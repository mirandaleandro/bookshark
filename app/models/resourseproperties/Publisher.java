package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.ArchivingFileAssign;
import models.assignment.PublisherAssign;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Publisher")
public class Publisher extends FolksonomyProperty
{
    //@Required
    public String publisher;

    public String city;

    public Publisher(String publisher, String city, User user)
    {
        super(user);
        this.publisher = publisher;
        this.city = city;
    }

    public Publisher(String publisher, User user)
    {
        super(user);
        this.publisher = publisher;
    }

    public boolean isAssignedToResource(Resource resource)
    {
        PublisherAssign assign = PublisherAssign.find("byPublisherAndResource",this,resource).first();
        return assign != null;
    }
    
    public String toString()
    {
        if(city != null)
        {
            return String.format("%s - %s",publisher,city);
        }
        
        return publisher;
    }
}
