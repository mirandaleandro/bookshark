package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.ArchivingFileAssign;
import models.assignment.PublishingDateAssign;
import play.data.validation.Required;
import play.mvc.Router;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PublishingDate")
public class PublishingDate extends FolksonomyProperty
{
    final static String[] MONTHS_DESCRIPTIONS = {"Undefined",
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    //@Required
    public Integer year;
    
    public Integer month;

    public PublishingDate(Integer year, Integer month, User user)
    {

        super(user);

        if(month != null)
        {
            if(month < 1)
                month = 1;
            else
            if (month.intValue() > 12)
                month = 12;

        }

        this.year = year;
        this.month = month;
    }


    public boolean isAssignedToResource(Resource resource)
    {
        PublishingDateAssign assign = PublishingDateAssign.find("byPublishingDateAndResource",this,resource).first();
        return assign != null;
    }

    public String toString()
    {
        if(year == null)
            return "undefined data";

        if(month == null || month.intValue() == 0)
        {
            return year.toString();
        }

        return String.format("%s - %s",MONTHS_DESCRIPTIONS[month], year);
    }
}
