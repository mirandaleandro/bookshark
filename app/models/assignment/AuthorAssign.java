package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Author;
import models.voting.AuthorVote;
import models.voting.TitleVote;

import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="AuthorAssign")
public class AuthorAssign extends AssignableProperty
{
    //@Required
    @OneToOne
    public Author author;

    public AuthorAssign(User assigner, Resource resource, Author author) {
        super(assigner, resource);
        this.author = author;
    }

    public String assignedProperty()
    {
        return author.author;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        AuthorVote previousVote = AuthorVote.find("byAuthorAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new AuthorVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  AuthorVote.find("byAuthorAssign", this).fetch();
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
            List<AuthorVote> votes = AuthorVote.find("byVoterAndAuthorAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<AuthorVote> votes = AuthorVote.find("byVoterAndAuthorAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
