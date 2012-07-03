package controllers;

import models.Resource;
import models.User;
import models.assignment.*;


public class Voting extends ContentInception
{

    public static void voteResource(Long id)
    {
        validation.required(id);
        if(validation.hasErrors())
        {
            render();
        }
        Resource resource = Resource.findById(id);

        render(resource);
    }

    public static void voteFile(Long fileAssignid , Boolean isVoteup )
    {

        //TODO debug votes here
        User voter = connected();

        ArchivingFileAssign fileAssign = ArchivingFileAssign.findById(fileAssignid);

        validation.required(fileAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(fileAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }

                if ( userMayVote(voter,isVoteup) )
                {
                    fileAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                    displayError("Sorry, your reputation is not enough for voting yet");
                }

            }else
            {
                System.out.println("Could not find user");
                displayError("Sorry, could not process your vote");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
            displayError("Sorry, could not process your vote");
        }

        Resource resource = fileAssign.resource ;

        render(resource);
    }

    public static void voteTitle(Long valueAssignid , Boolean isVoteup )
    {
        User voter = connected();

        TitleAssign valueAssign = TitleAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }
                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }

    public static void voteLanguage(Long valueAssignid , Boolean isVoteup )
    {
        User voter = connected();

        LanguageAssign valueAssign = LanguageAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {

                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }

                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }

    public static void voteAuthor(Long valueAssignid , Boolean isVoteup )
    {
        User voter = connected();

        AuthorAssign valueAssign = AuthorAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }

                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }

    public static void voteSubject(Long valueAssignid , Boolean isVoteup )
    {
        User voter = connected();

        SubjectAssign valueAssign = SubjectAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }
                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }

    public static void votePublishingDate(Long valueAssignid, Boolean isVoteup)
    {
        User voter = connected();

        PublishingDateAssign valueAssign = PublishingDateAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }
                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }

    public static void votePublisher(Long valueAssignid , Boolean isVoteup )
    {
        User voter = connected();

        PublisherAssign valueAssign = PublisherAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }

                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }

    public static void voteDescription(Long valueAssignid , Boolean isVoteup )
    {
        User voter = connected();

        DescriptionAssign valueAssign = DescriptionAssign.findById(valueAssignid);

        validation.required(valueAssign);
        validation.required(voter);
        validation.required(isVoteup);

        if (! validation.hasErrors() )
        {
            if(voter != null)
            {
                if (voter.id.equals(valueAssign.assigner.id) )
                {
                    System.out.println("Cannot vote your own assignment");
                    displayError("Cannot vote your own assignment");
                }

                if ( userMayVote(voter,isVoteup) )
                {
                    valueAssign.vote(isVoteup,voter);
                }else
                {
                    System.out.println("User has not enough points to vote");
                }

            }else
            {
                System.out.println("Could not find user");
            }
        }
        else
        {
            System.out.println("Vote file may not be completed");
        }

        Resource resource = valueAssign.resource ;

        render(resource);
    }
}
