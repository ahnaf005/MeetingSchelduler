package meetingscheduler;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class SendwindowController implements Initializable {

    ArrayList<String> usernames;
    String starttime1, endtime1, meetername, Location, Description, Meetingname;
    ObservableList<String> starttime;
    ObservableList<String> endtime;
//    @FXML
//    private Label label;
    @FXML
    private ComboBox<String> start;
    @FXML
    private ComboBox<String> end;
    @FXML
    private Button sendrqst;
    @FXML
    private Button clear;
    @FXML
    private TextField personname;
    @FXML
    private TextField meetingname;
    @FXML
    private TextField location;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea description;
    @FXML
    private CheckBox allday;
    @FXML
    private Button back;

    @FXML
    public void backclicked() {
        MeetingScheduler.sen.close();
        MeetingScheduler.cal = new Mainwindow();
        MeetingScheduler.cal.start();
    }

    @FXML
    public void clearclicked() {
        meetingname.clear();
        location.clear();
        personname.clear();
        description.clear();
        date.setValue(null);
        start.setValue(null);
        end.setValue(null);
    }

    public void sendrqstclicked() {
        usernames = new ArrayList<String>();
        if (meetingname.getText().length() > 0 && location.getText().length() > 0 && personname.getText().length() > 0 && description.getText().length() > 0 && start.getValue() != null && end.getValue() != null && date.getValue() != null) {
            DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String format = (date.getValue()).format(formatter_2);
            StringTokenizer st = new StringTokenizer(format);
            int formatdate = Integer.parseInt(st.nextToken("/"));
            int formatmonth = Integer.parseInt(st.nextToken("/"));
            int formatyear = Integer.parseInt(st.nextToken("/"));
            starttime1 = start.getValue();
            endtime1 = end.getValue();
            StringTokenizer st1 = new StringTokenizer(starttime1);
            String hour = st1.nextToken(":");
            String minute1 = st1.nextToken(":");
            System.out.println("hello");
            int hourf = 0;
            int minutef = 0;
            if (starttime1.contains("PM")) {
                String minute = minute1.substring(0, minute1.indexOf("PM") - 1);
                if (!hour.equals("12")) {
                    hourf = Integer.parseInt(hour) + 12;
                } else {
                    hourf = Integer.parseInt(hour);
                }
                minutef = Integer.parseInt(minute);
            } else if (starttime1.contains("AM")) {
                String minute = minute1.substring(0, minute1.indexOf("AM") - 1);
                hourf = Integer.parseInt(hour);
                minutef = Integer.parseInt(minute);
            }
            Calendar now = Calendar.getInstance();
            Calendar requested = Calendar.getInstance();
            System.out.println(now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + "/" + now.get(Calendar.YEAR) + "/");
            requested.set(Calendar.DATE, formatdate);
            requested.set(Calendar.MONTH, formatmonth - 1);
            requested.set(Calendar.YEAR, formatyear);
            requested.set(Calendar.HOUR_OF_DAY, hourf);
            requested.set(Calendar.MINUTE, minutef);
            requested.set(Calendar.SECOND, 0);
            for (int i = 0; i < MeetingScheduler.receive.ob.usernumbers.usernames.size(); i++) {
                usernames.add(MeetingScheduler.receive.ob.usernumbers.usernames.get(i));
            }
            int flag1 = 0, flag2 = 0;
            if (now.before(requested)) {
                if (time(starttime1) <= time(endtime1)) {
                    //System.out.println("done");
                    flag1 = 1;

                } else {
                    end.setValue(null);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error ");
                    alert.setHeaderText("Look, an Error Occured");
                    alert.setContentText("Endtime should be after Starttime");
                    alert.showAndWait();

                }
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Look, an Error Occured");
                alert.setContentText("The Desired Time is past!!");
                alert.showAndWait();
            }
            if (usernames.contains(personname.getText())) {
                flag2 = 1;
            } else {
                personname.clear();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Look, an Error Occured");
                alert.setContentText("The Person Is not signed up with our App");
                alert.showAndWait();
            }
            if (flag1 == 1 && flag2 == 1) {
                String des = description.getText();
                //des.replace('\n',' ');
                String sendthemsg = "Type:" + "MeetingRequest" + '\n' + "Username:" + MeetingScheduler.username + '\n'
                        + "meetdatetime:" + personname.getText() + '$' + format + '$' + start.getValue() + '-' + end.getValue()
                        + '$' + meetingname.getText() + '$' + location.getText() + '$' + des;
                //System.out.println(sendthemsg);
                meeting tmp=new meeting(personname.getText(),format,start.getValue() + '-' + end.getValue(),meetingname.getText(),location.getText(),des);
                MeetingScheduler.receive.ob.meetingrequestto.add(tmp);
                new Clientsend(MeetingScheduler.nc, sendthemsg);
                personname.clear();
                description.clear();
                meetingname.clear();
                location.clear();
                start.setValue(null);
                end.setValue(null);
                date.setValue(null);
                if (allday.isSelected()) {
                    allday.setSelected(false);
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Request Send Successful!");

                alert.showAndWait();

            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Look, an Error Occured");
            alert.setContentText("Please Fill up All the Fields");
            alert.showAndWait();
        }
    }

    @FXML
    public void alldayclicked() {
        if (allday.isSelected()) {
            start.setValue(starttime.get(0));
            end.setValue(endtime.get(endtime.size() - 1));
        }
    }

    int time(String a) {
        StringTokenizer st1 = new StringTokenizer(a);
        String hour = st1.nextToken(":");
        String minute1 = st1.nextToken(":");
        int hourf;
        if (a.contains("PM")) {
            String minute = minute1.substring(0, minute1.indexOf("PM") - 1);
            if (!hour.equals("12")) {
                hourf = Integer.parseInt(hour) + 12;
            } else {
                hourf = Integer.parseInt(hour);
            }
            int minutef = Integer.parseInt(minute);
            return hourf * 60 + minutef;
        } else if (a.contains("AM")) {
            String minute = minute1.substring(0, minute1.indexOf("AM") - 1);
            hourf = Integer.parseInt(hour);
            int minutef = Integer.parseInt(minute);
            return hourf * 60 + minutef;

        }
        return 1;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

        //label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        starttime = FXCollections.observableArrayList("09:00 AM","09:30 AM","10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM", "05:00 PM", "05:30 PM", "06:00 PM","06:30 PM","07:00 PM","07:30 PM","08:00 PM","08:30 PM","09:00 PM","09:30 PM");
        endtime = FXCollections.observableArrayList("09:30 AM","10:00 AM","10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM", "05:00 PM", "05:30 PM", "06:00 PM", "6:30 PM", "7:00 PM","07:30 PM","08:00 PM","08:30 PM","09:00 PM","09:30 PM","10:00 PM");
        start.setItems(starttime);
        end.setItems(endtime);
        //System.out.println("hello");
    }

}
