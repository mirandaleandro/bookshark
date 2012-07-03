package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.PublishingDate;
import models.voting.PublishingDateVote;
import models.voting.TitleVote;

import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PublishingDateAssign")
public class PublishingDateAssign extends AssignableProperty
{
    @OneToOne
    public PublishingDate publishingDate;

    public PublishingDateAssign(User assigner, Resource resource, PublishingDate publishingDate)
    {
        super(assigner, resource);
        this.publishingDate = publishingDate;
    }

    public String assignedProperty()
    {
        return publishingDate.toString();
    }

    public void vote(boolean isVoteUp, User voter)
    {
        PublishingDateVote previousVote = PublishingDateVote.find("byPublishingDateAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new PublishingDateVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  PublishingDateVote.find("byPublishingDateAssign", this).fetch();
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
            List<PublishingDateVote> votes = PublishingDateVote.find("byVoterAndPublishingDateAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<PublishingDateVote> votes = PublishingDateVote.find("byVoterAndPublishingDateAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
