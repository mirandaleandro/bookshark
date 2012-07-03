package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Identifier;
import models.voting.IdentifierVote;
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
@Table(name="IdentifierAssign")
public class IdentifierAssign extends AssignableProperty
{
    //@Required
    @OneToOne
    public Identifier identifier;

    public IdentifierAssign(User assigner, Resource resource, Identifier identifier) {
        super(assigner, resource);
        this.identifier = identifier;
    }

    public String assignedProperty()
    {
        return  identifier.identifier;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        IdentifierVote previousVote = IdentifierVote.find("byIdentifierAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new IdentifierVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  IdentifierVote.find("byIdentifierAssign", this).fetch();
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
            List<IdentifierVote> votes = IdentifierVote.find("byVoterAndIdentifierAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<IdentifierVote> votes = TitleVote.find("byVoterAndIdentifierAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
