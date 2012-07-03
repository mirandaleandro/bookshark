package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Description;
import models.voting.DescriptionVote;
import models.voting.TitleVote;

import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="DescriptionAssign")
public class DescriptionAssign extends AssignableProperty
{
    //@Required
    @OneToOne
    public Description description;
    
    public boolean isCurrent;


    public Long revision()
    {
        return this.id;
    }

    public String revisionDescription()
    {
        return String.format("v.%d",this.id);
    }
    
    public DescriptionAssign(User assigner, Resource resource, Description description)
    {
        super(assigner, resource);
        this.description = description;
        this.isCurrent = false;
    }

    public DescriptionAssign(User assigner, Resource resource, Description description,boolean isCurrent)
    {
        this(assigner,resource,description);
        this.isCurrent = isCurrent;
    }



    public String assignedProperty()
    {
        return description.description;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        DescriptionVote previousVote = DescriptionVote.find("byDescriptionAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new DescriptionVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  DescriptionVote.find("byDescriptionAssign", this).fetch();
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
            List<DescriptionVote> votes = DescriptionVote.find("byVoterAndDescriptionAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<DescriptionVote> votes = DescriptionVote.find("byVoterAndDescriptionAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
