package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.Subject;
import models.voting.SubjectVote;
import models.voting.TitleVote;
import play.mvc.Router;

import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="SubjectAssign")
public class SubjectAssign extends AssignableProperty
{
    //@Required
    @OneToOne
    public Subject subject;

    public SubjectAssign(User assigner, Resource resource, Subject subject)
    {
        super(assigner, resource);
        this.subject = subject;
    }

    public String assignedProperty()
    {
        return subject.subject;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        SubjectVote previousVote = SubjectVote.find("bySubjectAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        new SubjectVote(isVoteUp, voter,this).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  SubjectVote.find("bySubjectAssign", this).fetch();
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
            List<SubjectVote> votes = SubjectVote.find("byVoterAndSubjectAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<SubjectVote> votes = SubjectVote.find("byVoterAndSubjectAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //Adding new value url
    
    public String addAction()
    {
        return Router.getFullUrl("ContentInception.addAuthor");
    }

}
