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


}
