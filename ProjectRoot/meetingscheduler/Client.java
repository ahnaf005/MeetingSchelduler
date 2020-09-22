package meetingscheduler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.AudioClip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

class Clientreceive implements Runnable {

    static sendobject get;
    //sendobject s;
    boolean special = false;
    boolean special1 = false;
    networkutil nc;
    private Thread th;
    sendobject ob;
    static boolean msg = false;

    Clientreceive(networkutil nc) {
        this.nc = nc;
        this.th = new Thread(this);
        get = new sendobject(null);
        th.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int f = 0;
                sendobject s = (sendobject) nc.read();
                //System.out.println("Pailam");
                //s.readObject(nc.ois);
//                try {
//                    System.out.println(s.type);
//
//                } catch (Exception e) {
//                    System.out.println("Obostha Kharap");
//                }
                if (s.type != null) {
                    if (s.type.equals("Clear Your List")) {
                        //ob.clear();
                        //System.out.println(ob.size());
                        //System.out.println("Clear Your List");
                    } else if (s.type.equals("Request was Already Cancelled")) {
                        special = true;
                        Platform.runLater(() -> {
                            try {
                                //Thread.sleep(4000);
                                //window.close();
                                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                                alert1.setTitle("Error Dialog");
                                alert1.setHeaderText("This Request was Already Cancelled by the Sender");
                                alert1.setContentText("Action Not Valid");
                                alert1.showAndWait();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        });

                        //System.out.println("In client");
                    } else if (s.type.equals("Request Already Responded")) {
                        special1 = true;
                        Platform.runLater(() -> {
                            try {
                                //Thread.sleep(4000);
                                //window.close();
                                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                                alert1.setTitle("Error Dialog");
                                alert1.setHeaderText("This Request was Already responded by the Sender");
                                alert1.setContentText("Please Refresh and Check Notifications");
                                alert1.showAndWait();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        });

                        //System.out.println("In client");
                    } else if (s.type.equals("LogIn Successful") || s.type.equals("Invalid LogIn")) {
                        //System.out.println("Thik Ase");
                        get.type = s.type;
                        msg = true;
                        //System.out.println(CreateAccountController.matha.t.isAlive());
                        //System.out.println(CreateAccountController.matha.t.getPriority());
//                      //CreateAccountController.matha.myresume();                        
                        FXMLDocumentController.msgthread.myresume();
                        //System.out.println(s.type);
                    } else if (s.type.equals("Account Creation Successful") || s.type.equals("Username Taken")) {
                        get.type = s.type;
                        msg = true;
                        //System.out.println(CreateAccountController.matha.t.isAlive());
                        //System.out.println(CreateAccountController.matha.t.getPriority());
                        CreateAccountController.matha.myresume();
                    } else if (s.type.equals("NewRequest")) {
                        synchronized (this) {
                            sendobject tmp;
                            tmp = s;
                            ob = new sendobject(tmp.ob.username, tmp.ob.usernumbers, tmp.ob.meetinglist, tmp.ob.meetingrequestfrom, tmp.ob.meetingrequestto, tmp.ob.history, tmp.ob.notification);
                        }
                        Platform.runLater(() -> {
                            try {
                                //Thread.sleep(4000);
                                //window.close();
                                URL url = this.getClass().getResource("/BinaryContent/Notiftones.mp3");
                                if (url == null) {
                                    System.out.println("Resource not found. Aborting.");
                                    System.exit(-1);

                                }
                                String css = url.toExternalForm();
                                AudioClip plonkSound = new AudioClip(css);
                                plonkSound.play();
                                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                alert1.setTitle("New Requests");
                                alert1.setHeaderText("You Have new Requests");
                                alert1.setContentText("Please Refresh or Change window");
                                //alert1.showAndWait();
                                Optional<ButtonType> result = alert1.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    plonkSound.stop();
                                } else {
                                    plonkSound.stop();

                                }
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        });
                    } else if (s.type.equals("NewNotifications")) {
                        synchronized (this) {
                            sendobject tmp;
                            tmp = s;
                            ob = new sendobject(tmp.ob.username, tmp.ob.usernumbers, tmp.ob.meetinglist, tmp.ob.meetingrequestfrom, tmp.ob.meetingrequestto, tmp.ob.history, tmp.ob.notification);
                        }
                        Platform.runLater(() -> {
                            try {
                                //Thread.sleep(4000);
                                //window.close();
                                URL url = this.getClass().getResource("/BinaryContent/Notiftones.mp3");
                                if (url == null) {
                                    System.out.println("Resource not found. Aborting.");
                                    System.exit(-1);

                                }
                                String css = url.toExternalForm();
                                AudioClip plonkSound = new AudioClip(css);
                                plonkSound.play();
                                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                alert1.setTitle("New Notifications");
                                alert1.setHeaderText("You Have New Notifications");
                                alert1.setContentText("Please Refresh or Change window");
                                //alert1.showAndWait();
                                Optional<ButtonType> result = alert1.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    plonkSound.stop();
                                } else {
                                    plonkSound.stop();

                                }
//                                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Notiftone.mp3"));
//                                AudioFormat af=audioInputStream.getFormat();
//                                int size=(int)(af.getFrameSize()* audioInputStream.getFrameLength());
//                                byte[] audio=new byte[size];
//                                DataLine.Info info=new DataLine.Info(Clip.class, af, size);
//                                audioInputStream.read(audio, 0, size);
//                                for(int i=0; i < 10; i++) {
//                                     Clip clip = (Clip) AudioSystem.getLine(info);   
//                                     clip.open(af, audio, 0, size);
//                                     clip.start();
//                                }
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        });
                    }
                } else {
                    sendobject tmp;
                    tmp = s;
                    ob = new sendobject(tmp.username, tmp.usernumbers, tmp.meetinglist, tmp.meetingrequestfrom, tmp.meetingrequestto, tmp.history, tmp.notification);
                    f = 1;
                }
//                if (f == 1) {
//                    for (int i = 0; i < ob.size(); i++) {
//                        String user = ob.get(i).username;
//                        System.out.println(user);
//                        System.out.println("meetinglist:");
//                        if (!ob.get(i).meetinglist.isEmpty()) {
//                            for (int j = 0; j < ob.get(i).meetinglist.size(); j++) {
//                                System.out.println(ob.get(i).meetinglist);
//                            }
//                        }
//                        System.out.println("meetingrequestfrom");
//                        if (!ob.get(i).meetingrequestfrom.isEmpty()) {
//                            for (int j = 0; j < ob.get(i).meetingrequestfrom.size(); j++) {
//                                System.out.println(ob.get(i).meetingrequestfrom);
//                            }
//                        }
//                        System.out.println("meetingrequesto");
//                        if (!ob.get(i).meetingrequestto.isEmpty()) {
//                            for (int j = 0; j < ob.get(i).meetingrequestto.size(); j++) {
//                                System.out.println(ob.get(i).meetingrequestto);
//                            }
//                        }
//                    }
//                }

            }
        } catch (Exception e) {
            System.out.println("Network Terminated");
        }
        nc.closeConnection();

    }
}

//
//    String getmsg()
//    {
//        return s.type;
//    }
class Clientsend implements Runnable {

    networkutil nc;
    private Thread th;
    String msg;

    Clientsend(networkutil nc, String msg) {
        this.nc = nc;
        this.th = new Thread(this);
        this.msg = msg;
        th.start();
    }

    @Override
    public void run() {
        send();
    }

    public void send() {
        nc.write(msg);
//        Scanner input=new Scanner(System.in);
//        System.out.println("1."+"LogIn");
//        System.out.println("2."+"CreateAccount");
//        System.out.println("Enter your Choice");
//        int choice=input.nextInt();
//        if(choice==1)
//        {
//            System.out.println("Enter username: ");
//            System.out.println("Enter PassWord: ");
//            String username=input.next();
//            String password=input.next();
//            String sendmsg="Type:"+"LogIn"+'\n'+"Username:"+username+'\n'+"PassWord:"+password;
//            nc.write(sendmsg);
//        }
//        else if(choice==2)
//        {
//            System.out.println("Enter username: ");
//            System.out.println("Enter PassWord: ");
//            String username=input.next();
//            String password=input.next();
//            String sendmsg="Type:"+"CreateAccount"+'\n'+"Username:"+username+'\n'+"PassWord:"+password;
//            nc.write(sendmsg);
    }
}

public class Client {

}
