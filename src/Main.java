/**
 * Created by alexhoffman on 1/23/17.
 */

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;

public class Main {
    public static void main(String [] args) throws SQLException {


     MySQLConnector connector = new MySQLConnector();

    MainPage mainPage =  new MainPage(connector);

    mainPage.setVisible(true);


    }

}



