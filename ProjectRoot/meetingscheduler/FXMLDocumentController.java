package meetingscheduler;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class FXMLDocumentController implements Initializable {
    static getmsg msgthread;
    static CreateAccountWindow win;
    @FXML
    private Label label;
    @FXML
    private Button another;
    @FXML
    private Button login;
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
        win = new CreateAccountWindow();
        if (label.visibleProperty().getValue()) {
            label.visibleProperty().setValue(Boolean.FALSE);
        }
        try {
            win.start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        MeetingScheduler.stage1.close();
    }

    @FXML
    public void LogIn() {
        if (user.getText().length() > 0 && password.getText().length() > 0) {
            String username = user.getText();
            String pass = password.getText();
            MeetingScheduler.username=username;
            String sendmsg = "Type:" + "LogIn" + '\n' + "Username:" + username + '\n' + "PassWord:" + pass;
            //MeetingScheduler.receive=new Clientreceive(MeetingScheduler.nc);
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
        boolean suspendflag=true;
        Thread t;

        getmsg() {
            t = new Thread(this);
            t.start();
        }
        @Override
        public void run() {
            //System.out.println("hello from run");
            while (true) {
                synchronized(this)
                {
                    while(suspendflag)
                    {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                //System.out.println(MeetingScheduler.receive.msg);
                if (MeetingScheduler.receive.msg == true) {
                    String show = MeetingScheduler.receive.get.type;
                    System.out.println(show+" "+"from run");
                    if (show.equals("Invalid LogIn")) {
                        user.clear();
                        password.clear();
                        label.visibleProperty().setValue(Boolean.TRUE);
                        //label.setText(show);
                        System.out.println(show);
                        MeetingScheduler.receive.msg = false;

                    } else if (show.equals("LogIn Successful")) {
                        user.clear();
                        password.clear();
                        if (label.visibleProperty().getValue()) {
                            label.visibleProperty().setValue(Boolean.FALSE);
                        }
                        MeetingScheduler.receive.msg = false;
                        //System.out.println(show);
                        Platform.runLater(() -> {
                            try {
                                Loadingwin window = new Loadingwin();
                                MeetingScheduler.stage1.close();
                                window.start();
                                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(4));
                                delay.setOnFinished(event -> {
                                    MeetingScheduler.cal = new Mainwindow();
                                    window.close();
                                    MeetingScheduler.ring=new Alarmring();
                                    MeetingScheduler.cal.start();
                                });
                                delay.play();
                                //Thread.sleep(4000);
                                //window.close();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        });
                        
                    }
                    suspendflag=true;

                }
            }
        }
        synchronized public void myresume()
        {
            suspendflag=false;
            notify();
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println("hello");
        msgthread=new getmsg();
        if (label.visibleProperty().getValue()) {
            label.visibleProperty().setValue(Boolean.FALSE);
        }
    }

}
