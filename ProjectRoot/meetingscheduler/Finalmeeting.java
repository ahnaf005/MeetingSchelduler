package meetingscheduler;

import java.io.Serializable;

public class Finalmeeting implements Serializable{
    meeting ob;
    String reminder;
    Finalmeeting(meeting ob,String reminder)
    {
        this.ob=ob;
        this.reminder=reminder;
    }
    public boolean equals(Finalmeeting Ob)
    {
        if(ob.equals(Ob.ob))
        {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Finalmeeting{" + "ob=" + ob + ", reminder=" + reminder + '}';
    }
    
}
