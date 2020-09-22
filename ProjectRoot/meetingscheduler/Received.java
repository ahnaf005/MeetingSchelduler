package meetingscheduler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.StringTokenizer;
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

public class Received {

    Stage stage;
    int  size;

    Received(int size) {
        //this.idx = idx;
        this.size = size;
    }
    Label meetings[], persons[], dates[], times[], statuses[];
    Button details[], accepts[], declines[];

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
        Label accept = new Label("Action");
        accept.setAlignment(Pos.CENTER);
        accept.setMinSize(100, 50);
        accept.getStyleClass().add("accept");
        gridpane.add(accept, 5, 0);
        gridpane.setAlignment(Pos.CENTER);
        Label decline = new Label("Action");
        decline.setAlignment(Pos.CENTER);
        decline.setMinSize(100, 50);
        decline.getStyleClass().add("cancel");
        gridpane.add(decline, 6, 0);
        gridpane.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        Label status = new Label("Status");
        status.setAlignment(Pos.CENTER);
        status.setMinSize(100, 50);
        status.getStyleClass().add("wait");
        gridpane.add(status, 7, 0);
        gridpane.setAlignment(Pos.CENTER);
        back.setMinSize(200, 50);
        HBox.setMargin(back, new Insets(50, 0, 0, 10));
        back.getStyleClass().add("options");
        ArrayList<meeting> local = new ArrayList<meeting>();
        if (size > 0) {
            //local = MeetingScheduler.receive.ob.get(idx).meetingrequestfrom;
            for (int j = 0; j < size; j++) {
                local.add(MeetingScheduler.receive.ob.meetingrequestfrom.get(j));
            }
            meetings = new Label[size];
            persons = new Label[size];
            dates = new Label[size];
            times = new Label[size];
            details = new Button[size];
            accepts = new Button[size];
            declines = new Button[size];
            statuses = new Label[size];
        }
        vbox.setMinSize(1200, 800);
        root.setFitToWidth(true);
        Scene scene;
        for (int i = 0; i < size; i++) {
            meetings[i] = new Label(local.get(i).meetingname);
            meetings[i].setAlignment(Pos.CENTER);
            meetings[i].setMinSize(175, 50);
            meetings[i].getStyleClass().add("label2");
            gridpane.add(meetings[i], 0, i + 1);
            persons[i] = new Label(local.get(i).meetername);
            persons[i].setAlignment(Pos.CENTER);
            persons[i].setMinSize(175, 50);
            persons[i].getStyleClass().add("label2");
            gridpane.add(persons[i], 1, i + 1);
            dates[i] = new Label(local.get(i).date);
            dates[i].setAlignment(Pos.CENTER);
            dates[i].setMinSize(125, 50);
            dates[i].getStyleClass().add("label2");
            gridpane.add(dates[i], 2, i + 1);
            times[i] = new Label(local.get(i).time);
            times[i].setAlignment(Pos.CENTER);
            times[i].setMinSize(175, 50);
            times[i].getStyleClass().add("label2");
            gridpane.add(times[i], 3, i + 1);
            details[i] = new Button("Details");
            details[i].setAlignment(Pos.CENTER);
            details[i].setMinSize(100, 50);
            details[i].getStyleClass().add("details");
            gridpane.add(details[i], 4, i + 1);
            accepts[i] = new Button("Accept");
            accepts[i].setAlignment(Pos.CENTER);
            accepts[i].setMinSize(100, 50);
            accepts[i].getStyleClass().add("accept");
            gridpane.add(accepts[i], 5, i + 1);
            declines[i] = new Button("Decline");
            declines[i].setAlignment(Pos.CENTER);
            declines[i].setMinSize(100, 50);
            declines[i].getStyleClass().add("cancel");
            gridpane.add(declines[i], 6, i + 1);
            statuses[i] = new Label();
            statuses[i].setAlignment(Pos.CENTER);
            statuses[i].setMinSize(100, 50);
            statuses[i].getStyleClass().add("wait");
            gridpane.add(statuses[i], 7, i + 1);
        }
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
        userinfo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String msg = "Type:" + "Refresh" + '\n' + "Username:" + MeetingScheduler.username + '\n' + "Refreshing:" + "Do It";
                MeetingScheduler.ring.cancel();
                new Clientsend(MeetingScheduler.nc, msg);
                MeetingScheduler.recreq.close();
                LoadingWindow2 window = new LoadingWindow2();
                try {
                    window.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(5));
                delay.setOnFinished(event2 -> {
                    int size = 0;
                    
                    size = MeetingScheduler.receive.ob.meetingrequestfrom.size();
                    
                    MeetingScheduler.recreq = new Received( size);
                    MeetingScheduler.recreq.start();
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
                    MeetingScheduler.recreq.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        final ArrayList<meeting> local2 = local;
        for (int i = 0; i < size; i++) {
            final int idx2 = i;
            String name = meetings[i].getText();
            String person = persons[i].getText();
            String meetdate = dates[i].getText();
            String meettime = times[i].getText();
            final Button mybutton = details[i];
            final Button mybutton2 = accepts[i];
            final Button mybutton3 = declines[i];
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
            accepts[i].addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.HAND);
                    mybutton2.setEffect(new DropShadow(10, Color.CRIMSON));
                }
            });
            accepts[i].addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.DEFAULT);
                    mybutton2.setEffect(null);
                }
            });
            declines[i].addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.HAND);
                    mybutton3.setEffect(new DropShadow(10, Color.CRIMSON));
                }
            });
            declines[i].addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    scene.setCursor(Cursor.DEFAULT);
                    mybutton3.setEffect(null);
                }
            });
            final Label label = statuses[i];
            details[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    meeting tmp;
                    tmp = local2.get(idx2);
                    MeetingScheduler.detail = new Details(tmp);
                    try {
                        MeetingScheduler.variable = 2;
                        MeetingScheduler.detail.start();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    MeetingScheduler.recreq.close();

                }
            });
            accepts[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    meeting tmpl;
                    tmpl = local2.get(idx2);
                    System.out.println(size);
                    System.out.println(local2.size());
                    System.out.println(idx2);
                    //System.out.println();
                    System.out.println(tmpl.date + " " + tmpl.time);
                    boolean flag = checkbefore(tmpl.date, tmpl.time);
                    if (checkbefore(tmpl.date, tmpl.time)) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("You want to Accept this request(" + "Meeting:" + name + ")");
                        alert.setContentText("Are you ok with this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            mybutton.setDisable(true);
                            mybutton2.setDisable(true);
                            mybutton3.setDisable(true);
                            meeting tmp;
                            //tmp = MeetingScheduler.receive.ob.get(idx).meetingrequestfrom.get(idx2);
                            tmp = local2.get(idx2);
                            //System.out.println(local2.size());
                            String sendmsg = "Type:" + "MeetingRequestYes" + '\n' + "Username:" + MeetingScheduler.username
                                    + '\n' + "meetdatetime:" + tmp.meetername + '$' + tmp.date + '$' + tmp.time + '$'
                                    + tmp.meetingname + '$' + tmp.location + '$' + tmp.description;
                            new Clientsend(MeetingScheduler.nc, sendmsg);
                            for (int j = 0; j < MeetingScheduler.receive.ob.meetingrequestfrom.size(); j++) {
                                if (tmp.equals(MeetingScheduler.receive.ob.meetingrequestfrom.get(j))) {
                                    MeetingScheduler.receive.ob.meetingrequestfrom.remove(j);
                                    break;
                                }
                            }
                            //MeetingScheduler.receive.ob.get(idx).meetingrequestfrom.remove(idx2);
                            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(3));
                            delay.setOnFinished(event2 -> {
                                if (MeetingScheduler.receive.special) {
                                    MeetingScheduler.recreq.close();
                                    MeetingScheduler.recreq = new Received(MeetingScheduler.receive.ob.meetingrequestfrom.size());
                                    System.out.println("Done");
                                    MeetingScheduler.alertflag = 1;
                                    MeetingScheduler.receive.special = false;
                                    MeetingScheduler.recreq.start();
                                } else {
                                    label.setText("Accepted");
                                    label.getStyleClass().add("accept");
                                    Finalmeeting tmp2 = new Finalmeeting(tmp, "01.00 Hour");
                                    MeetingScheduler.receive.ob.meetinglist.add(tmp2);
                                    //System.out.println("Not desired");

                                }

                            });
                            delay.play();
                        } else {
                            // ... user chose CANCEL or closed the dialog
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("This Requests Time is Over");
                        alert.setContentText("Action Not Valid");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            label.setText("Invalid");
                            label.getStyleClass().add("accept");
                            mybutton.setDisable(true);
                            mybutton2.setDisable(true);
                            mybutton3.setDisable(true);
                            meeting tmp;
                            tmp = local2.get(idx2);
                            //tmp = MeetingScheduler.receive.ob.get(idx).meetingrequestfrom.get(idx2);
                            String sendmsg = "Type:" + "MeetingRequestInValid" + '\n' + "Username:" + MeetingScheduler.username
                                    + '\n' + "meetdatetime:" + tmp.meetername + '$' + tmp.date + '$' + tmp.time + '$'
                                    + tmp.meetingname + '$' + tmp.location + '$' + tmp.description;
                            new Clientsend(MeetingScheduler.nc, sendmsg);
                            for (int j = 0; j < MeetingScheduler.receive.ob.meetingrequestfrom.size(); j++) {
                                if (tmp.equals(MeetingScheduler.receive.ob.meetingrequestfrom.get(j))) {
                                    MeetingScheduler.receive.ob.meetingrequestfrom.remove(j);
                                    break;
                                }
                            }
                            
                        } else {
                            // ... user chose CANCEL or closed the dialog
                        }

                    }
                }
            });
            declines[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("You want to Decline this request(" + "Meeting:" + name + ")");
                    alert.setContentText("Are you ok with this?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        mybutton.setDisable(true);
                        mybutton2.setDisable(true);
                        mybutton3.setDisable(true);
                        meeting tmp;
                        tmp = local2.get(idx2);
                        //tmp = MeetingScheduler.receive.ob.get(idx).meetingrequestfrom.get(idx2);
                        String sendmsg = "Type:" + "MeetingRequestNo" + '\n' + "Username:" + MeetingScheduler.username
                                + '\n' + "meetdatetime:" + tmp.meetername + '$' + tmp.date + '$' + tmp.time + '$'
                                + tmp.meetingname + '$' + tmp.location + '$' + tmp.description;
                        new Clientsend(MeetingScheduler.nc, sendmsg);
                        for (int j = 0; j < MeetingScheduler.receive.ob.meetingrequestfrom.size(); j++) {
                            if (tmp.equals(MeetingScheduler.receive.ob.meetingrequestfrom.get(j))) {
                                MeetingScheduler.receive.ob.meetingrequestfrom.remove(j);
                                break;
                            }
                        }
                        PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(3));
                            delay.setOnFinished(event2 -> {
                                if (MeetingScheduler.receive.special) {
                                    MeetingScheduler.recreq.close();
                                    MeetingScheduler.recreq = new Received(MeetingScheduler.receive.ob.meetingrequestfrom.size());
                                    System.out.println("Done");
                                    MeetingScheduler.receive.special=false;
                                    MeetingScheduler.recreq.start();
                                } else {
                                    label.setText("Declined");
                                    label.getStyleClass().add("cancel");
                                    System.out.println("Not desired");

                                }

                            });
                            delay.play();
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                }
            });
        }
        URL url = this.getClass().getResource("/TextContent/style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        stage = new Stage();
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Received Requests");
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

    public boolean checkbefore(String date, String time) {
        StringTokenizer st = new StringTokenizer(date);
        int formatdate = Integer.parseInt(st.nextToken("/"));
        int formatmonth = Integer.parseInt(st.nextToken("/"));
        int formatyear = Integer.parseInt(st.nextToken("/"));
        StringTokenizer st2 = new StringTokenizer(time);
        String starttime1 = st2.nextToken("-");
        String endtime1 = st2.nextToken("-");
        StringTokenizer st1 = new StringTokenizer(endtime1);
        String hour = st1.nextToken(":");
        String minute1 = st1.nextToken(":");
        System.out.println("hello");
        int hourf = 0;
        int minutef = 0;
        if (endtime1.contains("PM")) {
            String minute = minute1.substring(0, minute1.indexOf("PM") - 1);
            if (!hour.equals("12")) {
                hourf = Integer.parseInt(hour) + 12;
            } else {
                hourf = Integer.parseInt(hour);
            }
            minutef = Integer.parseInt(minute);
        } else if (endtime1.contains("AM")) {
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
        System.out.println(requested.get(Calendar.DATE) + "/" + requested.get(Calendar.MONTH) + "/" + requested.get(Calendar.YEAR));
        System.out.println(requested.get(Calendar.HOUR) + ":" + requested.get(Calendar.MINUTE));
        return now.before(requested);
    }

    public void close() {
        stage.close();
    }
//    public static void main(String[] args) {
//        launch(args);
//    }

}
