package models.voting;

import models.User;
import models.Vote;
import models.assignment.AuthorAssign;
import models.resourseproperties.Author;
import javax.persistence.Entity;
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
@Table(name="AuthorVote")
public class AuthorVote extends Vote
{
    //@Required
    @OneToOne
    public AuthorAssign authorAssign;

    public AuthorVote(boolean upVote, User voter, AuthorAssign authorAssign)
    {
        super(upVote, voter);
        this.authorAssign = authorAssign;
    }
}
