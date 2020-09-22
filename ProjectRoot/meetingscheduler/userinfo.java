package meetingscheduler;
import java.io.Serializable;
public class userinfo implements Serializable {
    public String username,password;
    userinfo(String username,String password)
    {
        this.username=username;
        this.password=password;
    }

    @Override
    public String toString() {
        return "userinfo{" + "username=" + username + ", password=" + password + '}';
    }
    
}
