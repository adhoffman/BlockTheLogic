/**
 * Created by alexhoffman on 4/21/17.
 */
public class Note {

    private int id;
    private int projectID;
    private String createDate;
    private String communicationMedium;
    private int contactID;
    private String noteText;

    public Note(){

    }

    public Note(int idNOTE, int projectID, String createDate, String communicationMedium, int contactID, String noteText) {
        this.id =idNOTE;
        this.projectID = projectID;
        this.createDate = createDate;
        this.communicationMedium = communicationMedium;
        this.contactID = contactID;
        this.noteText = noteText;
    }

    public String getNoteText() {
        return this.noteText;
    }

    public int getContactID() {
        return this.contactID;
    }

    public String getDate() {
        return this.createDate;
    }

    public String getContactMedium() {
        return this.communicationMedium;
    }
}
