package controllers;

import models.Resource;
import models.assignment.SubjectAssign;
import models.resourseproperties.Subject;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class Search extends Application
{
    public static void search(String searchTerm, Long subjectid )
    {       
        List<Resource> resources = searchAndApplyFilters( searchTerm,  subjectid );
                              
        List<Subject> subjects = subjects();

        render(resources,subjects,searchTerm);
    }
    
    public static List<Resource> searchAndApplyFilters(String searchTerm, Long subjectid )
    {
        List<Resource> resources = searchByTitle(searchTerm);

        if(subjectid != null)
        {
            Subject subject = Subject.findById(subjectid);

            applySubjectFilter(resources,subject);
        }
        return resources;
    }

    public static List<Subject> subjects( )
    {
        return Subject.find("order by subject").fetch();
    }

    public static List<Resource> applySubjectFilter(List<Resource> resources, Subject subject)
    {
        List<Resource> filteredValues = new ArrayList<Resource>(resources.size());

        if(subject != null)
        {
              for(Resource resource: resources)
              {
                  for(SubjectAssign sAssign: resource.subjects)
                  {
                      if( sAssign.subject.id.equals(subject.id))
                      {
                          filteredValues.add(resource);
                      }
                  }
              }
            resources.retainAll(filteredValues);
        }
        return resources;
    }

    public static List<Resource> searchByTitle(String searchTerm)
    {
        return Resource.find("SELECT distinct r FROM Resource r, TitleAssign ta, Title t WHERE ta.resource = r AND ta.title=t AND t.title like ?","%"+searchTerm+"%").fetch();
    }
}



