package models.voting;

import models.User;
import models.Vote;
import javax.persistence.Entity;

import models.assignment.DescriptionAssign;
import play.data.validation.Required;

import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 14/01/12
 * Time: 20:47
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="DescriptionVote")
public class DescriptionVote extends Vote
{
    //@Required
    @OneToOne
    public DescriptionAssign descriptionAssign;

    public DescriptionVote(boolean upVote, User voter, DescriptionAssign descriptionAssign)
    {
        super(upVote, voter);
        this.descriptionAssign = descriptionAssign;
    }
}
