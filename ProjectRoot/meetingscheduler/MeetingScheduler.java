package meetingscheduler;
import java.io.InputStream;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MeetingScheduler extends Application {

    public static String username;
    static int alertflag = 0;
    static Details detail;
    static Alarmring ring;
    static Received recreq;
    static Notificationwin notif;
    static int variable = 0;
    static int val = 0;
    static Meetinglistwin meetinglistscene;
    static SentRequest sent;
    static UsersWindow userwin;
    static Mainwindow cal;
    static EventRemind event;
    static Historywin historywin;
    static Datemeetingwin datemeeting;
    static SendWindow sen;
    public static networkutil nc;
    static Clientreceive receive;
    static boolean value = true;
    static Stage stage1;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/TextContent/FXMLDocument.fxml"));
//        String externalForm = getClass().getResource("TextContent/FXMLDocument.fxml").toExternalForm();
//
//         InputStream inStream = new URL(externalForm).openStream();
//        FXMLLoader loader = new FXMLLoader();
//        loader.load(inStream);
        //loader.setLocation(getClass().getResource("TextContent/FXMLDocument.fxml"));
        //Parent root = loader.load(); 
        Scene scene = new Scene(root);
        stage1 = stage;
        stage1.setScene(scene);
        stage1.setResizable(false);
        stage1.setTitle("LogIn Window");
        stage1.show();
        stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        try {
            nc = new networkutil("localhost", 45555);
            receive = new Clientreceive(nc);
        } catch (Exception e) {
            System.out.println(e);
        }
        launch(args);
    }

}
