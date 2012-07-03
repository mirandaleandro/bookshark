package models;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Johnny
 * Date: 08/01/12
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public interface Votable 
{
    public void vote(boolean isVoteUp, User voter);

    //called from the UI
    public long votingResults();

    //called from the UI
    public boolean isVotedUp(User user);

    //called from the UI
    public boolean isVotedDown(User user);

}
