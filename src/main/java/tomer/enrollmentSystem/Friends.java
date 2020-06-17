package tomer.enrollmentSystem;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represent the friendship relation between pupils
 */
@Service
public class Friends {
    private HashMap<Long ,ArrayList<Long>> friendsList = new HashMap<>();

    Friends(){}

    /**
     * adds secId as friend of firstId
     * @param firstId
     * @param secId
     */
    public void addFriend(long firstId, long secId)
    {
        ArrayList<Long> curFriendList = this.friendsList.get(firstId);
        if (curFriendList == null)
        {
            curFriendList = new ArrayList<Long>();
        }
        curFriendList.add(secId);
        this.friendsList.put(firstId,curFriendList);
    }

    public ArrayList<Long> getFriends(long id)
    {
        return this.friendsList.get(id);
    }

}
