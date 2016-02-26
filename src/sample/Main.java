package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private Scene scene;
    private Stage stage;
    private String access_token;
    private Browser mBrowser;
    Auth auth;

    @Override
    public void start(Stage stage) {
        // create the scene
        stage.setTitle("Авторизация");
        mBrowser = new Browser();
        this.stage = stage;
        //stage.setScene(scene);
        //stage.show();
        startAuth();
    }

    void startAuth() {
        //auth = new Auth(mBrowser);
        if (readTokenFromFile()) {
            System.out.print("Прочитан OAuth-токен из файла");
            startApplication();
        } else {
            scene = new Scene(mBrowser, 750, 500, Color.web("#666970"));
            stage.setScene(scene);
            stage.show();
            webAuth();
        }

    }

    @FXML
    void startApplication() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root, 300, 275);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("VKmatic");
        stage.setScene(scene);
        stage.show();
        BotLogic botLogic = new BotLogic();
    }


    void webAuth(){
        String scope = "messages";
        String url = "http://api.vkontakte.ru/oauth/authorize?" +
                "client_id=5320052" +
                "&scope="+scope+"" +
                "&display=mobile" +
                "&redirect_uri=https%3A//oauth.vk.com/blank.html" +
                "&response_type=token" +
                "&revoke=1" +
                "&v=5.29";
        mBrowser.webEngine.load(url);
        System.out.println("Запрос авторизации "+url);
        mBrowser.webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            ReadOnlyStringProperty location = mBrowser.webEngine.locationProperty();
            String[] parsingTemp = location.toString().split("#");
            for (String s : parsingTemp) {
                if (s.startsWith("access_token=")) {
                    access_token = s.split("&")[0].split("=")[1];
                    Auth.access_token = access_token;
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
                Auth.access_token = sCurrentLine;
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

    public static void main(String[] args) {
        launch(args);
    }
}
