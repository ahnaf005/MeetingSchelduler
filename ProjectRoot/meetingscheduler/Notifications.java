package meetingscheduler;
import java.io.Serializable;
public class Notifications implements Serializable{
    meeting ob;
    String type;
    String status;
    Notifications(meeting ob,String type,String status)
    {
        this.ob=ob;
        this.type=type;
        this.status=status;
    }
    @Override
    public String toString() {
        return "Notifications{" + "ob=" + ob + ", type=" + type + ", status=" + status + '}';
    }
}
