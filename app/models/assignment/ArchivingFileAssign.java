package models.assignment;

import models.AssignableProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.resourseproperties.ArchivingFile;
import models.voting.ArchivingFileVote;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.net.URI;
import java.net.URL;
import java.util.List;


@Entity
@Table(name="ArchivingFileAssign")
public class ArchivingFileAssign extends AssignableProperty
{
    @OneToOne
    public ArchivingFile file;


    public ArchivingFileAssign(User assigner, Resource resource, ArchivingFile file)
    {
        super(assigner, resource);
        this.file = file;
    }

    public URL parseUrl(String s) throws Exception {
        URL u = new URL(s);
        return new URI(
                u.getProtocol(),
                u.getAuthority(),
                u.getPath(),
                u.getQuery(),
                u.getRef()).
                toURL();
    }


    public String link(String host)
    {
       String path;

        try {
            path = this.parseUrl(host.concat(file.filepath)).toString();
        } catch (Exception e) {
            return "Not found";
        }

        return path;
    }

    public void vote(boolean isVoteUp, User voter)
    {
        ArchivingFileVote previousVote = ArchivingFileVote.find("byFileAssignAndVoter",this,voter).first();
        if(previousVote != null)
        {
            previousVote.delete();
        }

        this.assigner.vote(isVoteUp);

        ArchivingFileVote vote = new ArchivingFileVote(this, isVoteUp, voter).save();

    }

    //called from the UI
    public long votingResults()
    {
        long votingResults = 0;
        List<Vote> votes =  ArchivingFileVote.find("byFileAssign", this).fetch();
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
            List<ArchivingFileVote> votes = ArchivingFileVote.find("byVoterAndFileAssignAndUpVote",user,this,true).fetch();

            return votes.size() > 0;
        }

        return false;
    }

    //called from the UI
    public boolean isVotedDown(User user)
    {
        if(user != null)
        {
            List<ArchivingFileVote> votes = ArchivingFileVote.find("byVoterAndFileAssignAndUpVote",user,this,false).fetch();

            return votes.size() > 0;
        }

        return false;
    }
}
