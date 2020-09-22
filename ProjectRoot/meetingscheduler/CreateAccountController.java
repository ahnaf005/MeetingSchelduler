package meetingscheduler;

//import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountController implements Initializable {

    static getmsg matha;
    @FXML
    private Label label2;
    @FXML
    private Label label;
    @FXML
    private Button another;
    @FXML
    private Button create;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    public void anotherwindow() {
        MeetingScheduler.stage1.show();
        FXMLDocumentController.win.close();
    }

    @FXML
    public void createacnt() {
        if (user.getText().length() > 0 && password.getText().length() > 0) {
            String username = user.getText();
            String pass = password.getText();
            String sendmsg = "Type:" + "CreateAccount" + '\n' + "Username:" + username + '\n' + "PassWord:" + pass;
            try {
                MeetingScheduler.nc = new networkutil("localhost", 45555);
                MeetingScheduler.receive = new Clientreceive(MeetingScheduler.nc);
            } catch (Exception e) {
                System.out.println(e);
            }
            new Clientsend(MeetingScheduler.nc, sendmsg);
            //label.setText("Hello");
            //System.out.println("hello");
        }
    }

    class getmsg implements Runnable {

        public boolean flag = true;
        Thread t;

        getmsg() {
            t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (flag) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                if (MeetingScheduler.receive.msg == true) {
                    String show = MeetingScheduler.receive.get.type;
                    //System.out.println("hoitese");
                    //System.out.println("show");
                    //System.out.println(show);
                    if (show.equals("Account Creation Successful")) {
                        user.clear();
                        password.clear();
                        label.visibleProperty().setValue(Boolean.TRUE);
                        if (label2.visibleProperty().getValue()) {
                            label2.visibleProperty().setValue(Boolean.FALSE);
                        }
                        //label.setText(show);
                        System.out.println(show);
                        MeetingScheduler.receive.msg = false;

                    } else if (show.equals("Username Taken")) {
                        user.clear();
                        password.clear();
                        label2.visibleProperty().setValue(Boolean.TRUE);
                        if (label.visibleProperty().getValue()) {
                            label.visibleProperty().setValue(Boolean.FALSE);
                        }
                        MeetingScheduler.receive.msg = false;

                        //System.out.println(show);
                    }
                    flag = true;

                }
            }
        }

        synchronized public void myresume() {
            flag = false;
            notify();
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println("hello");
        matha = new getmsg();
        //label.setText("Hello");
    }

}
