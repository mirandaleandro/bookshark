package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Title;
import models.voting.TitleVote;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TitleAssign")
public class TitleAssign extends AssignableProperty
{
    //@Required
    @OneToOne
    public Title title;

    public TitleAssign(User assigner, Resource resource, Title title)
    {
        super(assigner, resource);
        this.title = title;
    }

    public String assignedProperty()
    {
        return title.title;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        TitleVote previousVote = TitleVote.find("byTitleAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

         new TitleVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  TitleVote.find("byTitleAssign", this).fetch();
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
            List<TitleVote> votes = TitleVote.find("byVoterAndTitleAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<TitleVote> votes = TitleVote.find("byVoterAndTitleAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
