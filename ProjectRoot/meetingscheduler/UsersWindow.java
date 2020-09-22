package meetingscheduler;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UsersWindow {

    Stage stage;
    Label labels[], numbers[];

    public void start() {
        ScrollPane root = new ScrollPane();
        VBox parentroot = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);
        VBox vbox = new VBox();
        GridPane gridpane = new GridPane();
        //VBox.setVgrow(gridpane, Priority.ALWAYS);
        gridpane.getColumnConstraints().add(new ColumnConstraints(250));
        gridpane.getColumnConstraints().add(new ColumnConstraints(250));
        HBox hbox = new HBox();
        hbox.setPrefSize(600, 150);
        Button userinfo = new Button("Refresh");
        HBox.setMargin(userinfo, new Insets(50, 0, 0, 100));
        userinfo.setAlignment(Pos.CENTER);
        userinfo.setMinSize(200, 50);
        userinfo.getStyleClass().add("options");
        Label end = new Label("End of List");
        VBox.setMargin(end, new Insets(50, 0, 0, 100));
        end.setAlignment(Pos.CENTER);
        end.setMinSize(200, 50);
        end.getStyleClass().add("options");
        Label username = new Label("UserName");
        username.setAlignment(Pos.CENTER);
        username.setMinSize(250, 50);
        username.getStyleClass().add("label2");
        gridpane.add(username, 0, 0);
        Label number = new Label("Number of Meetings");
        number.setAlignment(Pos.CENTER);
        number.setMinSize(250, 50);
        number.getStyleClass().add("label2");
        gridpane.add(number, 1, 0);
        gridpane.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        vbox.setMinSize(700, 700);
        root.setFitToWidth(true);
        back.setMinSize(200, 50);
        HBox.setMargin(back, new Insets(50, 0, 0, 10));
        back.getStyleClass().add("options");
        if (MeetingScheduler.receive.ob.usernumbers.usernames.size() > 0) {
            labels = new Label[MeetingScheduler.receive.ob.usernumbers.usernames.size()];
            numbers = new Label[MeetingScheduler.receive.ob.usernumbers.usernames.size()];
        }
        //vbox.setPrefWidth(700);
        for (int i = 0; i < MeetingScheduler.receive.ob.usernumbers.usernames.size(); i++) {
            labels[i] = new Label(MeetingScheduler.receive.ob.usernumbers.usernames.get(i));
            labels[i].setAlignment(Pos.CENTER);
            labels[i].setMinSize(250, 50);
            labels[i].getStyleClass().add("label2");
            gridpane.add(labels[i], 0, i + 1);
            numbers[i] = new Label(String.valueOf(MeetingScheduler.receive.ob.usernumbers.number.get(i)));
            numbers[i].setAlignment(Pos.CENTER);
            numbers[i].setMinSize(250, 50);
            numbers[i].getStyleClass().add("label2");
            gridpane.add(numbers[i], 1, i + 1);

        }
        userinfo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String msg="Type:"+"Refresh"+'\n'+"Username:"+MeetingScheduler.username+'\n'+"Refreshing:"+"Do It";
                MeetingScheduler.ring.cancel();
                new Clientsend(MeetingScheduler.nc, msg);
                MeetingScheduler.userwin.close();
                LoadingWindow2 window = new LoadingWindow2();
                try {
                    window.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(5));
                delay.setOnFinished(event2 -> {
                    MeetingScheduler.userwin=new UsersWindow();
                    MeetingScheduler.userwin.start();
                    window.close();
                    MeetingScheduler.ring=new Alarmring();
                    
                });
                delay.play();
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.userwin.close();
                MeetingScheduler.cal = new Mainwindow();
                try {
                    MeetingScheduler.cal.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        hbox.getChildren().addAll(userinfo, back);
        vbox.getChildren().addAll(hbox, gridpane, end);
        root.setContent(vbox);
        parentroot.getChildren().add(root);
        root.getStyleClass().add(".scroll-bar:vertical .thumb");
        vbox.getStyleClass().add("user");
        parentroot.getStyleClass().add("user");
        Scene scene = new Scene(parentroot, 700, 700);
        userinfo.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                userinfo.setEffect(new DropShadow(10, Color.CHOCOLATE));
                scene.setCursor(Cursor.HAND);
            }
        });
        back.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                back.setEffect(new DropShadow(10, Color.CHOCOLATE));
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
        URL url = this.getClass().getResource("/TextContent/style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        stage = new Stage();
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setTitle("Users List");
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

    public void close() {
        stage.close();
    }
}
