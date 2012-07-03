package models;

import models.assignment.*;
import models.resourseproperties.Author;
import models.voting.TitleVote;
import play.db.jpa.Model;
import play.mvc.Router;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.File;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 31/12/11
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
@Table(name="Resource")
public class Resource extends Model
{

    public static final int MOST_RELEVANT_AUTHORS_SIZE = 3 ;

    //@Required
    @OneToOne
    public User creator;

    //@Required
    public Date creationDate;

    @OneToMany(mappedBy="resource")
    public List<ArchivingFileAssign> files = new Vector<ArchivingFileAssign>();

    //DC data
    public String format;                                   //dc.format

    @OneToMany(mappedBy="resource")
    public List<DescriptionAssign> descriptions = new Vector<DescriptionAssign>();            //dc.description

    @OneToMany
    public List<User> contributors = new Vector<User>();          //dc.contributor

    @OneToMany(mappedBy="resource")
    public List<AuthorAssign> authors = new Vector<AuthorAssign>();             //dc.creator

    @OneToMany(mappedBy="resource")
    public List<PublishingDateAssign> dates = new Vector<PublishingDateAssign>();       //dc.date

    public String identifier;                                          //dc.identifier

    @OneToMany(mappedBy="resource")
    public List<LanguageAssign> languages = new Vector<LanguageAssign>();         //dc.language

    @OneToMany(mappedBy="resource")
    public List<PublisherAssign> publishers = new Vector<PublisherAssign>();       //dc.publisher

    @OneToMany(mappedBy="resource")
    public List<SubjectAssign> subjects = new Vector<SubjectAssign>();           //dc. subject  ->categories

    @OneToMany(mappedBy="resource")
    public List<TitleAssign> titles = new Vector<TitleAssign>();               //dc.title



    public Resource()
    {
        this.creationDate = new Date();
    }

    /*public List<DescriptionAssign> historyDescriptions()
    {
        List<DescriptionAssign> historyDescriptions =
    } */
    public String mostRelevantTitle()
    {
        long maxVotes = -1;

        String titleValue = String.format("Content with undefined title");

        if (titles != null && titles.size() > 0)
        {

            for (TitleAssign title : titles)
            {
                long titleVotes = title.votingResults();

                if ( maxVotes < titleVotes )
                {
                    maxVotes = titleVotes;
                    titleValue = title.assignedProperty();
                }
            }
        }

        return titleValue;
    }
    
    public String link()
    {
        Hashtable params = new Hashtable();
        params.put("id", this.id);
        String url = Router.reverse("Voting.voteResource",params).url;

        return url;
    }

    public List<AuthorAssign> mostRelevantAuthors()
    {
        List<AuthorAssign> authorsAssign = new ArrayList<AuthorAssign>(MOST_RELEVANT_AUTHORS_SIZE);

        if (authors == null || this.authors.size() <= 0)
        {
            return authorsAssign;
        }

        List<AuthorAssign> allAuthors = new ArrayList<AuthorAssign>(this.authors.size());
        allAuthors.addAll(this.authors);

        for(int i = 0; i < MOST_RELEVANT_AUTHORS_SIZE; i++ )
        {
            AuthorAssign aAssign = mostRelevantAuthorInList(allAuthors);
            if(aAssign != null)
            {
                authorsAssign.add(aAssign);
                allAuthors.remove(aAssign);
            }
        }

        return authorsAssign;
    }



    public AuthorAssign mostRelevantAuthorInList(List<AuthorAssign> authors)
    {
        if ( authors == null || authors.size() <= 0 )
        {
            return null;
        }

        long maxVotes = -1;

        AuthorAssign mostVoted = null;

            for (AuthorAssign aProp : authors)
            {
                long votes = aProp.votingResults();

                if ( maxVotes < votes )
                {
                    maxVotes = votes;
                    mostVoted = aProp;

                }
            }

        return mostVoted;
    }
}
