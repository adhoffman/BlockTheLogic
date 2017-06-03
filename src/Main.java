/**
 * Created by alexhoffman on 1/23/17.
 */

import java.sql.*;
public class Main {
    public static void main(String [] args) throws SQLException {


     DataController controller = new DataController();

    MainPage mainPage =  new MainPage(controller);

    mainPage.setVisible(true);


    }

}



