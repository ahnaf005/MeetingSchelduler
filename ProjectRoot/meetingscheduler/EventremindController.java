/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meetingscheduler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sun.util.locale.StringTokenIterator;

public class EventremindController implements Initializable {

    ObservableList<String> remindtime;
    @FXML
    private ComboBox<String> reminder;
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
        String remindtimer = reminder.getValue();
        MeetingScheduler.receive.ob.meetinglist.get(MeetingScheduler.event.idx2).reminder = remindtimer;
        String sendmsg = "Type:" + "ChangeRemind" + '\n' + "Username:" + MeetingScheduler.username
                + '\n' + "meetdatetime:" + MeetingScheduler.event.Ob.ob.meetername + '$' + MeetingScheduler.event.Ob.ob.date + '$'
                + MeetingScheduler.event.Ob.ob.time + '$'
                + MeetingScheduler.event.Ob.ob.meetingname + '$' + MeetingScheduler.event.Ob.ob.location + '$'
                + MeetingScheduler.event.Ob.ob.description + '$'
                + remindtimer;
        new Clientsend(MeetingScheduler.nc,sendmsg);
        if(MeetingScheduler.val==1){
            MeetingScheduler.meetinglistscene.start();
            MeetingScheduler.val=0;
        }
        else if(MeetingScheduler.val==2)
        {
            MeetingScheduler.datemeeting.start();
            MeetingScheduler.val=0;
        }
        MeetingScheduler.event.close();
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
        remindtime = FXCollections.observableArrayList("05.00 Min", "10.00 Min", "15.00 Min", "20.00 Min", "30.00 Min", "45.00 Min", "01.00 Hour", "01.50 Hour", "02.00 Hour", "02.50 Hour", "03.50 Hour", "04.00 Hour", "04.50 Hour", "05.00 Hour", "06.00 Hour", "07.00 Hour", "08.00 Hour");
        reminder.getItems().addAll(remindtime);
        name.setText(MeetingScheduler.event.Ob.ob.meetingname);
        personame.setText(MeetingScheduler.event.Ob.ob.meetername);
        description.setText(MeetingScheduler.event.Ob.ob.description);
        location.setText(MeetingScheduler.event.Ob.ob.location);
        StringTokenizer st = new StringTokenizer(MeetingScheduler.event.Ob.ob.time);
        starttime.setText(st.nextToken("-"));
        endtime.setText(st.nextToken("-"));
        StringTokenizer st1 = new StringTokenizer(MeetingScheduler.event.Ob.ob.date);
        String day = st1.nextToken("/");
        String month = limit(Integer.parseInt(st1.nextToken("/")));
        String year = st1.nextToken("/");
        date.setText(month + " " + day + "," + year);
        reminder.setValue((String) MeetingScheduler.event.Ob.reminder);
    }

}
