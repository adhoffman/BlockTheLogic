/**
 * Created by alexhoffman on 1/23/17.
 */

import javafx.application.Application;
import javafx.stage.Stage;

import javax.naming.ldap.Control;
import java.sql.*;

public class Main {
    public static void main(String [] args) throws SQLException {


     DataController controller = new DataController();

    MainPage mainPage =  new MainPage(controller);

    mainPage.setVisible(true);


    }

}



