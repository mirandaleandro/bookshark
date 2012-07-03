package models.voting;

import models.User;
import models.Vote;
import models.assignment.SubjectAssign;
import models.resourseproperties.Subject;
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
@Table(name="SubjectVote")
public class SubjectVote extends Vote
{
    //@Required
    @OneToOne
    public SubjectAssign subjectAssign;

    public SubjectVote(boolean upVote, User voter, SubjectAssign subjectAssign)
    {
        super(upVote, voter);
        this.subjectAssign = subjectAssign;
    }
}
