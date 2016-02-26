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
        stage.setScene(scene);
        stage.show();
        startAuth();
    }

    void startAuth() {
        auth = new Auth(mBrowser);
        if (auth.readTokenFromFile()) {
            System.out.print("Прочитан OAuth-токен из файла");
            startApplication();
        } else {
            scene = new Scene(mBrowser, 750, 500, Color.web("#666970"));
            auth.webAuth();
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


    public static void main(String[] args) {
        launch(args);
    }
}
