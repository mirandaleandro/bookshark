package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Language;
import models.voting.LanguageVote;
import models.voting.TitleVote;

import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="LanguageAssign")
public class LanguageAssign extends AssignableProperty
{
    @OneToOne
    public Language language;

    public LanguageAssign(User assigner, Resource resource, Language language)
    {
        super(assigner, resource);
        this.language = language;
    }


    public String assignedProperty()
    {
        return language.language;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        LanguageVote previousVote = LanguageVote.find("byLanguageAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new LanguageVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  LanguageVote.find("byLanguageAssign", this).fetch();
        for(Vote v:votes)
        {
            if(v.upVote)
            {
                votingResults+=1;
            }
            else
            {
                votingResults-=1;
            }
        }
        return votingResults;
    }
    //called from the UI
    public boolean isVotedUp(User user)
    {
        if(user != null)
        {
            List<LanguageVote> votes = LanguageVote.find("byVoterAndLanguageAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<LanguageVote> votes = LanguageVote.find("byVoterAndLanguageAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
