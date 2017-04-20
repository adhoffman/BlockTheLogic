import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexhoffman on 4/19/17.
 */
public class Contact {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private int numberOfCompletedProjects;
    private String artistGroupName;
    private String idealClientFlag;
    private String createDate;
    private String lastContactDate;
    private String mailingListPerm;
    private String referenceCredit;
    private String facebookName;
    private String website;
    private String paysTimely;
    private String communicationEfficiency;
    private int numberOfReferences;
    private String fixItFactor;
    private String followupDate;
    private int followupSequence;






    public Contact(String firstName, String lastName, String artistGroupName, String email, String facebookName, String website, String referenceCredit) {
        //firsNameField.getText(),lastNameField.getText(),artistgroupNameField.getText(),emailField.getText(),facebookNameField.getText(),websitesField.getText(), referencesField.getText()));

        this.firstName = firstName;
        this.lastName = lastName;
        this.artistGroupName = artistGroupName;
        this.email = email;
        this.facebookName = facebookName;
        this.website = website;
        this.referenceCredit =referenceCredit;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getArtistGroupName() {
        return this.artistGroupName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFacebookName() {
        return this.facebookName;
    }

    public String getWebsite() {
        return this.website;
    }


}
