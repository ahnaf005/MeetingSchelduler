package meetingscheduler;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Mainwindow {

    Scene scene;
    sendobject get;
    Stage stage;
    Label digital;
    SplitPane parentroot;
    String day, mon, dat, time, y;
    VBox root, options;
    HBox first, second;
    Button previous, next, event, refreshed, users, sent, received, notifications, history, refresh, logout, Meetings, close;
    Region firstregion, labelb, labela;
    Label month, year;
    GridPane calender;

    Mainwindow() {
        get = MeetingScheduler.receive.ob;
    }

    int isevent(String monthtext, String yeartext, int buttonlebel) {
        ArrayList<Finalmeeting> usermeeting = new ArrayList<Finalmeeting>();
        //System.out.println(MeetingScheduler.username);
        usermeeting = get.meetinglist;
        for (int i = 0; i < usermeeting.size(); i++) {
            StringTokenizer com = new StringTokenizer(usermeeting.get(i).ob.date);
            String comdate = com.nextToken("/");
            String commonth = com.nextToken("/");
            String comyear = com.nextToken("/");
            //System.out.println(comdate+" "+commonth+" "+comyear);
            if (Integer.parseInt(commonth) == monthnum(monthtext) && Integer.parseInt(comyear) == Integer.parseInt(yeartext) && Integer.parseInt(comdate) == buttonlebel) {
                return 1;
            }
        }
        return 0;
    }

    int myfunction(int y, int m, int q)//Function for determining the day of the week.
    {
        int h;
        if (m == 1) {
            m = 13;
            y = y - 1;
        }
        if (m == 2) {
            m = 14;
            y = y - 1;
        }
        h = (q + (26 * (m + 1) / 10) + y + (y / 4) + 6 * (y / 100) + (y / 400)) % 7;
        return h;
    }

    int limitmonth(String str, String yr) {
        if (str.equals("Jan")) {
            return 31;
        } else if (str.equals("Feb")) {
            int y = Integer.parseInt(yr);
            if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0)) {
                return 29;
            }
            return 28;
        } else if (str.equals("Mar")) {
            return 31;
        } else if (str.equals("Apr")) {
            return 30;
        } else if (str.equals("May")) {
            return 31;
        } else if (str.equals("Jun")) {
            return 30;
        } else if (str.equals("Jul")) {
            return 31;
        } else if (str.equals("Aug")) {
            return 31;
        } else if (str.equals("Sep")) {
            return 30;
        } else if (str.equals("Oct")) {
            return 31;
        } else if (str.equals("Nov")) {
            return 30;
        } else if (str.equals("Dec")) {
            return 31;
        }
        return 0;

    }

    int monthnum(String str) {
        if (str.equals("Jan")) {
            return 1;
        } else if (str.equals("Feb")) {
            return 2;
        } else if (str.equals("Mar")) {
            return 3;
        } else if (str.equals("Apr")) {
            return 4;
        } else if (str.equals("May")) {
            return 5;
        } else if (str.equals("Jun")) {
            return 6;
        } else if (str.equals("Jul")) {
            return 7;
        } else if (str.equals("Aug")) {
            return 8;
        } else if (str.equals("Sep")) {
            return 9;
        } else if (str.equals("Oct")) {
            return 10;
        } else if (str.equals("Nov")) {
            return 11;
        } else if (str.equals("Dec")) {
            return 12;
        }
        return 0;
    }

    void calendermake() {
        Calendar current = Calendar.getInstance();
        Label label[] = new Label[7];
        for (int i = 0; i < 7; i++) {
            String str;
            if (i == 0) {
                str = "SAT";
            } else if (i == 1) {
                str = "SUN";
            } else if (i == 2) {
                str = "MON";
            } else if (i == 3) {
                str = "TUE";
            } else if (i == 4) {
                str = "WED";
            } else if (i == 5) {
                str = "THU";
            } else {
                str = "FRI";
            }
            label[i] = new Label(str);
            label[i].setPrefWidth(75);
            label[i].setAlignment(Pos.CENTER);
            label[i].setTextAlignment(TextAlignment.CENTER);
            label[i].getStyleClass().add("label2");
        }
        calender = new GridPane();
        int c = myfunction(Integer.parseInt(year.getText()), monthnum(month.getText()), 1);
        for (int i = 0; i < 7; i++) {
            calender.getColumnConstraints().add(new ColumnConstraints(75));
            calender.add(label[i], i, 0);
        }
        final Button date[] = new Button[31];
        String months[] = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        for (int i = 0; i <= 30; i++) {
            date[i] = new Button(String.valueOf(i + 1));
            date[i].setPrefSize(75, 75);
            date[i].getStyleClass().add("button2");
            if (month.getText().equals(months[current.get(Calendar.MONTH)]) && Integer.parseInt(year.getText()) == current.get(Calendar.YEAR) && i + 1 == current.get(Calendar.DATE)) {
                date[i].getStyleClass().add("button3");
            }
            //System.out.println(month.getText().substring(0,3));
            int flag = isevent(month.getText().substring(0, 3), year.getText(), i + 1);
            if (isevent(month.getText().substring(0, 3), year.getText(), i + 1) == 1) {
                date[i].getStyleClass().add("eventhas");
            }
        }
        for (int i = 0; i <= 30; i++) {
            final int idx = i;
            final Button mybutton = date[i];
            date[i].addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    mybutton.setEffect(new DropShadow(20, Color.CHOCOLATE));
                    scene.setCursor(Cursor.HAND);
                }
            });
            date[i].addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    mybutton.setEffect(null);
                    scene.setCursor(Cursor.DEFAULT);
                }
            });
            date[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String starter = String.valueOf(idx + 1) + "/" + String.valueOf(monthnum(month.getText())) + "/" + year.getText();
                    MeetingScheduler.datemeeting = new Datemeetingwin(starter);
                    try {
                        MeetingScheduler.datemeeting.start();
                        MeetingScheduler.cal.close();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
            //date[i].setTextAlignment(TextAlignment.LEFT);
        }
        //date[i].setTextAlignment(TextAlignment.LEFT);
        //date[i].setTextAlignment(TextAlignment.LEFT);
        int k = 0;
        for (int i = c; i < 7; i++) {
            //calender.getColumnConstraints().add(new ColumnConstraints(50));
            calender.add(date[k], i, 1);
            k++;
        }
        int limit = limitmonth(month.getText(), year.getText());
        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                //GridPane.setConstraints(date[k],j, i);
                calender.add(date[k], j, i);
                k++;
                if (k > (limit - 1)) {
                    break;
                }
            }
            if (k > (limit - 1)) {
                break;
            }

        }
        VBox.setMargin(calender, new Insets(10, 0, 0, 0));
        root.getChildren().add(calender);
        calender.setAlignment(Pos.CENTER);
    }

    public void start() {
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        for (int i = 0; i < get.size(); i++) {
//            get.get(i).print();
//        }
        StringTokenizer st = new StringTokenizer(new Date().toString());
        day = st.nextToken();
        mon = st.nextToken();
        dat = st.nextToken();
        time = st.nextToken();
        st.nextToken();
        y = st.nextToken();
        parentroot = new SplitPane();
        parentroot.setDividerPositions(.8);
        root = new VBox();
        options = new VBox();
        first = new HBox();
        //first.setPrefSize(500,42);
        first.setPrefHeight(60);
        second = new HBox();
        //second.setPrefSize(500,68);
        second.setPrefHeight(100);
        previous = new Button("Previous");
        previous.setPrefSize(141, 60);
        previous.getStyleClass().add("button1");
        next = new Button("Next");
        next.setPrefSize(141, 60);
        next.getStyleClass().add("button1");
        firstregion = new Region();
        firstregion.setPrefSize(91, 60);
        event = new Button("New Event");
        event.setPrefSize(200, 72);
        event.getStyleClass().add("options");
        month = new Label(mon);
        year = new Label(y);
        labelb = new Region();
        labela = new Region();
        labelb.setPrefSize(148, 68);
        labela.setPrefSize(168, 68);
        month.setPrefSize(81, 40);
        HBox.setMargin(previous, new Insets(0, 0, 0, 150));
        HBox.setMargin(next, new Insets(0, 0, 0, 10));
        HBox.setMargin(month, new Insets(30, 0, 0, 140));
        HBox.setMargin(year, new Insets(30, 0, 0, 40));
        month.setFont(new Font("Times New Roman", 18));
        year.setFont(new Font("Times New Roman", 18));
        year.setPrefSize(81, 40);
        month.setAlignment(Pos.CENTER);
        year.setAlignment(Pos.CENTER);
        month.setTextAlignment(TextAlignment.RIGHT);
        year.setTextAlignment(TextAlignment.RIGHT);
        month.getStyleClass().add("label1");
        year.getStyleClass().add("label1");
        digital = new DigitalClock();
        HBox.setMargin(digital, new Insets(0, 0, 0, 100));
        digital.setAlignment(Pos.CENTER);
        digital.setAlignment(Pos.CENTER);
        digital.setPrefSize(110, 60);
        digital.getStyleClass().add("digiclock");
        String userssize = "0";
        String notifsize = "0";
        String meetsize = "0";
        String histosize = "0";
        String recsize = "0";
        String sensize = "0";
        userssize = String.valueOf(MeetingScheduler.receive.ob.usernumbers.usernames.size());
        notifsize = String.valueOf(MeetingScheduler.receive.ob.notification.size());
        meetsize = String.valueOf(MeetingScheduler.receive.ob.meetinglist.size());
        histosize = String.valueOf(MeetingScheduler.receive.ob.history.size());
        recsize = String.valueOf(MeetingScheduler.receive.ob.meetingrequestfrom.size());
        sensize = String.valueOf(MeetingScheduler.receive.ob.meetingrequestto.size());
        users = new Button("USERS" + "(" + userssize + ")");
        users.getStyleClass().add("options");
        //Image imagesent = new Image(getClass().getResourceAsStream("sent.png"));
        sent = new Button("SENT");
        if (!sensize.equals("0")) {
            sent.setText("SENT" + "(" + sensize + ")");
        }
        //sent.setGraphicTextGap(10);
        sent.getStyleClass().add("options");
        received = new Button("RECEIVED");
        if (!recsize.equals("0")) {
            received.setText("RECEIVED" + "(" + recsize + ")");
        }
        received.getStyleClass().add("options");
        notifications = new Button("NOTIFICATIONS");
        if (!notifsize.equals("0")) {
            notifications.setText("NOTIFICATIONS" + "(" + notifsize + ")");
        }
        notifications.getStyleClass().add("options");
        history = new Button("HISTORY");
        if (!histosize.equals("0")) {
            history.setText("HISTORY" + "(" + histosize + ")");
        }
        history.getStyleClass().add("options");
        //Image imagerefresh = new Image(getClass().getResourceAsStream("refresh.png"));
        refreshed = new Button("REFRESH");
        //refreshed.setGraphicTextGap(10);
        refreshed.getStyleClass().add("options");
        logout = new Button("LOG OUT");
        logout.getStyleClass().add("options");
        Meetings = new Button("Meetings");
        if (!meetsize.equals("0")) {
            Meetings.setText("Meetings" + "(" + meetsize + ")");
        }
        Meetings.setPrefSize(200, 72);
        Meetings.getStyleClass().add("options");
        close = new Button("CLOSE");
        close.getStyleClass().add("options");
        users.setPrefSize(200, 72);
        sent.setPrefSize(200, 72);
        received.setPrefSize(200, 72);
        notifications.setPrefSize(200, 72);
        history.setPrefSize(200, 72);
        refreshed.setPrefSize(200, 72);
        logout.setPrefSize(200, 72);
        close.setPrefSize(200, 72);
        first.getChildren().addAll(previous, next, digital);
        second.getChildren().addAll(labelb, month, year, labela);
//        month.setAlignment(Pos.CENTER);
//        year.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");
        root.getChildren().addAll(first, second);
        options.getChildren().addAll(event, Meetings, users, sent, received, notifications, history, refreshed, logout, close);
        parentroot.getItems().addAll(root, options);
        calendermake();
        previous.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                previous.setEffect(new DropShadow(20, Color.WHEAT));
                scene.setCursor(Cursor.HAND);
            }
        });
        next.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                next.setEffect(new DropShadow(20, Color.WHEAT));
                scene.setCursor(Cursor.HAND);
            }
        });
        previous.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                previous.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        next.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                next.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        event.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                event.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        users.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                users.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        Meetings.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Meetings.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        received.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                received.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        history.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                history.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        sent.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                sent.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        refreshed.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                refreshed.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        notifications.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                notifications.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        close.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                close.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        logout.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                logout.setEffect(new DropShadow(20, Color.CORAL));
                scene.setCursor(Cursor.HAND);
            }
        });
        refreshed.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                refreshed.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        users.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                users.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        Meetings.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Meetings.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        received.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                received.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        sent.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                sent.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        notifications.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                notifications.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        history.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                history.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        logout.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                logout.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                close.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        event.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                event.setEffect(null);
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        event.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.cal.close();
                MeetingScheduler.sen = new SendWindow();
                try {
                    MeetingScheduler.sen.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        users.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.userwin = new UsersWindow();
                try {
                    MeetingScheduler.cal.close();
                    MeetingScheduler.userwin.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        history.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.historywin = new Historywin();
                try {
                    MeetingScheduler.cal.close();
                    MeetingScheduler.historywin.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        notifications.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.notif = new Notificationwin();
                try {
                    MeetingScheduler.cal.close();
                    MeetingScheduler.notif.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        refreshed.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                String msg = "Type:" + "Refresh" + '\n' + "Username:" + MeetingScheduler.username + '\n' + "Refreshing:" + "Do It";
                MeetingScheduler.ring.cancel();
                new Clientsend(MeetingScheduler.nc, msg);
                MeetingScheduler.cal.close();
                LoadingWindow2 window = new LoadingWindow2();
                try {
                    window.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(5));
                delay.setOnFinished(event2 -> {
                    MeetingScheduler.cal = new Mainwindow();
                    MeetingScheduler.cal.start();
                    window.close();
                    MeetingScheduler.ring = new Alarmring();

                });
                delay.play();
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                //String msg = "Type:" + "Refresh" + '\n' + "Username:" + MeetingScheduler.username + '\n' + "Refreshing:" + "Do It";
                //new Clientsend(MeetingScheduler.nc, msg);
                MeetingScheduler.ring.cancel();
                MeetingScheduler.cal.close();
                Loadingwin window = new Loadingwin();
                String sendmsg = "Type:" + "CloseConnection" + '\n' + "Username:" + MeetingScheduler.username + '\n'
                            + "meetdate:" + "Do It";
                new Clientsend(MeetingScheduler.nc, sendmsg);
                try {
                    window.start();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
                delay.setOnFinished(event2 -> {
                    //MeetingScheduler.receive.ob.clear();
                    MeetingScheduler.stage1.show();
                    window.close();

                });
                delay.play();
            }
        });
        Meetings.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MeetingScheduler.meetinglistscene = new Meetinglistwin();
                try {
                    MeetingScheduler.cal.close();
                    MeetingScheduler.meetinglistscene.start();
                } catch (Exception ex) {
                    System.out.println(ex + "shjbds");
                }
            }
        });
        close.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("You want to Close This Application");
                alert.setContentText("Are you Sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    String sendmsg = "Type:" + "CloseConnection" + '\n' + "Username:" + MeetingScheduler.username + '\n'
                            + "meetdate:" + "Do It";
                    new Clientsend(MeetingScheduler.nc, sendmsg);
                    System.exit(0);
                } else {

                }
            }
        });
        sent.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int size = 0;
                size = MeetingScheduler.receive.ob.meetingrequestto.size();
                MeetingScheduler.sent = new SentRequest(size);

                MeetingScheduler.cal.close();
                MeetingScheduler.sent.start();

            }
        });
        received.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int size = 0;
                size = MeetingScheduler.receive.ob.meetingrequestfrom.size();

                MeetingScheduler.recreq = new Received(size);

                MeetingScheduler.cal.close();
                MeetingScheduler.recreq.start();

            }
        });
        previous.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Hello World!");
                if (month.getText().equals("Jan")) {
                    month.setText("Dec");
                    year.setText(String.valueOf(Integer.parseInt(year.getText()) - 1));
                } else if (month.getText().equals("Feb")) {
                    month.setText("Jan");
                } else if (month.getText().equals("Mar")) {
                    month.setText("Feb");
                } else if (month.getText().equals("Apr")) {
                    month.setText("Mar");
                } else if (month.getText().equals("May")) {
                    month.setText("Apr");
                } else if (month.getText().equals("Jun")) {
                    month.setText("May");
                } else if (month.getText().equals("Jul")) {
                    month.setText("Jun");
                } else if (month.getText().equals("Aug")) {
                    month.setText("Jul");
                } else if (month.getText().equals("Sep")) {
                    month.setText("Aug");
                } else if (month.getText().equals("Oct")) {
                    month.setText("Sep");
                } else if (month.getText().equals("Nov")) {
                    month.setText("Oct");
                } else if (month.getText().equals("Dec")) {
                    month.setText("Nov");
                }
                root.getChildren().remove(calender);
                calendermake();
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Hello World!");
                if (month.getText().equals("Dec")) {
                    month.setText("Jan");
                    year.setText(String.valueOf(Integer.parseInt(year.getText()) + 1));
                } else if (month.getText().equals("Nov")) {
                    month.setText("Dec");
                } else if (month.getText().equals("Oct")) {
                    month.setText("Nov");
                } else if (month.getText().equals("Sep")) {
                    month.setText("Oct");
                } else if (month.getText().equals("Aug")) {
                    month.setText("Sep");
                } else if (month.getText().equals("Jul")) {
                    month.setText("Aug");
                } else if (month.getText().equals("Jun")) {
                    month.setText("Jul");
                } else if (month.getText().equals("May")) {
                    month.setText("Jun");
                } else if (month.getText().equals("Apr")) {
                    month.setText("May");
                } else if (month.getText().equals("Mar")) {
                    month.setText("Apr");
                } else if (month.getText().equals("Feb")) {
                    month.setText("Mar");
                } else if (month.getText().equals("Jan")) {
                    month.setText("Feb");
                }
                root.getChildren().remove(calender);
                calendermake();
            }
        });
        stage = new Stage();
        scene = new Scene(parentroot, 1000, 700);
        URL url = this.getClass().getResource("/TextContent/style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);
        //scene.getStylesheets().add("style.css");
//        primaryStage.setTitle("Hello World!");
        stage.setTitle(MeetingScheduler.username + " " + "Meeting Scheduler");
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

class DigitalClock extends Label {

    public DigitalClock() {
        bindToTime();
    }

    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Calendar time = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        //setText(simpleDateFormat.format(time.getTime()));
                        if (time.get(Calendar.HOUR_OF_DAY) == 12) {
                            setText(simpleDateFormat.format(time.getTime()) + " " + "PM");
                        } else if (time.get(Calendar.HOUR_OF_DAY) < 12) {
                            setText(simpleDateFormat.format(time.getTime()) + " " + "AM");
                        } else {
                            String hr = String.valueOf(time.get(Calendar.HOUR_OF_DAY) - 12);
                            if (Integer.parseInt(hr) / 10 == 0) {
                                hr = "0" + hr;
                            }
                            String mn = String.valueOf(time.get(Calendar.MINUTE));
                            if (Integer.parseInt(mn) / 10 == 0) {
                                mn = "0" + mn;
                            }
                            String sd = String.valueOf(time.get(Calendar.SECOND));
                            if (Integer.parseInt(sd) / 10 == 0) {
                                sd = "0" + sd;
                            }
                            setText(hr + ":" + mn + ":" + sd + " " + "PM");
                        }
                    }
                }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
