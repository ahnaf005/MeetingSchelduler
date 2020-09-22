package meetingscheduler;
//"05.00 Min", "10.00 Min", "15.00 Min", "20.00 Min", "30.00 Min", "45.00 Min", "01.00 Hour", 
//"01.50 Hour", "02.00 Hour", "02.50 Hour", "03.50 Hour", 

import java.net.URL;
import java.util.Calendar;
import java.util.Optional;
import java.util.StringTokenizer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.media.AudioClip;
public class Alarmring implements Runnable {

    volatile boolean alert = true;
    Thread t;
    volatile boolean cancel = false;

    Alarmring() {
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            while (!cancel) {
                int tmpsize;
                tmpsize = MeetingScheduler.receive.ob.meetinglist.size();
                final int size = tmpsize;
                for (int i = 0; i < size; i++) {
                    final int idx2 = i;
                    String date = MeetingScheduler.receive.ob.meetinglist.get(i).ob.date;
                    String time = MeetingScheduler.receive.ob.meetinglist.get(i).ob.time;
                    String subtracttime = MeetingScheduler.receive.ob.meetinglist.get(i).reminder;
                    StringTokenizer st = new StringTokenizer(date);
                    int formatdate = Integer.parseInt(st.nextToken("/"));
                    int formatmonth = Integer.parseInt(st.nextToken("/"));
                    int formatyear = Integer.parseInt(st.nextToken("/"));
                    StringTokenizer start = new StringTokenizer(time);
                    String starttime1 = start.nextToken("-");
                    StringTokenizer st1 = new StringTokenizer(starttime1);
                    String hour = st1.nextToken(":");
                    String minute1 = st1.nextToken(":");
                    int hourf = 0;
                    int minutef = 0;
                    if (starttime1.contains("PM")) {
                        String minute = minute1.substring(0, minute1.indexOf("PM") - 1);
                        if (!hour.equals("12")) {
                            hourf = Integer.parseInt(hour) + 12;
                        } else {
                            hourf = Integer.parseInt(hour);
                        }
                        minutef = Integer.parseInt(minute);
                    } else if (starttime1.contains("AM")) {
                        String minute = minute1.substring(0, minute1.indexOf("AM") - 1);
                        hourf = Integer.parseInt(hour);
                        minutef = Integer.parseInt(minute);
                    }
                    Calendar now = Calendar.getInstance();
                    Calendar requested = Calendar.getInstance();
                    // now.set(Calendar.SECOND, 0);
                    //now.set(Calendar.MILLISECOND, 0);
                    //System.out.println(now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + "/" + now.get(Calendar.YEAR) + "/");
                    requested.set(Calendar.DATE, formatdate);
                    requested.set(Calendar.MONTH, formatmonth - 1);
                    requested.set(Calendar.YEAR, formatyear);
                    requested.set(Calendar.HOUR_OF_DAY, hourf);
                    requested.set(Calendar.MINUTE, minutef);
                    requested.set(Calendar.SECOND, 10);
                    //requested.add(Calendar.MINUTE,13);
                    //requested.set(Calendar.MILLISECOND, 0);
                    Calendar alarm = Calendar.getInstance();
                    alarm.set(Calendar.DATE, formatdate);
                    alarm.set(Calendar.MONTH, formatmonth - 1);
                    alarm.set(Calendar.YEAR, formatyear);
                    alarm.set(Calendar.HOUR_OF_DAY, hourf);
                    alarm.set(Calendar.MINUTE, minutef);
                    alarm.set(Calendar.SECOND, 10);
                    //alarm.set(Calendar.MILLISECOND, 0);
                    if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("05.00 Min")) {
                        alarm.add(Calendar.MINUTE, -5);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("10.00 Min")) {
                        alarm.add(Calendar.MINUTE, -10);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("15.00 Min")) {
                        alarm.add(Calendar.MINUTE, -15);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("20.00 Min")) {
                        alarm.add(Calendar.MINUTE, -20);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("30.00 Min")) {
                        alarm.add(Calendar.MINUTE, -30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("45.00 Min")) {
                        alarm.add(Calendar.MINUTE, -45);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("01.00 Hour")) {
                        alarm.add(Calendar.HOUR, -1);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("01.50 Hour")) {
                        alarm.add(Calendar.HOUR, -1);
                        alarm.add(Calendar.MINUTE, -30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("02.00 Hour")) {
                        alarm.add(Calendar.HOUR, -2);
                        //alarm.add(Calendar.MINUTE,-30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("02.50 Hour")) {
                        alarm.add(Calendar.HOUR, -2);
                        alarm.add(Calendar.MINUTE, -30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("03.00 Hour")) {
                        alarm.add(Calendar.HOUR, -3);
                        //alarm.add(Calendar.MINUTE,-30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("03.50 Hour")) {
                        alarm.add(Calendar.HOUR, -3);
                        alarm.add(Calendar.MINUTE, -30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("04.00 Hour")) {
                        alarm.add(Calendar.HOUR, -4);
                        //alarm.add(Calendar.MINUTE,-30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("04.30 Hour")) {
                        alarm.add(Calendar.HOUR, -4);
                        alarm.add(Calendar.MINUTE, -30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("05.00 Hour")) {
                        alarm.add(Calendar.HOUR, -5);
                        //alarm.add(Calendar.MINUTE,-30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("06.00 Hour")) {
                        alarm.add(Calendar.HOUR, -6);
                        //alarm.add(Calendar.MINUTE,-30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("07.00 Hour")) {
                        alarm.add(Calendar.HOUR, -7);
                        //alarm.add(Calendar.MINUTE,-30);
                    } else if (MeetingScheduler.receive.ob.meetinglist.get(i).reminder.equals("08.00 Hour")) {
                        alarm.add(Calendar.HOUR, -8);
                        //alarm.add(Calendar.MINUTE,-30);
                    }
                    URL url = this.getClass().getResource("/BinaryContent/alarm.mp3");
                    if (url == null) {
                        System.out.println("Resource not found. Aborting.");
                        System.exit(-1);

                    }
                    String css = url.toExternalForm();
                    AudioClip plonkSound = new AudioClip(css);
                    if (alert) {
                        if (now.equals(alarm)) {
                            alert = false;
                            Platform.runLater(() -> {
                                plonkSound.play();
                                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                                alert1.setTitle("You have meeting to attend!");
                                alert1.setHeaderText("Meeting" + " " + MeetingScheduler.receive.ob.meetinglist.get(idx2).ob.meetingname + " " + "with " + MeetingScheduler.receive.ob.meetinglist.get(idx2).ob.meetername);
                                alert1.setContentText(subtracttime + " " + "later");
                                Optional<ButtonType> result = alert1.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    plonkSound.stop();
                                    alert = true;
                                } else {
//                            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(60));
//                            delay.setOnFinished(event2
//                                    -> {
//                                    plonkSound.stop();
//                            });
//                            delay.play();
                                    plonkSound.stop();
                                    alert = true;

                                }

                            });
                        }
                    }
                    if (alert) {
                        if (now.equals(requested)) {
                            alert = false;
                            Platform.runLater(() -> {
                                plonkSound.play();
                                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                                alert1.setTitle("You have meeting to attend!");
                                alert1.setHeaderText("Meeting"+" "+MeetingScheduler.receive.ob.meetinglist.get(idx2).ob.meetingname + " " + "with " + MeetingScheduler.receive.ob.meetinglist.get(idx2).ob.meetername);
                                alert1.setContentText("Holding" + " " + "Now");
                                Optional<ButtonType> result = alert1.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    plonkSound.stop();
                                    alert = true;
                                } else {
                                    alert = true;
                                    plonkSound.stop();
                                }

                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void cancel() {
        cancel = true;
    }

}
