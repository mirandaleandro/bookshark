package controllers;

import models.Resource;
import models.User;
import models.assignment.*;
import models.resourseproperties.*;
import models.utils.HashMaker;
import models.voting.ArchivingFileVote;
import play.mvc.Router;

import java.io.File;
import java.util.*;


public class ContentInception extends Application
{

    public static void index()
    {
        Application.render();
    }


    public static void authorsList(String term)
    {
        System.out.println("Filtering authors by term: ".concat(term));

        List<Author> authors = Author.find("byAuthorLike","%"+term+"%").fetch();

        List<String> response = new ArrayList<String>();
        
        for(int i = 0; response.size() < 10 && i < authors.size() ; i++)
        {
            Author author = authors.get(i);

            if( !response.contains(author.author) )
            {
                response.add(author.author);
            }

        }

        renderJSON(response);
    }

    public static void subjectsList(String term)
    {
        System.out.println("Filtering subjects by term: ".concat(term));

        List<Subject> subjects = Subject.find("bySubjectLike","%"+term+"%").fetch();

        final List<String> response = new ArrayList<String>();

        for(int i = 0; response.size() < 10 && i < subjects.size() ; i++)
        {
            Subject subject = subjects.get(i);

            if( !response.contains(subject.subject) )
            {
                response.add(subject.subject);
            }
        }

        renderJSON(response);
    }

    public static void publisherNameList(String term)
    {
        System.out.println("Filtering publishers name by term: ".concat(term));

        List<Publisher> publishers = Publisher.find("byPublisherLike","%"+term+"%").fetch();

        final List<String> response = new ArrayList<String>();

        for(Publisher publisher: publishers)
        {
            response.add(publisher.publisher);
        }

        renderJSON(response);
    }

    public static void publisherCityList(String term)
    {
        System.out.println("Filtering publishers city by term: ".concat(term));

        List<Publisher> publishers = Publisher.find("byCityLike","%"+term+"%").fetch();

        final List<String> response = new ArrayList<String>();

        for(Publisher publisher: publishers)
        {
            response.add(publisher.city);
        }

        renderJSON(response);
    }

    public static void languagesList(String term)
    {
        System.out.println("Filtering languages city by term: ".concat(term));

        List<Language> languages = Language.find("byLanguageLike","%"+term+"%").fetch();

        final List<String> response = new ArrayList<String>();

        for(Language language: languages)
        {
            response.add(language.language);
        }

        renderJSON(response);
    }
    


    public static boolean userMayVote(User user, boolean isVoteup)
    {
        boolean mayVote;

        if(isVoteup)
        {
            mayVote = user.isEnthusiast();
        }
        else
        {
            mayVote = user.isInspector();
        }

        return mayVote;
    }
    
    public static void uploadResource(File file)
    {


        User creator = connected();

        validation.required(file);
        validation.required(creator);

        if(validation.hasErrors())
        {
            System.out.println("upload fail");
            render("@index");
        }

        Resource resource = new Resource();
        resource.save();
        resource.creationDate = new Date();
        resource.creator = creator;

        ArchivingFile archivingFile = new ArchivingFile(file, creator, resource.creationDate);

        if(archivingFile.filepath ==  null)
        {
            System.out.println("Could not save file");
            Application.index();
        }

        String hash = null;
        try {
            hash = HashMaker.generateHashFromFile(file);
        } catch (Exception e) {
            System.out.println("Problems with the file");
            Application.index();
        }

        if (hash == null)
        {
            System.out.println("Problems with the file");
            Application.index();
            return;
        }
        archivingFile.save();

        resource.identifier = hash;

        ArchivingFileAssign assign = new ArchivingFileAssign(creator,resource,archivingFile).save();
        resource.files.add(assign);

        creator.hasUploaded();
        creator.save();
        resource.save();

        Voting.voteResource(resource.id);

    }

    /* insertMethods */

    public static Title insertTitle(String newValue,User creator)
    {

        Title value = Title.find("byTitle",newValue).first();

        if(value == null)
        {
            value = new Title(newValue,creator).save();
        }

        return value;
    }

    public static Language insertLanguage(String newValue,User creator)
    {
        Language value = Language.find("byLanguage",newValue).first();

        if(value == null)
        {
            if( !creator.isEditor() )
            {
                displayError("You cannot add new Languages. Earn more respect by uploading data and being up voted");
            }
            value = new Language(newValue,creator).save();
        }

        return value;
    }

    public static PublishingDate insertPublishingDate(Integer year, Integer month,User creator)
    {
        //Date dateValue = dateWithYearAndMonth(year,month);
        PublishingDate value = PublishingDate.find("byYearAndMonth",year,month).first();

        if(value == null)
        {

             value = new PublishingDate(year,month,creator).save();
        }

        return value;
    }

    public static Publisher insertPublisher(String publisher, String city,User creator)
    {
        Publisher value = Publisher.find("byPublisherAndCity", publisher, city).first();

        if(value == null)
        {
            if( !creator.isEditor() )
            {
                displayError("You cannot add new Publishers. Earn more respect by uploading data and being up voted");
            }
            value = new Publisher(publisher,city,creator).save();
        }

        return value;
    }

    public static Description insertDescription(String newValue,User creator)
    {
        Description value = Description.find("byDescription",newValue).first();

        if(value == null)
        {
            value = new Description(newValue,creator).save();
        }

        return value;
    }
    
    public static Author insertAuthor(String name,User creator)
    {
        Author theAuthor = Author.find("byAuthor",name).first();

        if(theAuthor == null)
        {
            if( !creator.isReporter() )
            {
                displayError("You cannot add new Authors. Earn more respect by uploading data and being up voted");
            }
            theAuthor = new Author(name,creator).save();
        }

        return theAuthor;
    }

    public static Subject insertSubject(String subject,User creator)
    {
        Subject subjectInstance = Subject.find("bySubject",subject).first();

        if(subjectInstance == null)
        {
            if( !creator.isEditor() )
            {
                displayError("You cannot add new Subjects. Earn more respect by uploading data and being up voted");
            }
            subjectInstance = new Subject(subject,creator).save();
        }

        return subjectInstance;
    }

    /* end insertMethods */

    /* assign methods */
    public static boolean assignTitle(String value,Resource resource, User assigner)
    {
        Title valueInstance = insertTitle( value, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            TitleAssign valueAssign = new TitleAssign(assigner, resource, valueInstance).save();

            resource.titles.add(valueAssign);

            resource.save();

            return true;
        }

        return false;
    }

    public static boolean assignDescription(String value, Resource resource, User assigner, DescriptionAssign lastAssign)
    {
        Description valueInstance = insertDescription( value, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            DescriptionAssign valueAssign = new DescriptionAssign(assigner, resource, valueInstance,true).save();

            if(lastAssign != null)
            {
                lastAssign.isCurrent = false;
                lastAssign.save();
            }
          //  resource.descriptions.add(valueAssign);

         //   resource.save();

            return true;
        }

        return false;
    }

    public static boolean assignAuthor(String value,Resource resource, User assigner)
    {
        Author valueInstance = insertAuthor( value, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            AuthorAssign valueAssign = new AuthorAssign(assigner, resource, valueInstance).save();

            resource.authors.add(valueAssign);

            resource.save();

            return true;
        }

        return false;
    }

    public static boolean assignSubject(String value,Resource resource, User assigner)
    {
        Subject valueInstance = insertSubject( value, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            SubjectAssign valueAssign = new SubjectAssign(assigner, resource, valueInstance).save();

            resource.subjects.add(valueAssign);

            resource.save();

            return true;
        }

        return false;
    }

    public static boolean assignPublishingDate(Integer year, Integer month, Resource resource, User assigner)
    {
        PublishingDate valueInstance = insertPublishingDate( year, month, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            PublishingDateAssign valueAssign = new PublishingDateAssign(assigner, resource, valueInstance).save();

            resource.dates.add(valueAssign);

            resource.save();

            return true;
        }

        return false;
    }

    public static boolean assignLanguage(String value,Resource resource, User assigner)
    {
        Language valueInstance = insertLanguage( value, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            LanguageAssign valueAssign = new LanguageAssign(assigner, resource, valueInstance).save();

            resource.languages.add(valueAssign);

            resource.save();

            return true;
        }

        return false;
    }

    public static boolean assignPublisher(String publisher, String city,Resource resource, User assigner)
    {
        Publisher valueInstance = insertPublisher( publisher,city, assigner );

        if ( ! valueInstance.isAssignedToResource(resource) )
        {
            PublisherAssign valueAssign = new PublisherAssign(assigner, resource, valueInstance).save();

            resource.publishers.add(valueAssign);

            resource.save();

            return true;
        }

        return false;
    }
    /* end assign methods */

    // add content
    public static void addFile(Long resourceId, Long assignerId, File file)
    {
        User assigner = connected();
        Resource resource = Resource.findById(resourceId);
       // User assigner = User.findById(assignerId);

        validation.required(resource);
        validation.required(file);
        validation.required(assigner);
        
        if(validation.hasErrors())
        {
            System.out.println("Errors on adding files");
            render(resource);
        }

        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        ArchivingFile archivingFile = new ArchivingFile(file, assigner, new Date()).save();

        if(archivingFile.filepath ==  null)
        {
            System.out.println("Could not save file");
            Application.index();
        }
        
        archivingFile.save();

        ArchivingFileAssign assign = new ArchivingFileAssign(assigner,resource,archivingFile).save();
      
        resource.files.add(assign);
        
        resource.save();
        
        render(resource);
    }

    public static void addTitle(Long resourceId, String value)
    {
        User assigner = connected();
        System.out.println("Add title");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(value);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on adding title");
            render(resource);
        }

        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        if ( ! assignTitle(value,resource,assigner) )
        {
            displayError("property already assign");
            System.out.println("property already assign");
        }

        render(resource);
    }

    public static void addSubject(Long resourceId, String value)
    {
        User assigner = connected();
        System.out.println("Add subject");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(value);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on subject");
            render(resource);
        }
        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        if(!assignSubject(value,resource,assigner) )
        {
            displayError("property already assign");
            System.out.println("property already assign");
        }

        render(resource);
    }

    public static void addPublishingDate(Long resourceId, Integer year, Integer month)
    {
        User assigner = connected();
        System.out.println("Add publishing date");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(year);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on publishing dates");
            render(resource);
        }

        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        if( !assignPublishingDate(year, month, resource, assigner) )
        {
            displayError("property already assign");
            System.out.println("property already assigned");
        }

        render(resource);
    }
    
    public static void addPublisher(Long resourceId, String publisherName, String publisherCity)
    {
        User assigner = connected();
        System.out.println("Add publisher");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(publisherName);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on adding publisher");
            render(resource);
        }

        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        if(!assignPublisher(publisherName,publisherCity,resource,assigner) )
        {
            displayError("property already assign");
            System.out.println("property already assign");
        }

        render(resource);
    }

    public static void addLanguage(Long resourceId, String value)
    {
        User assigner = connected();
        System.out.println("Add language");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(value);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on adding language");
            render(resource);
        }

        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        if(!assignLanguage(value,resource,assigner) )
        {
            displayError("property already assign");
            System.out.println("property already assign");
        }

        render(resource);
    }

    public static void addDescription(Long resourceId, String description, Long lastDescriptionAssignId)
    {
        User assigner = connected();
        System.out.println("Add description");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(description);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on description");
            render(resource);
        }

        if(!resource.creator.id.equals(assigner.id) && !assigner.isEditor())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }

        DescriptionAssign lastAssign = null;
        if(lastDescriptionAssignId != null) {
            lastAssign = DescriptionAssign.findById(lastDescriptionAssignId);
        }


        if(!assignDescription(description,resource,assigner,lastAssign) )
        {
            displayError("property already assign");
            System.out.println("property already assign");
        }
       // resource = Resource.findById(resourceId);

        render(resource);
    }

    public static void addAuthor(Long resourceId, String value)
    {
        User assigner = connected();
        System.out.println("Add author");
        Resource resource = Resource.findById(resourceId);

        validation.required(resource);
        validation.required(value);
        validation.required(assigner);

        if(validation.hasErrors())
        {
            System.out.println("Errors on adding author");
            render(resource);
        }
        if(!resource.creator.id.equals(assigner.id) && !assigner.isReporter())
        {
            displayError("Sorry, you have not enough respect to add new content");
        }


        if (!assignAuthor(value, resource, assigner)) {
            displayError("property already assign");
            System.out.println("property already assign");
        }


        render(resource);
    }

    public static boolean restoreDescriptionOnDatabase(DescriptionAssign toRestore)
    {
        DescriptionAssign currentDescription = null;//DescriptionAssign.find("byIsCurrent",true).first();
        if (toRestore != null) {
            for (DescriptionAssign da : toRestore.resource.descriptions) {
                if (da.isCurrent)
                {
                    da.isCurrent = false;
                    da.save();
                }
            }

            toRestore.isCurrent = true;
            toRestore.save();

            return true;

        }
        return false;
    }
    
    public static void restoreDescription( Long toRestoreDescriptionId)
    {
        validation.required(toRestoreDescriptionId);

        if(validation.hasErrors())
        {
            System.out.println("Errors on restoring description");
            displayError("Sorry, errors on restoring description");

        }

        User user = connected();

        if(! user.isEditor())
        {
            displayError("Sorry, editor reputation needed.");
        }

        DescriptionAssign toRestoreDescription = DescriptionAssign.findById(toRestoreDescriptionId);

        if( ! restoreDescriptionOnDatabase(toRestoreDescription) )
        {
            System.out.println("Errors on restoring description");
            Application.index();
        }
        
        Resource resource = toRestoreDescription.resource;// Resource.findById(toRestoreDescription.resource.id);
        render(resource);
    }

    //end add content

}
