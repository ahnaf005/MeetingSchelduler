package meetingscheduler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CreateAccountWindow {
    Stage stage;
    public void start() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/TextContent/CreateAccount.fxml"));
        stage=new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Create Account Window");
        stage.setResizable(false);
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
