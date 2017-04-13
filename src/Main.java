/**
 * Created by alexhoffman on 1/23/17.
 */

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;

public class Main {
    public static void main(String [] args) throws SQLException {

        System.out.println("HI ALEX. YOUR PROGRAM IS RUNNING.. OR AT LEAST STARTED RUNNING. MAYBE. WHO KNOWS. THE CODE DOESN'T. LOVE YOU. ");


        MySQLConnector connector = new MySQLConnector();

        MainPage page = new MainPage(connector);
        page.setVisible(true);

    }

}



