import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * Created by alexhoffman on 1/28/17.
 */
public class MySQLConnector {
    private Connection connection;
    private Statement statement;
    private String ipAddress="localhost";
    private String port= "3306";
    private String database = "TEST_LOGICBLOCKSTUDIOS";
    private String user= "student";
    private String password= "student";

    /*
    public void queryProject() throws SQLException {

        // 3. Execute SQL Query
        ResultSet result = statement.executeQuery("SELECT * FROM PROJECT");
        // 4. Process the result set

        while (result.next()) {
            System.out.println(result.getString("PROJECT_TITLE") + " , " + result.getString("SONG_COUNT"));
        }

    }
*/

    public void addContact(String firstName, String lastName, String artistgroupName, String email, String facebookName, String website, String references) throws SQLException, ParseException {

        Date today = new java.util.Date();
        String todayFormatted = new SimpleDateFormat("yyyy-MM-dd").format(today);

        String followupDate = setInitialFollowupDate(today);


        String query = buildInsertQuery(firstName,lastName,artistgroupName,email,facebookName,website,todayFormatted,todayFormatted,followupDate);

        System.out.println(query);

        connection = DriverManager.getConnection("jdbc:mysql://"+this.ipAddress+":"+this.port+"/"+this.database, this.user, this.password);
        statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

       statement.executeUpdate(query);
    }

    private String buildInsertQuery(String firstName, String lastName, String artistgroupName, String email, String facebookName, String website, String todayFormatted, String todayFormatted1, String followupDate) {
        return "INSERT INTO CONTACT (FIRST_NAME,LAST_NAME,ARTIST_GROUP_NAME,EMAIL,FACEBOOK_NAME,WEBSITES,REFERENCE_CREDIT,CREATE_DATE,LAST_CONTACT_DATE,MAILING_LIST_PERM,FOLLOWUP_DATE,FOLLOWUP_SEQ) VALUES (\""+firstName+"\",\""+lastName+"\",\""+artistgroupName+"\",\""+email+"\",\""+facebookName+"\",\""+website+"\",100,\""+todayFormatted+"\",\""+todayFormatted+"\",\"N\",\"" + followupDate + "\",1);";
    }

    private String setInitialFollowupDate(Date date) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 7);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
}

