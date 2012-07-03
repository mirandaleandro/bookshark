package models.voting;

import models.User;
import models.Vote;
import models.assignment.PublishingDateAssign;
import models.resourseproperties.PublishingDate;
import javax.persistence.Entity;
import play.data.validation.Required;

import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 14/01/12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PublishingDateVote")
public class PublishingDateVote extends Vote
{
    //@Required
    @OneToOne
    public PublishingDateAssign publishingDateAssign;

    public PublishingDateVote(boolean upVote, User voter, PublishingDateAssign publishingDateAssign)
    {
        super(upVote, voter);
        this.publishingDateAssign = publishingDateAssign;
    }
}
