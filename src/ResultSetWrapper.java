import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexhoffman on 4/19/17.
 */
public class ResultSetWrapper {

    public ResultSetWrapper (){

    }


    public ArrayList<Contact> getContactList(ResultSet result) throws SQLException {

        ArrayList<Contact> contactList = new ArrayList<Contact>();


        while(result.next()){

            contactList.add(createContactInstance(result));

        }

        return contactList;

    }

    private Contact createContactInstance(ResultSet result) throws SQLException {

        return new Contact(result.getInt("idCONTACT"),result.getString("EMAIL"),result.getString("FIRST_NAME"),result.getString("LAST_NAME"),result.getInt("NUM_COMP_PROJECTS"),result.getString("ARTIST_GROUP_NAME"),result.getString("IDEAL_CLIENT"),result.getString("CREATE_DATE"),result.getString("LAST_CONTACT_DATE"),result.getString("MAILING_LIST_PERM"),result.getString("REFERENCE_CREDIT"),result.getString("FACEBOOK_NAME"),result.getString("WEBSITES"),result.getString("PAY_TIMELY"),result.getString("COMMUNICATION"),result.getInt("NUM_OF_REFERENCES"),result.getString("FIX_IT_FACTOR"),result.getString("FOLLOWUP_DATE"),result.getInt("FOLLOWUP_SEQ"));
    }

    private Contact createContactInstanceByNameAndEmail(ResultSet result) throws SQLException {

        return new Contact(result.getInt("idCONTACT"),result.getString("EMAIL"),result.getString("FIRST_NAME"),result.getString("LAST_NAME"));
    }

    public ArrayList<Note> getNoteList(ResultSet resultSet) throws SQLException {

        ArrayList<Note> noteList = new ArrayList<Note>();

        while(resultSet.next()){

            noteList.add(CreateNoteInstance(resultSet));
        }
        return noteList;
    }

    private Note CreateNoteInstance(ResultSet resultSet) throws SQLException {
        return new Note(resultSet.getInt("idNOTE"),resultSet.getInt("PROJECT_ID_NOTE"),resultSet.getString("CREATE_DATE"),resultSet.getString("COMMUNICATION_MEDIUM"),resultSet.getInt("CONTACT_ID_NOTE"),resultSet.getString("NOTE_TEXT"));
    }

    public ArrayList<Contact> getContactListByNameAndEmail(ResultSet resultSet) throws SQLException {
        ArrayList<Contact> contactList = new ArrayList<Contact>();

        while(resultSet.next()){

            contactList.add(createContactInstanceByNameAndEmail(resultSet));
        }

        return contactList;

    }

    public Contact getContactByEmail(ResultSet resultSet) throws SQLException {
        ArrayList<Contact> contactList = new ArrayList<Contact>();

       while(resultSet.next()){

           contactList.add(new Contact(resultSet.getInt("idCONTACT"),resultSet.getString("EMAIL")));
       }

        return contactList.get(0);

    }

    public ArrayList<Project> returnArrayofProjects(ResultSet resultSet) throws SQLException {
        ArrayList<Project> projectList = new ArrayList<>();

        while(resultSet.next()){

            projectList.add(new Project(resultSet.getString("idPROJECT"),resultSet.getString("START_DATE"), resultSet.getString("END_DATE"), resultSet.getString("CONTACT_ID_PROJ"), resultSet.getString("EMAIL"),resultSet.getString("PROJECT_TITLE"), resultSet.getString("SONG_COUNT"), resultSet.getString("SERVICE_TYPE"),resultSet.getString("TOTAL_COST"),resultSet.getString("PROJECT_STATUS"),resultSet.getString("DUE_DATE"), resultSet.getString("ADJUSTMENT_AMT")));

        }

        return projectList;
    }
}
