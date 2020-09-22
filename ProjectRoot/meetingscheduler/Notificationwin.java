package meetingscheduler;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Notificationwin {
    Scene scene;
    Stage stage;
    Label meetings[], persons[], dates[], times[],types[],statuses[];
    public void start() {
        ScrollPane root = new ScrollPane();
        VBox parentroot = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);
        VBox vbox = new VBox();
        GridPane gridpane = new GridPane();
        //VBox.setVgrow(gridpane, Priority.ALWAYS);
        gridpane.getColumnConstraints().add(new ColumnConstraints(200));
        gridpane.getColumnConstraints().add(new ColumnConstraints(200));
        gridpane.getColumnConstraints().add(new ColumnConstraints(175));
        gridpane.getColumnConstraints().add(new ColumnConstraints(175));
        gridpane.getColumnConstraints().add(new ColumnConstraints(125));
        gridpane.getColumnConstraints().add(new ColumnConstraints(125));
        HBox hbox = new HBox();
        hbox.setPrefSize(600, 150);
        Button userinfo = new Button("Refresh");
        HBox.setMargin(userinfo, new Insets(50, 0, 0, 300));
        //userinfo.setAlignment(Pos.CENTER);
        userinfo.setMinSize(200, 50);
        userinfo.getStyleClass().add("options");
        Label meetingname = new Label("MeetingName");
        meetingname.setAlignment(Pos.CENTER);
        meetingname.setMinSize(200, 50);
        meetingname.getStyleClass().add("label2");
        gridpane.add(meetingname, 0, 0);
        Button clear = new Button("Clear All");
        HBox.setMargin(clear, new Insets(50, 0, 0, 20));
        //userinfo.setAlignment(Pos.CENTER);
        //gridpane.setAlignment(Pos.CENTER);
        clear.setMinSize(200, 50);
        clear.getStyleClass().add("options");
        Label personname = new Label("Person Name");
        personname.setAlignment(Pos.CENTER);
        personname.setMinSize(200, 50);
        personname.getStyleClass().add("label2");
        Label end = new Label("End of List");
        VBox.setMargin(end, new Insets(50, 0, 0, 300));
        end.setAlignment(Pos.CENTER);
        end.setMinSize(200, 50);
        end.getStyleClass().add("options");
        gridpane.add(personname, 1, 0);
        Label datestring = new Label("Date");
        datestring.setAlignment(Pos.CENTER);
        datestring.setMinSize(175, 50);
        datestring.getStyleClass().add("label2");
        gridpane.add(datestring, 2, 0);
        Label timestring = new Label("Time");
        timestring.setAlignment(Pos.CENTER);
        timestring.setMinSize(175, 50);
        timestring.getStyleClass().add("label2");
        gridpane.add(timestring, 3, 0);
        Label type = new Label("Type");
        type.setAlignment(Pos.CENTER);
        type.setMinSize(125, 50);
        type.getStyleClass().add("details");
        gridpane.add(type, 4, 0);
        Label status = new Label("Status");
        status.setAlignment(Pos.CENTER);
        status.setMinSize(125, 50);
        status.getStyleClass().add("wait");
        gridpane.add(status, 5, 0);
        gridpane.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        back.setMinSize(200, 50);
        HBox.setMargin(back, new Insets(50, 0, 0, 10));
        back.getStyleClass().add("options");
        ArrayList<Notifications> local = new ArrayList<Notifications>();
        int tmpidx=0,tmpsize=0;
        tmpsize = MeetingScheduler.receive.ob.notification.size();
        final int idx=tmpidx;
        final int size=tmpsize;
        if (size > 0) {
            for(int i=0;i<MeetingScheduler.receive.ob.notification.size();i++)
            {
                local.add(MeetingScheduler.receive.ob.notification.get(i));
            }
            meetings = new Label[size];
            persons = new Label[size];
            dates = new Label[size];
            times = new Label[size];
            types = new Label[size];
            statuses = new Label[size];
        }
        vbox.setMinSize(1200, 800);
        root.setFitToWidth(true);
        for (int i = 0; i < size; i++) {
            meetings[i] = new Label(local.get(i).ob.meetingname);
            meetings[i].setAlignment(Pos.CENTER);
            meetings[i].setMinSize(200, 50);
            meetings[i].getStyleClass().add("label2");
            gridpane.add(meetings[i], 0, i + 1);
            persons[i] = new Label(local.get(i).ob.meetername);
            persons[i].setAlignment(Pos.CENTER);
            persons[i].setMinSize(200, 50);
            persons[i].getStyleClass().add("label2");
            gridpane.add(persons[i], 1, i + 1);
            dates[i] = new Label(local.get(i).ob.date);
            dates[i].setAlignment(Pos.CENTER);
            dates[i].setMinSize(175, 50);
            dates[i].getStyleClass().add("label2");
            gridpane.add(dates[i], 2, i + 1);
            times[i] = new Label(local.get(i).ob.time);
            times[i].setAlignment(Pos.CENTER);
            times[i].setMinSize(175, 50);
            times[i].getStyleClass().add("label2");
            gridpane.add(times[i], 3, i + 1);
            types[i] = new Label(local.get(i).type);
            types[i].setAlignment(Pos.CENTER);
            types[i].setMinSize(125, 50);
            if(local.get(i).type.equals("Request"))
                types[i].getStyleClass().add("request");
            else if(local.get(i).type.equals("Meeting"))
                types[i].getStyleClass().add("meeting");
            gridpane.add(types[i], 4, i + 1);
            statuses[i] = new Label(local.get(i).status);
            statuses[i].setAlignment(Pos.CENTER);
            statuses[i].setMinSize(125, 50);
            if(local.get(i).status.equals("Accepted"))
                statuses[i].getStyleClass().add("accept");
            else if(local.get(i).status.equals("Declined"))
                statuses[i].getStyleClass().add("cancel");
            else if(local.get(i).status.equals("Cancelled"))
                statuses[i].getStyleClass().add("cancelled");
            gridpane.add(statuses[i], 5, i + 1);
        }
        userinfo.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                userinfo.setEffect(new DropShadow(20, Color.CHOCOLATE));
                scene.setCursor(Cursor.HAND);
            }
        });
        back.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                back.setEffect(new DropShadow(20, Color.CHOCOLATE));
                scene.setCursor(Cursor.HAND);
            }
        });
        clear.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                clear.setEffect(new DropShadow(20, Color.CHOCOLATE));
                scene.setCursor(Cursor.HAND);
            }
        });
        userinfo.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                userinfo.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        back.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                back.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        clear.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                clear.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        userinfo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String msg = "Type:" + "Refresh" + '\n' + "Username:" + MeetingScheduler.username + '\n' + "Refreshing:" + "Do It";
                MeetingScheduler.ring.cancel();
                new Clientsend(MeetingScheduler.nc, msg);
                MeetingScheduler.notif.close();
                LoadingWindow2 window = new LoadingWindow2();
                try {
                    window.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(4));
                delay.setOnFinished(event2 -> {
                    MeetingScheduler.notif = new Notificationwin();
                    MeetingScheduler.notif.start();
                    window.close();
                    MeetingScheduler.ring=new Alarmring();

                });
                delay.play();
            }
        });
        //final int idx2=idx;
        clear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String msg="Type:"+"ClearNotifications"+'\n'+"Username:"+MeetingScheduler.username+'\n'
                        +"meetdate:"+"Do It";
                new Clientsend(MeetingScheduler.nc,msg);
                MeetingScheduler.receive.ob.notification.clear();
                MeetingScheduler.notif.close();
                Loadingwin window = new Loadingwin();
                try {
                    window.start();
                    MeetingScheduler.notif = new Notificationwin();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
                delay.setOnFinished(event2 -> {
                    MeetingScheduler.notif.start();
                    window.close();

                });
                delay.play();
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.cal = new Mainwindow();
                try {
                    MeetingScheduler.cal.start();
                    MeetingScheduler.notif.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        hbox.getChildren().addAll(userinfo, back,clear);
        vbox.getChildren().addAll(hbox, gridpane, end);
        //VBox.setVgrow(root, Priority.ALWAYS);
        root.setContent(vbox);
        parentroot.getChildren().add(root);
        //parentroot.setPrefSize(700,700);
        root.getStyleClass().add(".scroll-bar:vertical .thumb");
        vbox.getStyleClass().add("user");
        parentroot.getStyleClass().add("user");
        root.getStyleClass().add("user");
        //parentroot.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);
        scene = new Scene(parentroot, 1200, 800);
        URL url = this.getClass().getResource("/TextContent/style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        stage=new Stage();
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Meeting List");
        stage.setScene(scene);
        stage.show();

    }
    public void close()
    {
        stage.close();
    }

}


