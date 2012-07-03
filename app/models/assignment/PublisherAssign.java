package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Publisher;
import models.voting.PublisherVote;
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
@Table(name="PublisherAssign")
public class PublisherAssign extends AssignableProperty
{
    //@Required
    @OneToOne
    public Publisher publisher;

    public PublisherAssign(User assigner, Resource resource, Publisher publisher)
    {
        super(assigner, resource);
        this.publisher = publisher;
    }


    public String assignedProperty()
    {
        return publisher.toString();
    }

    public void vote(boolean isVoteUp, User voter)
    {
        PublisherVote previousVote = PublisherVote.find("byPublisherAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new PublisherVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  PublisherVote.find("byPublisherAssign", this).fetch();
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
            List<PublisherVote> votes = PublisherVote.find("byVoterAndPublisherAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<PublisherVote> votes = PublisherVote.find("byVoterAndPublisherAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
