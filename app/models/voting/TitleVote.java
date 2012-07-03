package models.voting;

import models.User;
import models.Vote;
import models.assignment.TitleAssign;
import models.resourseproperties.Title;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 14/01/12
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TitleVote")
public class TitleVote extends Vote
{
    //@Required
    @OneToOne
    public TitleAssign titleAssign;

    public TitleVote(boolean upVote, User voter, TitleAssign titleAssign)
    {
        super(upVote, voter);
        this.titleAssign = titleAssign;
    }
}
