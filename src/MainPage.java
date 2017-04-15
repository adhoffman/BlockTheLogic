import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by alexhoffman on 4/12/17.
 */
public class MainPage extends JFrame{
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

    public MainPage(MySQLConnector connector) {
        super("BlockTheLogic");
        this.connector = connector;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension (1200, 750));

        setContentPane(rootPane);

        setVisible(true);


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
