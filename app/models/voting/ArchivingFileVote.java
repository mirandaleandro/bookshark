package models.voting;

import models.User;
import models.Vote;
import models.assignment.ArchivingFileAssign;
import models.resourseproperties.ArchivingFile;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 14/01/12
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="ArchivingFileVote")
public class ArchivingFileVote extends Vote
{
    //@Required
    @OneToOne
    public ArchivingFileAssign fileAssign;

    public ArchivingFileVote(ArchivingFileAssign fileAssign,  boolean upVote, User voter) {
        super(upVote, voter);
        this.fileAssign = fileAssign;
    }


}
