package sample;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * Created by romanismagilov on 26.02.16.
 */
public class BotLogic {
    HashMap<String, ArrayList<Message>> daysOfWeek;


    public BotLogic(){
        createBasic();
    }

    void createBasic(){
        daysOfWeek = new HashMap<>();
        daysOfWeek.put("понедельник", new ArrayList<>());
        daysOfWeek.put("вторник", new ArrayList<>());
        daysOfWeek.put("среда", new ArrayList<>());
        daysOfWeek.put("четверг", new ArrayList<>());
        daysOfWeek.put("пятница", new ArrayList<>());
        daysOfWeek.put("суббота", new ArrayList<>());
        daysOfWeek.put("воскресенье", new ArrayList<>());

        sendMessage("176530428", "test123");
    }

    void initTimer(){
        java.util.Timer timer2 = new java.util.Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                //Do work!
            }
        };
        timer2.schedule( task, 100 );
    }

    void sendMessage(String user_id, String message){
        try {
            URLConnection connection = new URL("https://api.vk.com/method/messages.send?" +
                    "user_id="+user_id+
                    "&message="+message+
                    "&v=5.37"+
                    "&access_token="+Auth.access_token).openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            char[] buffer = new char[256];
            int rc;

            StringBuilder sb = new StringBuilder();

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);

            reader.close();

            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
