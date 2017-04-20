import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alexhoffman on 4/19/17.
 */
public class DataController {

    private MySQLConnector connector;

    public DataController(){
        connector = new MySQLConnector();

    }


    public void addContact(String firstName, String lastName, String artistgroupName, String email, String facebookName, String website, String references) throws SQLException, ParseException {

        Date today = new java.util.Date();
        String todayFormatted = new SimpleDateFormat("yyyy-MM-dd").format(today);

        String followupDate = setInitialFollowupDate(today);

        String query = buildInsertQuery(firstName,lastName,artistgroupName,email,facebookName,website,todayFormatted,todayFormatted,followupDate);

        connector.addContact(query);

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

    public ResultSet getFollowup() throws SQLException {

        return connector.getFollowup();

    }
}
