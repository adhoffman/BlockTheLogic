import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


    private String buildInsertQuery(String firstName, String lastName, String artistgroupName, String email, String facebookName, String website, String todayFormatted, String todayFormatted1, String followupDate) {
        return "INSERT INTO CONTACT (FIRST_NAME,LAST_NAME,ARTIST_GROUP_NAME,EMAIL,FACEBOOK_NAME,WEBSITES,REFERENCE_CREDIT,CREATE_DATE,LAST_CONTACT_DATE,MAILING_LIST_PERM,FOLLOWUP_DATE,FOLLOWUP_SEQ) VALUES (\""+firstName+"\",\""+lastName+"\",\""+artistgroupName+"\",\""+email+"\",\""+facebookName+"\",\""+website+"\",100,\""+todayFormatted+"\",\""+todayFormatted+"\",\"N\",\"" + followupDate + "\",1);";
    }


    private String setInitialFollowupDate() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, 7);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public ArrayList<Contact> getFollowup() throws SQLException {

        return connector.getFollowup();

    }

    public void addContact(Contact contact) throws SQLException {

        String todayFormatted = getTodayFormated();
        String followupDate = setInitialFollowupDate();


        String query = buildInsertQuery(contact.getFirstName(),contact.getLastName(),contact.getArtistGroupName(),contact.getEmail(),contact.getFacebookName(),contact.getWebsite(), todayFormatted,todayFormatted,followupDate);

        connector.addContact(query);

    }

    private String getTodayFormated() {
        Date today = new java.util.Date();
        String todayFormatted = new SimpleDateFormat("yyyy-MM-dd").format(today);
        return todayFormatted;
    }


    public void setFolowupDateForContact(Contact contact) throws SQLException {

        String newFollowupDate = calculateFollowupDate(contact.getFollowupDate(),contact.getFollowupSequence());
        int newSeq = calSeq(contact.getFollowupSequence());

        String query = "UPDATE CONTACT SET FOLLOWUP_DATE = \""+newFollowupDate+"\", FOLLOWUP_SEQ = \""+newSeq+ "\" WHERE idCONTACT = \""+Integer.toString(contact.getID())+"\"";
        connector.updateFollowupDate(query);
    }

    private int calSeq(int followupSequence) {
        if(followupSequence==10 || followupSequence>10){
            return 10;
        }else{
            return followupSequence+1;
        }

    }

    private String calculateFollowupDate(String followupDate, int followupSequence) {


        String newFollowupDate = "";

        switch(followupSequence){
            case 1: newFollowupDate = calculateDay(7,followupDate);
                break;
            case 2: newFollowupDate = calculateDay(10,followupDate);
                break;
            case 3: newFollowupDate = calculateDay(14,followupDate);
                break;
            case 4: newFollowupDate = calculateDay(14,followupDate);
                break;
            case 5: newFollowupDate = calculateDay(14,followupDate);
                break;
            case 6: newFollowupDate = calculateDay(21,followupDate);
                break;
            case 7: newFollowupDate = calculateDay(30,followupDate);
                break;
            case 8: newFollowupDate = calculateDay(30,followupDate);
                break;
            case 9: newFollowupDate = calculateDay(60,followupDate);
                break;
            case 10: newFollowupDate = calculateDay(90,followupDate);
                break;
            default: newFollowupDate = calculateDay(7,followupDate);
                break;

        }

        return newFollowupDate;

    }

    private String calculateDay(int days,String followupDate) {
        String newDate = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = df.parse(followupDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days);
            newDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }


    public void createNoteForContact(Contact contact, String noteText, String communicationMedium) throws SQLException {
        String today = getTodayFormated();

        String query = "INSERT INTO NOTE (CREATE_DATE,COMMUNICATION_MEDIUM,CONTACT_ID_NOTE,NOTE_TEXT) VALUES (\""+today+"\",\""+communicationMedium+"\",\""+contact.getID()+"\",\""+noteText+"\")";

        connector.createNoteForContact(query);
    }
}
