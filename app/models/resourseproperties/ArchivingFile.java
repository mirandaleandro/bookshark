package models.resourseproperties;

import models.FolksonomyProperty;
import models.Resource;
import models.User;
import models.Vote;
import models.assignment.ArchivingFileAssign;
import models.assignment.AuthorAssign;
import models.voting.ArchivingFileVote;
import org.apache.commons.io.FileUtils;
import play.Play;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 09/01/12
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="ArchivingFile")
public class ArchivingFile extends FolksonomyProperty
{
    public static String ARCHIVING_FILE_PATH = "/home/leandro/Dropbox/TCC/bookshark/files/";
    public static String ARCHIVING_FILE_DIRECTORY_NAME = "/files/";
    
    //@Required
    public String filepath;
    
    public String format;                                   //dc.format
    
    public boolean savedSuccessfully;

    public ArchivingFile(File file, User creator, Date date)
    {
        super(creator);
        this.savedSuccessfully = this.saveFile( file, date );

    }

    public boolean isAssignedToResource(Resource resource)
    {
        ArchivingFileAssign assign = ArchivingFileAssign.find("byFileAndResource",this,resource).first();
        return assign != null;
    }

    public static String fileExtension(File file)
    {
        String extension = null;

        if (file != null)
        {
            String path = file.getPath();

            int dotPos = path.lastIndexOf(".");

            extension = path.substring(dotPos);
        }

        return extension;
    }

    public boolean saveFile(File file, Date date)
    {
        boolean  ans = false;
        StringBuilder destinationFilePath = new StringBuilder(ARCHIVING_FILE_PATH);
        destinationFilePath.append(date.getTime());
        destinationFilePath.append("/");
        destinationFilePath.append(file.getName());
        
        StringBuilder tempFilePath = new StringBuilder(ARCHIVING_FILE_DIRECTORY_NAME);
        tempFilePath.append(date.getTime());
        tempFilePath.append("/");
        tempFilePath.append(file.getName());
        
        try
        {
            File destination = new File(destinationFilePath.toString());
            FileUtils.copyFile(file, destination);
            //verificar se o arquivo foi realmente foi copiado
            this.filepath = tempFilePath.toString();
            this.format = ArchivingFile.fileExtension(file);

            ans = true;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return ans;
    }
    
    public static String fileStoragePath()
    {
        File file = Play.applicationPath;
        return file.getAbsolutePath().concat(ARCHIVING_FILE_DIRECTORY_NAME);
    }
}
