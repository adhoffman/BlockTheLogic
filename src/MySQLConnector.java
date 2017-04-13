import java.sql.*;

/**
 * Created by alexhoffman on 1/28/17.
 */
public class MySQLConnector {

    public void MySQLConnector (){

    }


    public void queryProject() throws SQLException {

        // 1. Get a connection to database
        Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TEST_LOGICBLOCKSTUDIOS", "student", "student");


        // 2. Create a Statement
        Statement myStatement = null;
        try {
            myStatement = myConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3. Execute SQL Query
        ResultSet result = myStatement.executeQuery("SELECT * FROM PROJECT");
        // 4. Process the result set

        while (result.next()) {
            System.out.println(result.getString("PROJECT_TITLE") + " , " + result.getString("SONG_COUNT"));
        }

    }

}