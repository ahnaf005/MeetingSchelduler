package meetingscheduler;

import java.io.Serializable;
import java.util.ArrayList;

public class UserNumbers implements Serializable{
    ArrayList<String>usernames;
    ArrayList<Integer>number;
    UserNumbers(ArrayList<String>usernames,ArrayList<Integer>number)
    {
        this.usernames=usernames;
        this.number=number;
    }
    public void print()
    {
        System.out.println("Usernames:");
        for(int i=0;i<usernames.size();i++)
        {
            System.out.println(usernames.get(i));
        }
        System.out.println("Numbers:");
        for(int i=0;i<number.size();i++)
        {
            System.out.println(number.get(i));
        }
    }
    
}
