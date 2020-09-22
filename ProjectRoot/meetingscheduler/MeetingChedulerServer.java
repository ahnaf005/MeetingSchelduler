package meetingscheduler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server implements Runnable {
    ArrayList<sendobject> sent;
    ArrayList<userinfo> Ob;
    networkutil nc;
    Thread th;
    String msg[];

    Server(networkutil nc) {
        this.nc = nc;
        this.th = new Thread(this);
        Ob = new ArrayList<userinfo>();
        sent = new ArrayList<sendobject>();
        th.start();
    }

    int time(String a) {
        StringTokenizer st1 = new StringTokenizer(a);
        String hour = st1.nextToken(":");
        String minute1 = st1.nextToken(":");
        int hourf;
        if (a.contains("PM")) {
            String minute = minute1.substring(0, minute1.indexOf("PM"));
            if (!hour.equals("12")) {
                hourf = Integer.parseInt(hour) + 12;
            } else {
                hourf = Integer.parseInt(hour);
            }
            int minutef = Integer.parseInt(minute);
            return hourf * 60 + minutef;
        } else if (a.contains("AM")) {
            String minute = minute1.substring(0, minute1.indexOf("AM"));
            hourf = Integer.parseInt(hour);
            int minutef = Integer.parseInt(minute);
            return hourf * 60 + minutef;

        }
        return 1;
    }

    public int checkcollison(String a, String b) {
        StringTokenizer st1 = new StringTokenizer(a);
        StringTokenizer st2 = new StringTokenizer(b);
        String start1 = st1.nextToken("-");
        String end1 = st1.nextToken("-");
        String start2 = st2.nextToken("-");
        String end2 = st2.nextToken("-");
        int start1f = time(start1);
        int start2f = time(start2);
        int end1f = time(end1);
        int end2f = time(end2);
        int dif1 = end1f - start1f;
        int dif2 = end2f - start2f;
        //System.out.println(start1+" "+start2+" "+end1+" "+end2);
        //System.out.println(start1f+" "+start2f+" "+end1f+" "+end2f);
        if (start1f <= start2f && start2f < end1f) {
            return 0;
        } else if (start2f <= start1f && start1f < end2f) {
            return 0;
        }
        return 1;
    }

    public sendobject instant(String user) {
        ArrayList<Finalmeeting> meetinglist = new ArrayList<Finalmeeting>();
        ArrayList<meeting> meetingrequestfrom = new ArrayList<meeting>();
        ArrayList<meeting> meetingrequestto = new ArrayList<meeting>();
        ArrayList<meeting> history = new ArrayList<meeting>();
        ArrayList<Notifications> notification = new ArrayList<Notifications>();
        UserNumbers usernumbers = findinfo();
        meetinglist = finalmeetinglist("TextContent/"+user + "mf.txt");
        meetingrequestfrom = deleterqst("TextContent/"+user + "rf.txt");
        meetingrequestto = deleterqst("TextContent/"+user + "rt.txt");
        notification = getnotif("TextContent/"+user + "mn.txt");
        history = deleterqst("TextContent/"+user + "mh.txt");
        sendobject pathau = new sendobject(user, usernumbers, meetinglist, meetingrequestfrom, meetingrequestto, history, notification);
        return pathau;
    }

    public UserNumbers findinfo() {
        ArrayList<String> usernames = new ArrayList<String>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < Ob.size(); i++) {
            ArrayList<Finalmeeting> meetinglist = new ArrayList<Finalmeeting>();
            String user = Ob.get(i).username;
            meetinglist = finalmeetinglist("TextContent/"+user + "mf.txt");
            usernames.add(user);
            numbers.add(meetinglist.size());
        }
        UserNumbers usernumbers = new UserNumbers(usernames, numbers);
        return usernumbers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int num = 0;
                File f1 = new File("TextContent/"+"usernumber.txt");
                File f2 = new File("TextContent/"+"userinfo.txt");
                String s = (String) nc.read();
                if (s == null) {
                    break;
                }
                System.out.println(s);
                msg = new String[3];
                msg[0] = s.substring(0, s.indexOf('\n'));
                msg[1] = s.substring(s.indexOf('\n') + 1, s.lastIndexOf('\n'));
                msg[2] = s.substring(s.lastIndexOf('\n') + 1, s.length());
                String type = msg[0].substring(msg[0].indexOf(':') + 1, msg[0].length());
                System.out.println(type);
                if (f1.exists()) {
                    try {
                        FileReader usernumfr = new FileReader("TextContent/"+"usernumber.txt");
                        BufferedReader br = new BufferedReader(usernumfr);
                        num = Integer.parseInt(br.readLine());
                        System.out.println(num);
                        usernumfr.close();
                    } catch (IOException ex) {
                        //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex);
                    }
                }
                if (f2.exists()) {
                    try {
                        userinfo tmp;
                        FileInputStream fis = new FileInputStream("TextContent/"+"userinfo.txt");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        for (int i = 0; i < num; i++) {
                            tmp = (userinfo) ois.readObject();
                            Ob.add(tmp);
                        }
                        ois.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    for (int i = 0; i < Ob.size(); i++) {
                        try {
                            System.out.println(Ob.get(i));
                            String user = Ob.get(i).username;
                            historyverify(user);
                            ArrayList<Finalmeeting> meetinglist = new ArrayList<Finalmeeting>();
                            ArrayList<meeting> meetingrequestfrom = new ArrayList<meeting>();
                            ArrayList<meeting> meetingrequestto = new ArrayList<meeting>();
                            ArrayList<meeting> history = new ArrayList<meeting>();
                            ArrayList<Notifications> notification = new ArrayList<Notifications>();
                            UserNumbers usernumbers = findinfo();
                            meetinglist = finalmeetinglist("TextContent/"+user + "mf.txt");
                            meetingrequestfrom = deleterqst("TextContent/"+user + "rf.txt");
                            meetingrequestto = deleterqst("TextContent/"+user + "rt.txt");
                            notification = getnotif("TextContent/"+user + "mn.txt");
                            history = deleterqst("TextContent/"+user + "mh.txt");
                            sent.add(new sendobject(user, usernumbers, meetinglist, meetingrequestfrom, meetingrequestto, history, notification));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
                if (type.equals("CreateAccount")) {
                    int cflag = 1;
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String password = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    if (f2.exists()) {
                        for (int i = 0; i < Ob.size(); i++) {
                            if (username.equals(Ob.get(i).username)) {
                                cflag = 0;
                                break;
                            }
                        }
                    }
                    if (cflag == 1) {
                        try {
                            nc.write(new sendobject("Account Creation Successful"));
                            //new sendobject("Account Creation Successful").writeObject(nc.oos);
                            System.out.println("success");
                            PrintWriter usernamepw = new PrintWriter("TextContent/"+"usernumber.txt");
                            usernamepw.write(String.valueOf(num + 1));
                            usernamepw.flush();
                            usernamepw.close();
                        } catch (FileNotFoundException ex) {
                            System.out.println(ex);
                        }
                        try {
                            userinfo u1 = new userinfo(username, password);
                            FileOutputStream fos = new FileOutputStream("TextContent/"+"userinfo.txt");
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            if (f2.exists()) {
                                for (int i = 0; i < Ob.size(); i++) {
                                    oos.writeObject(Ob.get(i));
                                }
                            }
                            oos.writeObject(u1);
                            oos.flush();
                            oos.close();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else {
                        nc.write(new sendobject("Username Taken"));
                    }

                } else if (type.equals("LogIn")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String password = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    int flag = 0;
                    for (int i = 0; i < num; i++) {
                        if (username.equals(Ob.get(i).username) && password.equals(Ob.get(i).password)) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        nc.write(new sendobject("Invalid LogIn"));
                    } else {
                        nc.write(new sendobject("LogIn Successful"));
                        //nc.write(sent);
                        for (int i = 0; i < sent.size(); i++) {
                            if (sent.get(i).username.equals(username)) {
                                nc.write(sent.get(i));
                                break;
                            }
                        }
                        MeetingChedulerServer.table.put(username, nc);
                    }
                } else if (type.equals("MeetingRequest")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String meetdatetime = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    StringTokenizer st = new StringTokenizer(meetdatetime);
                    String meetername = st.nextToken("$");
                    String date = st.nextToken("$");
                    String time = st.nextToken("$");
                    String meetingname = st.nextToken("$");
                    String location = st.nextToken("$");
                    String description = st.nextToken("$");
                    meeting object = new meeting(meetername, date, time, meetingname, location, description);
                    meeting object2 = new meeting(username, date, time, meetingname, location, description);
                    int mflag = 1;
                    ArrayList<meeting> check = new ArrayList<meeting>();
                    ArrayList<meeting> rt = new ArrayList<meeting>();
                    ArrayList<meeting> rf = new ArrayList<meeting>();
                    try {
                        FileInputStream meetingcheck = new FileInputStream("TextContent/"+meetername + "mf.txt");
                        ObjectInputStream meetingcheckoos = new ObjectInputStream(meetingcheck);
                        meeting tmp;

                        while (true) {
                            try {
                                {
                                    tmp = (meeting) meetingcheckoos.readObject();
                                    check.add(tmp);
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("File not found");
                    }
                    try {
                        FileInputStream rts = new FileInputStream("TextContent/"+username + "rt.txt");
                        ObjectInputStream rtoos = new ObjectInputStream(rts);
                        meeting tmp;

                        while (true) {
                            try {
                                {
                                    tmp = (meeting) rtoos.readObject();
                                    rt.add(tmp);
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("File not found");
                    }
                    try {
                        FileInputStream rfs = new FileInputStream("TextContent/"+meetername + "rf.txt");
                        ObjectInputStream rfoos = new ObjectInputStream(rfs);
                        meeting tmp;

                        while (true) {
                            try {
                                {
                                    tmp = (meeting) rfoos.readObject();
                                    rf.add(tmp);
                                }
                            } catch (Exception e) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("File not found");
                    }
                    for (int i = 0; i < check.size(); i++) {
                        if (check.get(i).date.equals(date)) {
                            if (checkcollison(time, check.get(i).time) == 1) {
                                mflag = 0;
                                nc.write(new sendobject("Sorry,Requested Person has meeting at that time."));
                            }
                        }
                    }
                    mflag = 1;
                    if (mflag == 1) {
                        try {
                            FileOutputStream meetingrtfos = new FileOutputStream("TextContent/"+username + "rt.txt");
                            ObjectOutputStream meetingrtoos = new ObjectOutputStream(meetingrtfos);
                            for (int i = 0; i < rt.size(); i++) {
                                meetingrtoos.writeObject(rt.get(i));
                            }
                            meetingrtoos.writeObject(object);
                            meetingrtoos.flush();
                            meetingrtoos.close();
                            FileOutputStream meetingrffos = new FileOutputStream("TextContent/"+meetername + "rf.txt");
                            ObjectOutputStream meetingrfoos = new ObjectOutputStream(meetingrffos);
                            for (int i = 0; i < rf.size(); i++) {
                                meetingrfoos.writeObject(rf.get(i));
                            }
                            meetingrfoos.writeObject(object2);
                            meetingrfoos.flush();
                            meetingrfoos.close();
                            networkutil nc1=MeetingChedulerServer.table.get(meetername);
                            if(nc1!=null)
                            {
                                sendobject instant=new sendobject("NewRequest",instant(meetername));
                                nc1.write(instant);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
//                    printlist(check);
//                    printlist(rt);
//                    printlist(rf);
                } else if (type.equals("MeetingRequestYes") || type.equals("MeetingRequestNo") || type.equals("MeetingRequestInValid")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String meetdatetime = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    StringTokenizer st = new StringTokenizer(meetdatetime);
                    String meetername = st.nextToken("$");
                    String date = st.nextToken("$");
                    String time = st.nextToken("$");
                    String meetingname = st.nextToken("$");
                    String location = st.nextToken("$");
                    String description = st.nextToken("$");
                    meeting object = new meeting(meetername, date, time, meetingname, location, description);
                    meeting object2 = new meeting(username, date, time, meetingname, location, description);
                    Finalmeeting object3 = new Finalmeeting(object, "01.00 Hour");
                    Finalmeeting object4 = new Finalmeeting(object2, "01.00 Hour");
                    ArrayList<meeting> rf = new ArrayList<meeting>();
                    ArrayList<meeting> rt = new ArrayList<meeting>();
                    ArrayList<Finalmeeting> mf = new ArrayList<Finalmeeting>();
                    ArrayList<Finalmeeting> mt = new ArrayList<Finalmeeting>();
                    ArrayList<Notifications> notifob = new ArrayList<Notifications>();
                    notifob = getnotif("TextContent/"+meetername + "mn.txt");
                    rf = deleterqst("TextContent/"+username + "rf.txt");
                    rt = deleterqst("TextContent/"+meetername + "rt.txt");
                    mf = finalmeetinglist("TextContent/"+username + "mf.txt");
                    mt = finalmeetinglist("TextContent/"+meetername + "mf.txt");
                    Notifications notiifobject1 = new Notifications(object2, "Request", "Accepted");
                    Notifications notiifobject2 = new Notifications(object2, "Request", "Declined");
                    int flag = 0;
                    for (int i = 0; i < rf.size(); i++) {
                        if (object.equals(rf.get(i))) {
                            rf.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < rt.size(); i++) {
                        if (object2.equals(rt.get(i))) {
                            rt.remove(i);
                            flag = 1;
                            break;
                        }
                    }
                    try {
                        FileOutputStream meetingrtfos = new FileOutputStream("TextContent/"+username + "rf.txt");
                        ObjectOutputStream meetingrtoos = new ObjectOutputStream(meetingrtfos);
                        for (int i = 0; i < rf.size(); i++) {
                            meetingrtoos.writeObject(rf.get(i));
                        }
                        meetingrtoos.flush();
                        meetingrtoos.close();
                        FileOutputStream meetingrffos = new FileOutputStream("TextContent/"+meetername + "rt.txt");
                        ObjectOutputStream meetingrfoos = new ObjectOutputStream(meetingrffos);
                        for (int i = 0; i < rt.size(); i++) {
                            meetingrfoos.writeObject(rt.get(i));
                        }
                        meetingrfoos.flush();
                        meetingrfoos.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (type.equals("MeetingRequestYes")) {
                        if (flag == 1) {
                            try {
                                FileOutputStream meetingmffos = new FileOutputStream("TextContent/"+username + "mf.txt");
                                ObjectOutputStream meetingmfoos = new ObjectOutputStream(meetingmffos);
                                for (int i = 0; i < mf.size(); i++) {
                                    meetingmfoos.writeObject(mf.get(i));
                                }
                                meetingmfoos.writeObject(object3);
                                meetingmfoos.flush();
                                meetingmfoos.close();
                                FileOutputStream meetingmtfos = new FileOutputStream("TextContent/"+meetername + "mf.txt");
                                ObjectOutputStream meetingmtoos = new ObjectOutputStream(meetingmtfos);
                                for (int i = 0; i < mt.size(); i++) {
                                    meetingmtoos.writeObject(mt.get(i));
                                }
                                meetingmtoos.writeObject(object4);
                                meetingmtoos.flush();
                                meetingmtoos.close();
                                FileOutputStream meetingn = new FileOutputStream("TextContent/"+meetername + "mn.txt");
                                ObjectOutputStream meetingnfoos = new ObjectOutputStream(meetingn);
                                notifob.add(0, notiifobject1);
                                for (int i = 0; i < notifob.size(); i++) {
                                    meetingnfoos.writeObject(notifob.get(i));
                                }
                                //meetingnfoos.writeObject(notiifobject1);
                                meetingnfoos.flush();
                                meetingnfoos.close();
                                networkutil nc1=MeetingChedulerServer.table.get(meetername);
                                if(nc1!=null)
                                {
                                sendobject instant=new sendobject("NewNotifications",instant(meetername));
                                nc1.write(instant);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            nc.write(new sendobject("Request was Already Cancelled"));
                            System.out.println("hello");

                        }

                    } else if (type.equals("MeetingRequestNo")) {
                        if (flag == 1) {
                            FileOutputStream meetingn = new FileOutputStream("TextContent/"+meetername + "mn.txt");
                            ObjectOutputStream meetingnfoos = new ObjectOutputStream(meetingn);
                            notifob.add(0, notiifobject2);
                            for (int i = 0; i < notifob.size(); i++) {
                                meetingnfoos.writeObject(notifob.get(i));
                            }
                            //meetingnfoos.writeObject(notiifobject2);
                            meetingnfoos.flush();
                            meetingnfoos.close();
                            networkutil nc1=MeetingChedulerServer.table.get(meetername);
                            if(nc1!=null)
                            {
                                sendobject instant=new sendobject("NewNotifications",instant(meetername));
                                nc1.write(instant);
                            }
                        } else {
                            nc.write(new sendobject("Request was Already Cancelled"));
                        }
                    }

                } else if (type.equals("Refresh")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    nc.write(new sendobject("Clear Your List"));
                    for (int i = 0; i < sent.size(); i++) {
                        if (sent.get(i).username.equals(username)) {
                            nc.write(sent.get(i));
                            break;
                        }
                    }
                } else if (type.equals("CancelMeeting")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String meetdatetime = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    StringTokenizer st = new StringTokenizer(meetdatetime);
                    String meetername = st.nextToken("$");
                    String date = st.nextToken("$");
                    String time = st.nextToken("$");
                    String meetingname = st.nextToken("$");
                    String location = st.nextToken("$");
                    String description = st.nextToken("$");
                    //String reminder = st.nextToken("$");
                    ArrayList<Finalmeeting> mf = new ArrayList<Finalmeeting>();
                    ArrayList<Finalmeeting> mt = new ArrayList<Finalmeeting>();
                    ArrayList<Notifications> notifob = new ArrayList<Notifications>();
                    notifob = getnotif("TextContent/"+meetername + "mn.txt");
                    mf = finalmeetinglist("TextContent/"+username + "mf.txt");
                    mt = finalmeetinglist("TextContent/"+meetername + "mf.txt");
                    meeting object2 = new meeting(meetername, date, time, meetingname, location, description);
                    meeting object = new meeting(username, date, time, meetingname, location, description);
                    Notifications notiifobject = new Notifications(object, "Meeting", "Cancelled");
                    int flag = 0;
                    for (int i = 0; i < mf.size(); i++) {
                        if (object2.equals(mf.get(i).ob)) {
                            mf.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < mt.size(); i++) {
                        if (object.equals(mt.get(i).ob)) {
                            mt.remove(i);
                            flag = 1;
                            break;
                        }
                    }
                    try {
                        FileOutputStream meetingrtfos = new FileOutputStream("TextContent/"+username + "mf.txt");
                        ObjectOutputStream meetingrtoos = new ObjectOutputStream(meetingrtfos);
                        for (int i = 0; i < mf.size(); i++) {
                            meetingrtoos.writeObject(mf.get(i));
                        }
                        meetingrtoos.flush();
                        meetingrtoos.close();
                        FileOutputStream meetingrffos = new FileOutputStream("TextContent/"+meetername + "mf.txt");
                        ObjectOutputStream meetingrfoos = new ObjectOutputStream(meetingrffos);
                        for (int i = 0; i < mt.size(); i++) {
                            meetingrfoos.writeObject(mt.get(i));
                        }
                        meetingrfoos.flush();
                        meetingrfoos.close();
                        if (flag == 1) {
                            FileOutputStream meetingn = new FileOutputStream("TextContent/"+meetername + "mn.txt");
                            ObjectOutputStream meetingnfoos = new ObjectOutputStream(meetingn);
                            notifob.add(0, notiifobject);
                            for (int i = 0; i < notifob.size(); i++) {
                                meetingnfoos.writeObject(notifob.get(i));
                            }
                            //meetingnfoos.writeObject(notiifobject);
                            meetingnfoos.flush();
                            meetingnfoos.close();
                            networkutil nc1=MeetingChedulerServer.table.get(meetername);
                            if(nc1!=null)
                            {
                                sendobject instant=new sendobject("NewNotifications",instant(meetername));
                                nc1.write(instant);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else if (type.equals("CancelRequest")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String meetdatetime = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    StringTokenizer st = new StringTokenizer(meetdatetime);
                    String meetername = st.nextToken("$");
                    String date = st.nextToken("$");
                    String time = st.nextToken("$");
                    String meetingname = st.nextToken("$");
                    String location = st.nextToken("$");
                    String description = st.nextToken("$");
                    ArrayList<meeting> mf = new ArrayList<meeting>();
                    ArrayList<meeting> mt = new ArrayList<meeting>();
                    ArrayList<Notifications> notifob = new ArrayList<Notifications>();
                    mf = deleterqst("TextContent/"+username + "rt.txt");
                    mt = deleterqst("TextContent/"+meetername + "rf.txt");
                    meeting object2 = new meeting(meetername, date, time, meetingname, location, description);
                    meeting object = new meeting(username, date, time, meetingname, location, description);
                    int flag = 0;
                    for (int i = 0; i < mf.size(); i++) {
                        if (object2.equals(mf.get(i))) {
                            mf.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < mt.size(); i++) {
                        if (object.equals(mt.get(i))) {
                            mt.remove(i);
                            flag = 1;
                            break;
                        }
                    }
                    try {
                        FileOutputStream meetingrtfos = new FileOutputStream("TextContent/"+username + "rt.txt");
                        ObjectOutputStream meetingrtoos = new ObjectOutputStream(meetingrtfos);
                        for (int i = 0; i < mf.size(); i++) {
                            meetingrtoos.writeObject(mf.get(i));
                        }
                        meetingrtoos.flush();
                        meetingrtoos.close();
                        FileOutputStream meetingrffos = new FileOutputStream("TextContent/"+meetername + "rf.txt");
                        ObjectOutputStream meetingrfoos = new ObjectOutputStream(meetingrffos);
                        for (int i = 0; i < mt.size(); i++) {
                            meetingrfoos.writeObject(mt.get(i));
                        }
                        meetingrfoos.flush();
                        meetingrfoos.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (flag == 0) {
                        nc.write(new sendobject("Request Already Responded"));
                    }
                } else if (type.equals("ChangeRemind")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    String meetdatetime = msg[2].substring(msg[2].indexOf(':') + 1, msg[2].length());
                    StringTokenizer st = new StringTokenizer(meetdatetime);
                    String meetername = st.nextToken("$");
                    String date = st.nextToken("$");
                    String time = st.nextToken("$");
                    String meetingname = st.nextToken("$");
                    String location = st.nextToken("$");
                    String description = st.nextToken("$");
                    String reminder = st.nextToken("$");
                    meeting object = new meeting(meetername, date, time, meetingname, location, description);
                    Finalmeeting ob = new Finalmeeting(object, reminder);
                    ArrayList<Finalmeeting> mf = new ArrayList<Finalmeeting>();
                    mf = finalmeetinglist("TextContent/"+username + "mf.txt");
                    for (int i = 0; i < mf.size(); i++) {
                        if (mf.get(i).equals(ob)) {
                            mf.get(i).reminder = (String) ob.reminder;
                        }
                    }
                    try {
                        FileOutputStream meetingrtfos = new FileOutputStream("TextContent/"+username + "mf.txt");
                        ObjectOutputStream meetingrtoos = new ObjectOutputStream(meetingrtfos);
                        for (int i = 0; i < mf.size(); i++) {
                            meetingrtoos.writeObject(mf.get(i));
                        }
                        meetingrtoos.flush();
                        meetingrtoos.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else if (type.equals("ClearHistory")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    FileWriter w = new FileWriter("TextContent/"+username + "mh.txt");
                    PrintWriter p = new PrintWriter(w);
                    p.flush();
                    p.close();
                    w.close();
                } else if (type.equals("ClearNotifications")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    FileWriter w = new FileWriter("TextContent/"+username + "mn.txt");
                    PrintWriter p = new PrintWriter(w);
                    p.flush();
                    p.close();
                    w.close();
                } else if (type.equals("CloseConnection")) {
                    String username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
                    MeetingChedulerServer.table.remove(username,nc);
                    break;
                }
                sent.clear();
                Ob.clear();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        String username=null;
        try{
            username = msg[1].substring(msg[1].indexOf(':') + 1, msg[1].length());
        }
        catch(Exception e)
        {
            System.out.println("No Need");
        }
        try{
            MeetingChedulerServer.table.remove(username,nc);
        }
        catch(Exception e)
        {
            System.out.println("No need");
        }
        System.out.println("Connection Terminated");
        nc.closeConnection();
    }

    ArrayList<Notifications> getnotif(String a) {
        ArrayList<Notifications> ob = new ArrayList<Notifications>();
        try {
            FileInputStream ios = new FileInputStream(a);
            ObjectInputStream oos = new ObjectInputStream(ios);
            Notifications tmp;

            while (true) {
                try {
                    {
                        tmp = (Notifications) oos.readObject();
                        ob.add(tmp);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("File not found");
            return ob;
        }
        return ob;

    }

    ArrayList<Finalmeeting> finalmeetinglist(String a) {
        ArrayList<Finalmeeting> ob = new ArrayList<Finalmeeting>();
        try {
            FileInputStream ios = new FileInputStream(a);
            ObjectInputStream oos = new ObjectInputStream(ios);
            Finalmeeting tmp;

            while (true) {
                try {
                    {
                        tmp = (Finalmeeting) oos.readObject();
                        ob.add(tmp);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("File not found");
            return ob;
        }
        return ob;

    }

    public void historyverify(String a) {
        ArrayList<Finalmeeting> meetinglist = new ArrayList<Finalmeeting>();
        ArrayList<meeting> history = new ArrayList<meeting>();
        meetinglist = finalmeetinglist("TextContent/"+a + "mf.txt");
        history = deleterqst("TextContent/"+a + "mh.txt");
        int size = meetinglist.size();
        int i = 0;
        while (i < meetinglist.size()) {
            if (!checkbefore(meetinglist.get(i).ob.date, meetinglist.get(i).ob.time)) {
                history.add(meetinglist.get(i).ob);
                meetinglist.remove(i);
            } else {
                i++;
            }
        }
        //System.out.println(meetinglist.size());
        //System.out.println(history.size());
        try {
            FileOutputStream meetingrtfos = new FileOutputStream("TextContent/"+a + "mf.txt");
            ObjectOutputStream meetingrtoos = new ObjectOutputStream(meetingrtfos);
            for (int j = 0; j < meetinglist.size(); j++) {
                meetingrtoos.writeObject(meetinglist.get(j));
            }
            meetingrtoos.flush();
            meetingrtoos.close();
            FileOutputStream meetingrffos = new FileOutputStream("TextContent/"+a + "mh.txt");
            ObjectOutputStream meetingrfoos = new ObjectOutputStream(meetingrffos);
            for (int j = 0; j < history.size(); j++) {
                meetingrfoos.writeObject(history.get(j));
            }
            meetingrfoos.flush();
            meetingrfoos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
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
        //System.out.println("hello");
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
        //System.out.println(now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + "/" + now.get(Calendar.YEAR) + "/");
        requested.set(Calendar.DATE, formatdate);
        requested.set(Calendar.MONTH, formatmonth - 1);
        requested.set(Calendar.YEAR, formatyear);
        requested.set(Calendar.HOUR_OF_DAY, hourf);
        requested.set(Calendar.MINUTE, minutef);
        requested.set(Calendar.SECOND, 0);
        //System.out.println(requested.get(Calendar.DATE) + "/" + requested.get(Calendar.MONTH) + "/" + requested.get(Calendar.YEAR));
        //System.out.println(requested.get(Calendar.HOUR) + ":" + requested.get(Calendar.MINUTE));
        return now.before(requested);
    }

    ArrayList<meeting> deleterqst(String a) {
        ArrayList<meeting> ob = new ArrayList<meeting>();
        try {
            FileInputStream ios = new FileInputStream(a);
            ObjectInputStream oos = new ObjectInputStream(ios);
            meeting tmp;

            while (true) {
                try {
                    {
                        tmp = (meeting) oos.readObject();
                        ob.add(tmp);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("File not found");
            return ob;
        }
        return ob;

    }

    void printlist(ArrayList<meeting> ob) {
        for (int i = 0; i < ob.size(); i++) {
            System.out.println(ob.get(i));
        }
    }

}

public class MeetingChedulerServer {

    private ServerSocket ServSock;
    public int i = 1;
    static public Hashtable<String, networkutil> table;

    MeetingChedulerServer() {
        table = new Hashtable<>();
        try {
            ServSock = new ServerSocket(45555);
            //new WriteThreadServer(table, "Server");
            while (true) {
                Socket clientSock = ServSock.accept();
                networkutil nc = new networkutil(clientSock);
                Server sv = new Server(nc);
                //table.put("c"+ i++, nc);
                //new ReadThread(nc);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
        new MeetingChedulerServer();

    }

}
