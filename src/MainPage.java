import javax.swing.*;
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
    private MySQLConnector connector;
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
    private JButton refreshButton;
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
    private JTextArea messageTextArea;
    private JComboBox FU_communicationComboBox;
    private JLabel FU_communicationLabel;
    private JLabel FU_messageLabel;


    public MainPage(MySQLConnector connector) throws SQLException {
        super("BlockTheLogic");
        this.connector = connector;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension (1200, 750));

        setContentPane(rootPane);

        setVisible(true);

        setFollowupTextDisable();

        addProspectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    connector.addContact(firstNameField.getText(),lastNameField.getText(),artistgroupNameField.getText(),emailField.getText(),facebookNameField.getText(),websitesField.getText(), referencesField.getText());

                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (result.next()){

                        FU_first.setText(result.getString("FIRST_NAME"));
                        FU_last.setText(result.getString("LAST_NAME"));
                        FU_email.setText(result.getString("EMAIL"));
                        FU_facebook.setText(result.getString("FACEBOOK_NAME"));
                        FU_lastcontact.setText(result.getString("LAST_CONTACT_DATE"));
                        FU_followupdate.setText(result.getString("FOLLOWUP_DATE"));
                        //remainingLabel.setText(Integer.toString(result.getFetchSize()+1)+" Remaining");

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

            }
        });


        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    result = connector.getFollowup();
                    clearFollowupFields();

                    nextButton.setBorderPainted(true);
                    nextButton.setFocusPainted(true);
                    nextButton.setText("Next");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
