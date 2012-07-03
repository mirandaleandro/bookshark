package models;

import models.resourseproperties.ArchivingFile;
import models.voting.ArchivingFileVote;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 28/12/11
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="User")

public class User extends LoginUser
{
    public final static int ROLE_VISITOR = 0;
    public final static int ROLE_PUBLISHER = 30;
    public final static int ROLE_ENTHUSIAST = 50;
    public final static int ROLE_INSPECTOR = 80;
    public final static int ROLE_REPORTER = 100;
    public final static int ROLE_EDITOR = 150;

    public final static int POINTS_FOR_UPLOADING = 5;
    
    //@Required
    public Integer points;

    public User(String name, String password, String username) {
        super(name, password, username);

        this.points = new Integer(0);
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void vote(boolean voteUp) {
        if(voteUp)
        {
           this.points+= Vote.USER_VOTED_UP_VALUE;
        }
        else
        {
           this.points-= Vote.USER_VOTED_DOWN_VALUE;
        }
        this.save();
    }


    public Boolean isVisitor()
    {
        return points >= ROLE_VISITOR;
    }


    public Boolean isPublisher()
    {
        return points >= ROLE_PUBLISHER;
    }

    public Boolean isEnthusiast()
    {
        return points >= ROLE_ENTHUSIAST;
    }

    public Boolean isInspector()
    {
        return points >= ROLE_INSPECTOR;
    }

    public Boolean isReporter()
    {
        return points >= ROLE_REPORTER;
    }

    public Boolean isEditor()
    {
        return points >= ROLE_EDITOR;
    }

    public void setPointsForRegistering()
    {
        this.setPoints(new Integer(ROLE_PUBLISHER));
    }

    public void hasRegistered()
    {
        this.setPointsForRegistering();
    }

    public void setPointsForUploading()
    {
        this.setPoints(points.intValue() + POINTS_FOR_UPLOADING);
    }

    public void hasUploaded()
    {
        this.setPointsForUploading();
    }
}
