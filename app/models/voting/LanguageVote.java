package models.voting;

import models.User;
import models.Vote;
import models.assignment.LanguageAssign;
import models.resourseproperties.Language;

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
@Table(name="LanguageVote")
public class LanguageVote extends Vote
{
    //@Required
    @OneToOne
    public LanguageAssign languageAssign;

    public LanguageVote(boolean upVote, User voter, LanguageAssign languageAssign) {
        super(upVote, voter);
        this.languageAssign = languageAssign;
    }
}
