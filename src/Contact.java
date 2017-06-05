import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexhoffman on 4/19/17.
 */
public class Contact implements Comparable<Contact>{
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

        this.firstName = firstName;
        this.lastName = lastName;
        this.artistGroupName = artistGroupName;
        this.email = email;
        this.facebookName = facebookName;
        this.website = website;
        this.referenceCredit =referenceCredit;
    }

    public Contact(int idCONTACT, String email, String first_name, String last_name, int num_comp_projects, String artist_group_name, String ideal_client, String create_date, String last_contact_date, String mailing_list_perm, String reference_credit, String facebook_name, String websites, String pay_timely, String communication, int num_of_references, String fix_it_factor, String followup_date, int followup_seq) {
        this.id=idCONTACT;
        this.email=email;
        this.firstName=first_name;
        this.lastName=last_name;
        this.numberOfCompletedProjects=num_comp_projects;
        this.artistGroupName=artist_group_name;
        this.idealClientFlag=ideal_client;
        this.createDate=create_date;
        this.lastContactDate=last_contact_date;
        this.mailingListPerm=mailing_list_perm;
        this.referenceCredit=reference_credit;
        this.facebookName=facebook_name;
        this.website=websites;
        this.paysTimely=pay_timely;
        this.communicationEfficiency=communication;
        this.numberOfReferences=num_of_references;
        this.fixItFactor=fix_it_factor;
        this.followupDate=followup_date;
        this.followupSequence=followup_seq;
    }

    public Contact(int idCONTACT, String email, String first_name, String last_name) {
        this.id = idCONTACT;
        this.email = email;
        this.firstName = first_name;
        this.lastName = last_name;
    }

    public Contact(int idCONTACT, String email) {
        this.id = idCONTACT;
        this.email = email;

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


    public String getLastContactDate() {
        return this.lastContactDate;
    }

    public String getFollowupDate() {
        return this.followupDate;
    }

    public int getID() {
        return this.id;
    }

    public int getFollowupSequence() {
        return this.followupSequence;
    }

    @Override
    public int compareTo(Contact other) {
        return this.email.compareTo(other.email);
    }

    public int getNumberOfCompletedProjects() {
        return this.numberOfCompletedProjects;
    }

    public String getCreateDate() {
        return this.createDate;
    }
}
