package models;

import play.db.jpa.Model;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 08/01/12
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class FolksonomyProperty extends Model
{
    public final static Integer USER_POINTS_ON_VOTE_UP = new Integer(10);
    public final static Integer USER_POINTS_ON_VOTE_DOWN = new Integer(10);

    //@Required
    @Temporal(TemporalType.DATE)
    public Date creationDate;

    //@Required
    @OneToOne
    public User creator;

    public FolksonomyProperty(User creator)
    {
       this.creator = creator;
       this.creationDate = new Date();
    }

    public FolksonomyProperty() {
    }

    public abstract boolean isAssignedToResource(Resource resource);

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

     /*
    public void vote(Vote vote)
    {
        if( vote.upVote )
        {
            this.points+=1;
            this.creator.points+=USER_POINTS_ON_VOTE_UP;
        }
        else
        {
            this.points-=1;
            this.creator.points-= USER_POINTS_ON_VOTE_DOWN;
        }
        this.votes.add(vote);
    }

    @Override
    public void vote(boolean upVote, User voter) {
        Vote vote = new Vote(upVote,voter);
        this.vote(vote);
    }   */
}
