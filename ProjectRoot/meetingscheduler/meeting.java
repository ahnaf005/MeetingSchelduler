package meetingscheduler;
import java.io.Serializable;
public class meeting implements Serializable{
    String meetername,date,time,meetingname,location,description;
    meeting(String meetername,String date,String time,String meetingname,String location,String description)
    {
        this.meetername=meetername;
        this.date=date;
        this.time=time;
        this.meetingname=meetingname;
        this.location=location;
        this.description=description;
    }
    boolean equals(meeting ob)
    {
        if(meetername.equals(ob.meetername) && date.equals(ob.date) && time.equals(ob.time) && meetingname.equals(ob.meetingname) && location.equals(ob.location) && description.equals(ob.description))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "meeting{" + "meetername=" + meetername + ", date=" + date + ", time=" + time + ", meetingname=" + meetingname + ", location=" + location + ", description=" + description + '}';
    }
    
}
