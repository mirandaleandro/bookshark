package models;

import models.voting.ArchivingFileVote;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 15/01/12
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AssignableProperty extends Model implements Votable
{
    //@Required
    @OneToOne
    public User assigner;

    //@Required
    @ManyToOne
    public Resource resource;

    @Temporal(TemporalType.TIMESTAMP)
    public Date creationDate;

    public AssignableProperty(User assigner, Resource resource) {
        this.assigner = assigner;
        this.resource = resource;
        this.creationDate = new Date();
    }


}
