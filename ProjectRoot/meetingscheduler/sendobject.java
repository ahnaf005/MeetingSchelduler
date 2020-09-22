package meetingscheduler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
public class sendobject implements Serializable{
    sendobject ob;
    public String type;
    public String username;
    public ArrayList<Finalmeeting>meetinglist;
    public ArrayList<meeting>meetingrequestfrom;
    public ArrayList<meeting>meetingrequestto;
    public ArrayList<meeting>history;
    public ArrayList<Notifications>notification;
    public UserNumbers usernumbers;
//    sendobject(String username,ArrayList<Finalmeeting>meetinglist,ArrayList<meeting>meetingrequestfrom,ArrayList<meeting>meetingrequestto,ArrayList<meeting>history,ArrayList<Notifications>notification)
//    {
//        this.username=username;
//        this.meetinglist=meetinglist;
//        this.meetingrequestfrom=meetingrequestfrom;
//        this.meetingrequestto=meetingrequestto;
//        this.history=history;
//        this.notification=notification;
//    }
    sendobject(String username,UserNumbers usernumbers,ArrayList<Finalmeeting>meetinglist,ArrayList<meeting>meetingrequestfrom,ArrayList<meeting>meetingrequestto,ArrayList<meeting>history,ArrayList<Notifications>notification)
    {
        this.username=username;
        this.usernumbers=usernumbers;
        this.meetinglist=meetinglist;
        this.meetingrequestfrom=meetingrequestfrom;
        this.meetingrequestto=meetingrequestto;
        this.history=history;
        this.notification=notification;
    }
    sendobject(String type)
    {
        this.type=type;
    }
    sendobject(String type,sendobject ob)
    {
        this.type=type;
        this.ob=ob;
    }
//    sendobject(String type,String username,ArrayList<meeting>meetinglist,ArrayList<meeting>meetingrequestfrom,ArrayList<meeting>meetingrequestto)
//    {
//        this.type=type;
//        this.username=username;
//        this.meetinglist=meetinglist;
//        this.meetingrequestfrom=meetingrequestfrom;
//        this.meetingrequestto=meetingrequestto;
//        
//    }
    public void writeObject(ObjectOutputStream s) throws IOException
    {
        s.writeUTF(type);
        s.writeUTF(username);
        s.writeObject(meetinglist);
        s.writeObject(meetingrequestfrom);
        s.writeObject(meetingrequestto);
        
    }
    public void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        type=s.readUTF();
        username=s.readUTF();
        meetinglist=(ArrayList<Finalmeeting>) s.readObject();
        meetingrequestfrom=(ArrayList<meeting>) s.readObject();
        meetingrequestto=(ArrayList<meeting>) s.readObject();
    }
    public void print()
    {
        System.out.print("username:");
        System.out.println(username);
        System.out.println("meetinglist:");
        for(int i=0;i<meetinglist.size();i++)
        {
            System.out.println(meetinglist.get(i));
        }
        System.out.println("meetingrequestfrom:");
        for(int i=0;i<meetingrequestfrom.size();i++)
        {
            System.out.println(meetingrequestfrom.get(i));
        }
        System.out.println("meetingrequestto:");
        for(int i=0;i<meetingrequestto.size();i++)
        {
            System.out.println(meetingrequestto.get(i));
        }
        for(int i=0;i<history.size();i++)
        {
            System.out.println(history.get(i));
        }
        for(int i=0;i<notification.size();i++)
        {
            System.out.println(notification.get(i));
        }
    }
}
