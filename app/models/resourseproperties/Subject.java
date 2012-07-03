package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.assignment.SubjectAssign;
import play.data.validation.Required;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Subject")
public class Subject extends FolksonomyProperty
{
    //@Required
    public String subject;

    @OneToOne
    public Subject parent;

    public Subject(String subject, User user)
    {
        super(user);
        this.subject = subject;
    }

    public Subject (String subject, User user,Subject parent)
    {
        this(subject,user);
        this.parent = parent;
    }

    public boolean isAssignedToResource(Resource resource)
    {
        SubjectAssign assign = SubjectAssign.find("bySubjectAndResource",this,resource).first();
        return assign != null;
    }
    
    public String toString()
    {
        return subject;
    }

    public List<Subject> children()
    {
        List<Subject> children = Subject.find("byParent",this).fetch();
        return children;
    }
}
