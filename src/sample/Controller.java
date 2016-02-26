package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller {

    @FXML
    WebView webView;

    WebEngine webEngine ;

    public Controller () {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
    }
}
