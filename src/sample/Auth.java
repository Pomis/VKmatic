package sample;

import javafx.beans.property.ReadOnlyStringProperty;

import java.io.*;

/**
 * Created by romanismagilov on 26.02.16.
 */
public class Auth {

    Browser mBrowser;
    static String access_token;

    public Auth(Browser mBrowser) {
        this.mBrowser = mBrowser;
    }

    void webAuth(){
        String scope = "messages";
        String url = "https://oauth.vk.com/authorize?client_id=5320052&scope="+scope+"&display=mobile&redirect_uri=https%3A//oauth.vk.com/blank.html&response_type=token&revoke=1&v=5.29";
        mBrowser.webEngine.load(url);
        System.out.println("Запрос авторизации "+url);
        mBrowser.webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            ReadOnlyStringProperty location = mBrowser.webEngine.locationProperty();
            String[] parsingTemp = location.toString().split("#");
            for (String s : parsingTemp) {
                if (s.startsWith("access_token=")) {
                    access_token = s.split("&")[0].split("=")[1];

                    // Записываем токен в файлец
                    try (PrintWriter out = new PrintWriter("lib.dat")) {
                        out.println(access_token);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    boolean readTokenFromFile(){
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("lib.dat"));

            while ((sCurrentLine = br.readLine()) != null && sCurrentLine.length()>1) {
                access_token = sCurrentLine;
                return true;
            }
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
