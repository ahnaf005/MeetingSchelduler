package meetingscheduler;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sun.util.locale.StringTokenIterator;

public class DetailsController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private TextField name;
    @FXML
    private TextField personame;
    @FXML
    private TextField location;
    @FXML
    private TextField date;
    @FXML
    private TextField starttime;
    @FXML
    private TextField endtime;
    @FXML
    private TextArea description;

    @FXML
    public void backclicked() {
        int idx = 0, size = 0;
        if (MeetingScheduler.variable == 1) {
            size = MeetingScheduler.receive.ob.meetingrequestto.size();
            MeetingScheduler.variable=0;
            MeetingScheduler.sent = new SentRequest(size);
            MeetingScheduler.sent.start();
        }
        else if(MeetingScheduler.variable==2)
        {
            size = MeetingScheduler.receive.ob.meetingrequestfrom.size();
            MeetingScheduler.variable=0;
            MeetingScheduler.recreq = new Received(size);
            MeetingScheduler.recreq.start();
        }
        MeetingScheduler.detail.close();
    }

    String limit(int n) {
        if (n == 1) {
            return "Jan";
        } else if (n == 2) {
            return "Feb";
        } else if (n == 3) {
            return "Mar";
        } else if (n == 4) {
            return "Apr";
        } else if (n == 5) {
            return "May";
        } else if (n == 6) {
            return "Jun";
        } else if (n == 7) {
            return "Jul";
        } else if (n == 8) {
            return "Aug";
        } else if (n == 9) {
            return "Sep";
        } else if (n == 10) {
            return "Oct";
        } else if (n == 11) {
            return "Nov";
        } else if (n == 12) {
            return "Dec";
        }
        return "jan";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(MeetingScheduler.detail.name);
        personame.setText(MeetingScheduler.detail.personname);
        description.setText(MeetingScheduler.detail.description);
        location.setText(MeetingScheduler.detail.location);
        StringTokenizer st = new StringTokenizer(MeetingScheduler.detail.time);
        starttime.setText(st.nextToken("-"));
        endtime.setText(st.nextToken("-"));
        StringTokenizer st1 = new StringTokenizer(MeetingScheduler.detail.date);
        String day = st1.nextToken("/");
        String month = limit(Integer.parseInt(st1.nextToken("/")));
        String year = st1.nextToken("/");
        date.setText(month + " " + day + "," + year);
    }

}
