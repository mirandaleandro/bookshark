package models.voting;

import models.User;
import models.Vote;
import models.assignment.IdentifierAssign;
import models.resourseproperties.Identifier;
import javax.persistence.Entity;
import play.data.validation.Required;

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
@Table(name="IdentifierVote")
public class IdentifierVote extends Vote
{
    //@Required
    @OneToOne
    public IdentifierAssign identifierAssign;

    public IdentifierVote(boolean upVote, User voter, IdentifierAssign identifierAssign)
    {
        super(upVote, voter);
        this.identifierAssign = identifierAssign;
    }
}
