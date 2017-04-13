import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by alexhoffman on 1/28/17.
 */
public class MySQLConnector {
    private Connection connection;
    private Statement statement;

    public void MySQLConnector() throws SQLException {
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TEST_LOGICBLOCKSTUDIOS", "student", "student");
         statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void queryProject() throws SQLException {

        // 3. Execute SQL Query
        ResultSet result = statement.executeQuery("SELECT * FROM PROJECT");
        // 4. Process the result set

        while (result.next()) {
            System.out.println(result.getString("PROJECT_TITLE") + " , " + result.getString("SONG_COUNT"));
        }

    }


    public void addProspect(String firstName, String lastName, String artistgroupName, String email, String facebookName, String website, String references) throws SQLException {

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        String query = "INSERT INTO CONTACT (FIRST_NAME,LAST_NAME,ARTIST_GROUP_NAME,EMAIL,FACEBOOK_NAME,WEBSITES,REFERENCE_CREDIT,CREATE_DATE,LAST_CONTACT_DATE,MAILING_LIST_PERM) VALUES (\""+firstName+"\",\""+lastName+"\",\""+artistgroupName+"\",\""+email+"\",\""+facebookName+"\",\""+website+"\",100,\""+today+"\",\""+today+"\",\"N\");";

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TEST_LOGICBLOCKSTUDIOS", "student", "student");
        statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.execute(query);

    }
}

