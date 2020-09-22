package meetingscheduler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
public class EventRemind{
    Stage stage;
    Finalmeeting Ob;
    //int idx;
    int idx2;
    String date;
    EventRemind(Finalmeeting Ob,int idx2)
    {
        this.Ob=Ob;
        //this.idx=idx;
        this.idx2=idx2;
    }
    EventRemind(Finalmeeting Ob,int idx2,String date)
    {
        this.Ob=Ob;
        //this.idx=idx;
        this.idx2=idx2;
        this.date=date;
    }
    public void start() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/TextContent/eventremind2.fxml"));
        System.out.println(Ob.reminder);
        stage=new Stage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Event Details");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
    public void close()
    {
        stage.close();
    }
    
}
