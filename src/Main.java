/**
 * Created by alexhoffman on 1/23/17.
 */

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    public static void main(String [] args) throws SQLException {

    System.out.println("Hello, World");


    MySQLConnector connector = new MySQLConnector();

    connector.queryProject();


}

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}



