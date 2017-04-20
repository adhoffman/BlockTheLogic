import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by alexhoffman on 4/12/17.
 */
public class MainPage extends JFrame{
    private ResultSet result;

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

                    //controller.addContact(firstNameField.getText(),lastNameField.getText(),artistgroupNameField.getText(),emailField.getText(),facebookNameField.getText(),websitesField.getText(), referencesField.getText());
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


                if((followupTextAreaLargerThanZero())&&(emailFieldNotBlank())){

                    FU_alertLabel.setText("Messagebox Populdated");


                try {
                    if (result.next()){

                        FU_first.setText(result.getString("FIRST_NAME"));
                        FU_last.setText(result.getString("LAST_NAME"));
                        FU_email.setText(result.getString("EMAIL"));
                        FU_facebook.setText(result.getString("FACEBOOK_NAME"));
                        FU_lastcontact.setText(result.getString("LAST_CONTACT_DATE"));
                        FU_followupdate.setText(result.getString("FOLLOWUP_DATE"));
                        FU_messageTextArea.setText("");


                    }else
                    {
                        clearFollowupFields();
                        //remainingLabel.setText("No more Accounts");
                        nextButton.setBorderPainted(false);
                        nextButton.setFocusPainted(false);
                        nextButton.setText("No More Contacts");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                }else{
                    FU_alertLabel.setText("Messagebox Not Populated");

                }

            }
        });
        grabFollowupContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    result = controller.getFollowup();
                    clearFollowupFields();

                    nextButton.setBorderPainted(true);
                    nextButton.setFocusPainted(true);
                    nextButton.setText("Next");


                    try {
                        if (result.next()){

                            FU_first.setText(result.getString("FIRST_NAME"));
                            FU_last.setText(result.getString("LAST_NAME"));
                            FU_email.setText(result.getString("EMAIL"));
                            FU_facebook.setText(result.getString("FACEBOOK_NAME"));
                            FU_lastcontact.setText(result.getString("LAST_CONTACT_DATE"));
                            FU_followupdate.setText(result.getString("FOLLOWUP_DATE"));
                            FU_messageTextArea.setText("");


                        }else
                        {
                            clearFollowupFields();
                            //remainingLabel.setText("No more Accounts");
                            nextButton.setBorderPainted(false);
                            nextButton.setFocusPainted(false);
                            nextButton.setText("No More Contacts");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
    }


    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        artistgroupNameField.setText("");
        facebookNameField.setText("");
        websitesField.setText("");
        referencesField.setText("");
    }


    private void createUIComponents() {

    }
}
