package models.voting;

import models.User;
import models.Vote;
import models.assignment.PublisherAssign;
import models.resourseproperties.Publisher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 14/01/12
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PublisherVote")
public class PublisherVote extends Vote
{

    //@Required
    @OneToOne
    public PublisherAssign publisherAssign;

    public PublisherVote(boolean upVote, User voter, PublisherAssign publisherAssign)
    {
        super(upVote, voter);
        this.publisherAssign = publisherAssign;
    }
}
