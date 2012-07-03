package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 08/01/12
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class Vote extends Model
{
    final static int USER_VOTED_UP_VALUE = 10;
    final static  int USER_VOTED_DOWN_VALUE = 10;

    //@Required
    public boolean upVote;

    @Required
    @OneToOne
    public User voter;

    @Temporal(TemporalType.DATE)
    public Date voteDate;

    public Vote(boolean upVote, User voter)
    {
        this.upVote = upVote;
        this.voter = voter;
        this.voteDate = new Date();
    }

}
