/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meetingscheduler;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Details{
    String name,personname,location,date,time,description;
    Details(meeting ob)
    {
        this.name=ob.meetingname;
        this.personname=ob.meetername;
        this.location=ob.location;
        this.date=ob.date;
        this.time=ob.time;
        this.description=ob.description;
    }
    Stage stage;
    public void start() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/TextContent/Details.fxml"));
        stage=new Stage();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Meeting Details");
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
