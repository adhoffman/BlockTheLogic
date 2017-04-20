import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by alexhoffman on 4/12/17.
 */
public class MainPage extends JFrame{
    private ArrayList<Contact> contactList;

    private JButton runQueryButton;
    private JTextField firstNameField;
    private DataController controller;
    private JPanel rootPane;
    private JTabbedPane tabbedPane1;
    private JTabbedPane WorkflowTab;
    private JPanel AddContactTab;
    private JPanel FollowupTab;
    private JPanel ActiveTab;
    private JPanel PendingDepositTab;
    private JPanel Activetab;
    private JPanel RevisionTab;
    private JPanel PendingPayTab;
    private JPanel CompleteTab;
    private JPanel ClientsTab;
    private JPanel ReportsTab;
    private JButton addProspectButton;
    private JTextField facebookNameField;
    private JTextField lastNameField;
    private JTextField artistgroupNameField;
    private JTextField emailField;
    private JTextField websitesField;
    private JTextField referencesField;
    private JButton nextButton;
    private JButton grabFollowupContactsButton;
    private JTextField FU_first;
    private JTextField FU_last;
    private JTextField FU_email;
    private JTextField FU_facebook;
    private JTextField FU_lastcontact;
    private JTextField FU_followupdate;
    private JLabel FU_firstNameLabel;
    private JLabel FU_followupdate_Label;
    private JLabel FU_lastContactLabel;
    private JLabel FU_facebook_label;
    private JLabel FU_emailLabel;
    private JLabel FU_lastLabel;
    private JTextArea FU_messageTextArea;
    private JComboBox FU_communicationComboBox;
    private JLabel FU_communicationLabel;
    private JLabel FU_messageLabel;
    private JLabel FU_alertLabel;
    private JScrollPane FU_messageScrollPane;
    private int counter = 0;


    public MainPage(DataController controller) throws SQLException {
        super("BlockTheLogic");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension (1200, 750));

        setContentPane(rootPane);

        setVisible(true);

        setFollowupTextDisable();
        setFU_messageAreasettings();
        populateFU_CommunicationCombobox();

        addProspectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    controller.addContact(new Contact(firstNameField.getText(),lastNameField.getText(),artistgroupNameField.getText(),emailField.getText(),facebookNameField.getText(),websitesField.getText(), referencesField.getText()));

                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Send out queries updating current account, and update counter
                try {
                    setFollowupDateforCurrentContact(contactList.get(counter));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                counter +=1;


                //If end of list, clear fields and disable button
                if(contactList.size()==counter){

                    endOfFollowupContacts();

                }else{
                    if((followupTextAreaLargerThanZero())&&(emailFieldNotBlank())){

                        FU_alertLabel.setText("Messagebox Populdated");
                        if(contactList.size()==counter){
                            endOfFollowupContacts();


                        }else//if Not end fo list, get next account and populate textfields
                        {
                            Contact contact = contactList.get(counter);
                            setFollupTextFields(contact);
                        }


                    }else{
                        FU_alertLabel.setText("Messagebox Not Populated");

                    }
                }



            }
        });
        grabFollowupContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    contactList = controller.getFollowup();
                    clearFollowupFields();

                    nextButton.setBorderPainted(true);
                    nextButton.setFocusPainted(true);
                    nextButton.setText("Next");


                    if(contactList.size()==counter){
                        endOfFollowupContacts();
                    }else
                    {  Contact contact = contactList.get(counter);

                        setFollupTextFields(contact);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                counter = 0;
            }
        });
    }

    private void endOfFollowupContacts() {
        clearFollowupFields();
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setText("No More Contacts");
        counter = 0;
    }

    private void setFollowupDateforCurrentContact(Contact contact) throws SQLException {
        controller.setFolowupDateForContact(contact);
    }

    private void setFollupTextFields(Contact contact) {
        FU_first.setText(contact.getFirstName());
        FU_last.setText(contact.getLastName());
        FU_email.setText(contact.getEmail());
        FU_facebook.setText(contact.getFacebookName());
        FU_lastcontact.setText(contact.getLastContactDate());
        FU_followupdate.setText(contact.getFollowupDate());
        FU_messageTextArea.setText("");
    }

    private void populateFU_CommunicationCombobox() {
        FU_communicationComboBox.addItem("Email");
        FU_communicationComboBox.addItem("Facebook Message");
        FU_communicationComboBox.addItem("Phone Call");
        FU_communicationComboBox.addItem("Text");
        FU_communicationComboBox.addItem("In Person");
        FU_communicationComboBox.addItem("Other");

    }

    private void setFU_messageAreasettings() {
        FU_messageTextArea.setLineWrap(true);

    }


    private boolean emailFieldNotBlank() {
        if(FU_email.getText().length()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean followupTextAreaLargerThanZero() {
        if(FU_messageTextArea.getText().length()>0){
            return true;
        }else {
            return false;
        }
    }

    private void setFollowupTextDisable() {
        FU_first.setEnabled(false);
        FU_last.setEnabled(false);
        FU_email.setEnabled(false);
        FU_facebook.setEnabled(false);
        FU_lastcontact.setEnabled(false);
        FU_followupdate.setEnabled(false);
    }

    private void clearFollowupFields() {
        FU_first.setText("");
        FU_last.setText("");
        FU_email.setText("");
        FU_facebook.setText("");
        FU_lastcontact.setText("");
        FU_followupdate.setText("");
        FU_messageTextArea.setText("");
    }


    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        artistgroupNameField.setText("");
        facebookNameField.setText("");
        websitesField.setText("");
        referencesField.setText("");
        emailField.setText("");
    }


}
