package meetingscheduler;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
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
import javafx.stage.WindowEvent;

public class Meetinglistwin{

    Label meetings[], persons[], dates[], times[];
    Button details[], cancels[];
    Stage stage;
    public void start() {
        ScrollPane root = new ScrollPane();
        VBox parentroot = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);
        VBox vbox = new VBox();
        GridPane gridpane = new GridPane();
        //VBox.setVgrow(gridpane, Priority.ALWAYS);
        gridpane.getColumnConstraints().add(new ColumnConstraints(175));
        gridpane.getColumnConstraints().add(new ColumnConstraints(175));
        gridpane.getColumnConstraints().add(new ColumnConstraints(125));
        gridpane.getColumnConstraints().add(new ColumnConstraints(175));
        gridpane.getColumnConstraints().add(new ColumnConstraints(100));
        gridpane.getColumnConstraints().add(new ColumnConstraints(100));
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
        datestring.setMinSize(150, 50);
        datestring.getStyleClass().add("label2");
        gridpane.add(datestring, 2, 0);
        Label timestring = new Label("Time");
        timestring.setAlignment(Pos.CENTER);
        timestring.setMinSize(175, 50);
        timestring.getStyleClass().add("label2");
        gridpane.add(timestring, 3, 0);
        Label showdetails = new Label("Details");
        showdetails.setAlignment(Pos.CENTER);
        showdetails.setMinSize(100, 50);
        showdetails.getStyleClass().add("details");
        gridpane.add(showdetails, 4, 0);
        Label cancel = new Label("Action");
        cancel.setAlignment(Pos.CENTER);
        cancel.setMinSize(100, 50);
        cancel.getStyleClass().add("cancel");
        gridpane.add(cancel, 5, 0);
        gridpane.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        back.setMinSize(200, 50);
        HBox.setMargin(back, new Insets(50, 0, 0, 10));
        back.getStyleClass().add("options");
        ArrayList<Finalmeeting> local = new ArrayList<Finalmeeting>();
        int tmpidx=0,tmpsize=0;
        tmpsize = MeetingScheduler.receive.ob.meetinglist.size();
        final int idx=tmpidx;
        final int size=tmpsize;
        if (size > 0) {
            for(int i=0;i<MeetingScheduler.receive.ob.meetinglist.size();i++)
            {
                local.add(MeetingScheduler.receive.ob.meetinglist.get(i));
            }
            meetings = new Label[size];
            persons = new Label[size];
            dates = new Label[size];
            times = new Label[size];
            details = new Button[size];
            cancels = new Button[size];
        }
        vbox.setMinSize(1200, 800);
        root.setFitToWidth(true);
        Scene scene;
        final ArrayList<Finalmeeting>local2=local;
        for (int i = 0; i < size; i++) {
            meetings[i] = new Label(local.get(i).ob.meetingname);
            meetings[i].setAlignment(Pos.CENTER);
            meetings[i].setMinSize(175, 50);
            meetings[i].getStyleClass().add("label2");
            gridpane.add(meetings[i], 0, i + 1);
            persons[i] = new Label(local.get(i).ob.meetername);
            persons[i].setAlignment(Pos.CENTER);
            persons[i].setMinSize(175, 50);
            persons[i].getStyleClass().add("label2");
            gridpane.add(persons[i], 1, i + 1);
            dates[i] = new Label(local.get(i).ob.date);
            dates[i].setAlignment(Pos.CENTER);
            dates[i].setMinSize(125, 50);
            dates[i].getStyleClass().add("label2");
            gridpane.add(dates[i], 2, i + 1);
            times[i] = new Label(local.get(i).ob.time);
            times[i].setAlignment(Pos.CENTER);
            times[i].setMinSize(175, 50);
            times[i].getStyleClass().add("label2");
            gridpane.add(times[i], 3, i + 1);
            details[i] = new Button("Details");
            details[i].setAlignment(Pos.CENTER);
            details[i].setMinSize(100, 50);
            details[i].getStyleClass().add("details");
            gridpane.add(details[i], 4, i + 1);
            cancels[i] = new Button("Cancel");
            cancels[i].setAlignment(Pos.CENTER);
            cancels[i].setMinSize(100, 50);
            cancels[i].getStyleClass().add("cancel");
            gridpane.add(cancels[i], 5, i + 1);
        }
        userinfo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String msg = "Type:" + "Refresh" + '\n' + "Username:" + MeetingScheduler.username + '\n' + "Refreshing:" + "Do It";
                MeetingScheduler.ring.cancel();
                new Clientsend(MeetingScheduler.nc, msg);
                MeetingScheduler.meetinglistscene.close();
                LoadingWindow2 window = new LoadingWindow2();
                try {
                    window.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(5));
                delay.setOnFinished(event2 -> {
                    int idx = 0, size = 0;
                    size = MeetingScheduler.receive.ob.meetingrequestto.size();
                    MeetingScheduler.meetinglistscene = new Meetinglistwin();
                    MeetingScheduler.meetinglistscene.start();
                    window.close();
                    MeetingScheduler.ring=new Alarmring();

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
                    MeetingScheduler.meetinglistscene.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        hbox.getChildren().addAll(userinfo, back);
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
        for (int i = 0; i < size; i++) {
            final int idx2 = i;
            String name = meetings[i].getText();
            String person = persons[i].getText();
            String meetdate = dates[i].getText();
            String meettime = times[i].getText();
            final Button mybutton = details[i];
            final Button mybutton2 = cancels[i];
            details[i].addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.HAND);
                    mybutton.setEffect(new DropShadow(10, Color.CRIMSON));
                }
            });
            details[i].addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.DEFAULT);
                    mybutton.setEffect(null);
                }
            });
            cancels[i].addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.HAND);
                    mybutton2.setEffect(new DropShadow(10, Color.CRIMSON));
                }
            });
            cancels[i].addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.DEFAULT);
                    mybutton2.setEffect(null);
                }
            });
            details[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Finalmeeting tmp;
                    tmp = local2.get(idx2);
                    MeetingScheduler.val=1;
                    MeetingScheduler.event = new EventRemind(tmp,idx2);
                    try {
                        MeetingScheduler.event.start();
                    } catch (Exception e) {
                        System.out.println(e+"ami");
                    }
                    MeetingScheduler.meetinglistscene.close();

                }
            });
            cancels[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    int flag = 0;
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("You want to cancel this Event(" + "Meeting:" + name + ")");
                    alert.setContentText("Are you ok with this?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        flag = 1;
                        mybutton.setDisable(true);
                        mybutton2.setDisable(true);
                    } else {

                    }
                    if (flag == 1) {
                        Finalmeeting tmp;
                        tmp = local2.get(idx2);
                        String sendmsg = "Type:" + "CancelMeeting" + '\n' + "Username:" + MeetingScheduler.username
                                + '\n' + "meetdatetime:" + tmp.ob.meetername + '$' + tmp.ob.date + '$' + tmp.ob.time + '$'
                                + tmp.ob.meetingname + '$' + tmp.ob.location + '$' + tmp.ob.description;
                        new Clientsend(MeetingScheduler.nc, sendmsg);
                        //MeetingScheduler.receive.ob.get(idx).meetinglist.remove(idx2);
                        for (int j = 0; j < MeetingScheduler.receive.ob.meetinglist.size(); j++) {
                            if (tmp.equals(MeetingScheduler.receive.ob.meetinglist.get(j))) {
                                MeetingScheduler.receive.ob.meetinglist.remove(j);
                                break;
                            }
                        }
                        PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
                        Loadingwin window = new Loadingwin();
                        delay.setOnFinished(event2
                                -> {
                            MeetingScheduler.meetinglistscene.close();
                            try {
                                window.start();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            MeetingScheduler.meetinglistscene = new Meetinglistwin();
                            MeetingScheduler.meetinglistscene.start();
                            window.close();
                        });
                        delay.play();
                    }
                }
            });
        }
        URL url = this.getClass().getResource("/TextContent/style.css");
        if (url
                == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        stage = new Stage();
        String css = url.toExternalForm();

        scene.getStylesheets()
                .add(css);
        stage.setTitle(
                "Meeting List");
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

