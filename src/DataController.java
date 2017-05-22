import com.sun.tools.javap.TypeAnnotationWriter;

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
    private String projectQuery;

    public DataController(){
        connector = new MySQLConnector();
        this.projectQuery = "SELECT PROJECT_STATUS, PROJECT_TITLE, SONG_COUNT, SERVICE_TYPE, TOTAL_COST, DUE_DATE, EMAIL FROM PROJECT JOIN CONTACT ON CONTACT_ID_PROJ = idCONTACT";

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

    private String calculateDay(int days,String startingDate) {
        String newDate = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = df.parse(startingDate);
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
        String nextYearToday = yearFromToday();

        String query = "INSERT INTO NOTE (CREATE_DATE,COMMUNICATION_MEDIUM,CONTACT_ID_NOTE,NOTE_TEXT) VALUES (\""+today+"\",\""+communicationMedium+"\",\""+contact.getID()+"\",\""+noteText+"\")";

        connector.createNoteForContact(query);
    }

    private String yearFromToday() {
        String today = getTodayFormated();
        return calculateDay(365,today);
    }

    public ArrayList<Note> getNotes() throws SQLException {

        String query = "SELECT * FROM NOTE";

        return connector.getNotes(query);

    }


    public ArrayList<Contact> getContactListbyNameAndEmail() throws SQLException {

        String query = "SELECT idCONTACT,EMAIL,FIRST_NAME,LAST_NAME FROM CONTACT";

        return connector.getContactByNameAndEmail(query);
    }

    public void addProject(Project project) throws SQLException {

        Contact projectContact= getContactIDbyEmail(project.getEmail());

        String query = "INSERT INTO PROJECT (PROJECT_TITLE, CONTACT_ID_PROJ, START_DATE, END_DATE, DUE_DATE, SONG_COUNT,SERVICE_TYPE,TOTAL_COST, PROJECT_STATUS) VALUES (\""+project.getTitle()+"\",\""+projectContact.getID()+"\",\""+project.getStartDate()+"\",\""+project.getEndDate()+"\",\""+project.getDueDate()+"\",\""+project.getSongCount()+"\",\""+project.getServiceType()+"\",\""+project.getTotalCost()+"\",\""+ProjectStatus.NEW+"\")";

        connector.addProject(query);
    }

    private Contact getContactIDbyEmail(String email) throws SQLException {

        String query = "SELECT idCONTACT,EMAIL FROM CONTACT WHERE EMAIL = \""+email+"\"";

        Contact projectContact = connector.getContactIDByEmail(query);

     return projectContact;

    }

    public ArrayList<Project> getActiveProjects() throws SQLException {

        String query = this.projectQuery+" WHERE PROJECT_STATUS = \"NEW\"";

        return connector.getActiveProjects(query);

    }

    public ArrayList<Project> getPendingDepsitProjects() throws SQLException {

        String query = projectQuery+" WHERE PROJECT_STATUS = \"PENDING_DEPOSIT\"";

        return connector.getPendingDepositProjects(query);

    }
}
