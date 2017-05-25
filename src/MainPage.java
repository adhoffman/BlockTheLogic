import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by alexhoffman on 4/12/17.
 */
public class MainPage extends JFrame{
    private ArrayList<Contact> contactList;
    private ArrayList<Note> noteList;
    private ArrayList<Project> potentialProjectList;
    private ArrayList<Project> pendingDepositProjectList;

    private JButton runQueryButton;
    private JTextField firstNameField;
    private DataController controller;
    private JPanel rootPane;
    private JTabbedPane tabbedPane1;
    private JTabbedPane WorkflowTab;
    private JPanel AddContactTab;
    private JPanel FollowupTab;
    private JPanel PotentialTab;
    private JPanel ClientsTab;
    private JPanel ReportsTab;
    private JButton addProspectButton;
    private JTextField facebookNameField;
    private JTextField lastNameField;
    private JTextField artistgroupNameField;
    private JTextField emailField;
    private JTextField websitesField;
    private JTextField referencesField;
    private JButton FU_nextButton;
    private JButton FU_grabFollowupContactsButton;
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
    private JTable FU_noteTable;
    private JPanel ProjectTab;
    private JButton PA_addProjectButton;
    private JTextField PA_projectTitleText;
    private JTextField PA_endDateText;
    private JTextField PA_startDateText;
    private JLabel PA_projectTitleLabel;
    private JComboBox PA_contactCombobox;
    private JComboBox PA_songCountCombo;
    private JComboBox PA_serviceTypeCombo;
    private JTextField PA_totalCost;
    private JTextField PA_dueDateText;
    private JLabel PA_contactLabel;
    private JLabel PA_startDateLabel;
    private JLabel PA_endDateLabel;
    private JLabel PA_songCountLabel;
    private JLabel PA_serviceTypeLabel;
    private JLabel PA_totalCostLabel;
    private JLabel PA_dueDateLabel;
    private JButton PP_getPotentialProjectsButton;
    private JTable PP_projectTable;
    private JPanel PendingDepositTab;
    private JButton PD_getPendDepProjects;
    private JTable PD_pendDepProjectTable;
    private JButton PP_closeSelectedProject_Button;
    private JButton PP_promoteToPDButton;
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
        disableFollowUpNextButton();

        setupProjectAddComboBoxes();

        setupAutoSortTableHeaders();

        addProspectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    controller.addContact(new Contact(firstNameField.getText(),lastNameField.getText(),artistgroupNameField.getText(),emailField.getText(),facebookNameField.getText(),websitesField.getText(), referencesField.getText()));

                    clearNewContactFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        FU_nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if(FU_messageTextArea.getText().length()>500){
                    FU_alertLabel.setText("Message is "+FU_messageTextArea.getText().length()+". Please limit to 500.");
                }else {

                    //Send out queries updating current account, and update counter
                    try {
                        setFollowupDateforCurrentContact(contactList.get(counter));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    //Add note to contact
                    try {
                        if(FU_messageTextArea.getText().length()>0) {
                            createNoteForCurrentUser(contactList.get(counter), FU_messageTextArea.getText(), FU_communicationComboBox.getSelectedItem().toString());
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    counter += 1;


                    //If end of list, clear fields and disable button
                    if (contactList.size() == counter) {

                        endOfFollowupContacts();

                    } else {
                        if ((followupTextAreaLargerThanZero()) && (emailFieldNotBlank())) {

                            FU_alertLabel.setText("Messagebox Populdated");
                            if (contactList.size() == counter) {
                                endOfFollowupContacts();
                                clearNoteTable();


                            } else//if Not end fo list, get next account and populate textfields
                            {
                                Contact contact = contactList.get(counter);
                                setFollupTextFields(contact);

                                try {
                                    populateNoteListTableForContact();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }


                        } else {
                            FU_alertLabel.setText("Messagebox Not Populated");

                        }
                    }

                }

            }
        });
        FU_grabFollowupContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {



                    contactList = controller.getFollowup();
                    clearFollowupFields();

                    FU_nextButton.setBorderPainted(true);
                    FU_nextButton.setFocusPainted(true);
                    FU_nextButton.setText("Next");

                    if(contactList.size()==counter){
                        endOfFollowupContacts();
                        clearNoteTable();
                    }else
                    {
                        Contact contact = contactList.get(counter);
                        setFollupTextFields(contact);
                        populateNoteListTableForContact();

                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                counter = 0;
            }
        });

        PA_addProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    controller.addProject(new Project(PA_projectTitleText.getText(),PA_contactCombobox.getSelectedItem().toString(),PA_startDateText.getText(),PA_endDateText.getText(),PA_dueDateText.getText(),PA_songCountCombo.getSelectedItem().toString(),PA_serviceTypeCombo.getSelectedItem().toString(),PA_totalCost.getText()));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                resetAddProjectFields();

            }
        });

        PP_getPotentialProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                populatePotentialProjectsTable();

            }
        });

        PD_getPendDepProjects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    pendingDepositProjectList = controller.getPendingDepsitProjects();
                    populatePendingDepositProjectsTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        PP_promoteToPDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                for(int i=0; i< PP_projectTable.getColumnCount();i++) {
                    //System.out.println(PP_projectTable.getValueAt(PP_projectTable.getSelectedRow(), i));

                    for(int j = 0; j< potentialProjectList.size(); j++){
                        {
                            String currentCell = PP_projectTable.getValueAt(PP_projectTable.getSelectedRow(), i).toString();
                            if (potentialProjectList.get(j).getContactEmail().equals(currentCell)){
                                System.out.println(currentCell+ "  "+potentialProjectList.get(j).getTitle());

                                try {
                                    controller.changeProjectToDepositPending(potentialProjectList.get(j), ProjectStatus.PENDING_DEPOSIT);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }

                    }
                }

                populatePotentialProjectsTable();

            }
        });
    }

    private void populatePotentialProjectsTable() {
        try {
            potentialProjectList = controller.getPotentialProjects();
            populateActiveProjectsTable();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    private void setupAutoSortTableHeaders() {
        PP_projectTable.setAutoCreateRowSorter(true);
        PD_pendDepProjectTable.setAutoCreateRowSorter(true);

    }

    private void populatePendingDepositProjectsTable() {

        String col[] = {"Status","Title","Email","Services","Song Count","Due Date","Total Cost"};

        DefaultTableModel tableModel = new DefaultTableModel(col,0);

        for(int i = 0;i<pendingDepositProjectList.size();i++){
            Project project = pendingDepositProjectList.get(i);

            String row[] = {project.getStatus(),project.getTitle(),project.getContactEmail(), project.getServiceType(), Integer.toString(project.getSongCount()),project.getDueDate(),project.getTotalCost().toString()};
            tableModel.addRow(row);

        }

        PD_pendDepProjectTable.setModel(tableModel);
    }

    private void populateActiveProjectsTable() {

        String col[] = {"Status","Title","Email","Services","Song Count","Due Date","Total Cost"};

        DefaultTableModel tableModel = new DefaultTableModel(col,0);

        for(int i = 0; i< potentialProjectList.size(); i++){
            Project project = potentialProjectList.get(i);

            String row[] = {project.getStatus(),project.getTitle(),project.getContactEmail(), project.getServiceType(), Integer.toString(project.getSongCount()),project.getDueDate(),project.getTotalCost().toString()};
            tableModel.addRow(row);

        }

        PP_projectTable.setModel(tableModel);

    }

    private void resetAddProjectFields() {
        PA_projectTitleText.setText("");
        PA_contactCombobox.setSelectedItem("");
        PA_startDateText.setText("");
        PA_endDateText.setText("");
        PA_dueDateText.setText("");
        PA_songCountCombo.setSelectedItem("");
        PA_serviceTypeCombo.setSelectedItem("");
        PA_totalCost.setText("");

    }

    private void setupProjectAddComboBoxes() throws SQLException {

        setupProjectContactComboBox();
        setupSongCountComboBox();
        setupServiceTypeComboBox();

    }

    private void setupServiceTypeComboBox() {
        PA_serviceTypeCombo.addItem("");
        PA_serviceTypeCombo.addItem("Tracking");
        PA_serviceTypeCombo.addItem("Mixing");
        PA_serviceTypeCombo.addItem("Mastering");
        PA_serviceTypeCombo.addItem("Tracking, Mixing");
        PA_serviceTypeCombo.addItem("Tracking, Mastering");
        PA_serviceTypeCombo.addItem("Mixing, Mastering");
        PA_serviceTypeCombo.addItem("Tracking, Mixing, Mastering");
    }

    private void setupSongCountComboBox() {
        PA_songCountCombo.addItem("");
        PA_songCountCombo.addItem("1");
        PA_songCountCombo.addItem("2");
        PA_songCountCombo.addItem("3");
        PA_songCountCombo.addItem("4");
        PA_songCountCombo.addItem("5");
        PA_songCountCombo.addItem("6");
        PA_songCountCombo.addItem("7");
        PA_songCountCombo.addItem("8");
        PA_songCountCombo.addItem("9");
        PA_songCountCombo.addItem("10");
        PA_songCountCombo.addItem("11");
        PA_songCountCombo.addItem("12");
        PA_songCountCombo.addItem("13");
        PA_songCountCombo.addItem("14");
        PA_songCountCombo.addItem("15");
        PA_songCountCombo.addItem("16");
        PA_songCountCombo.addItem("17");
        PA_songCountCombo.addItem("18");
        PA_songCountCombo.addItem("19");
        PA_songCountCombo.addItem("20");
    }

    private void setupProjectContactComboBox() throws SQLException {
        ArrayList<Contact> contacts = controller.getContactListbyNameAndEmail();

        Collections.sort(contacts);

        for(int i =0;contacts.size()>i;i++) {
            PA_contactCombobox.addItem(contacts.get(i).getEmail());

        }

    }


    private void disableFollowUpNextButton() {
        FU_nextButton.setBorderPainted(false);
        FU_nextButton.setFocusPainted(false);
        FU_nextButton.setText("");
    }

    private void clearNoteTable() {
        DefaultTableModel tableModel = (DefaultTableModel) FU_noteTable.getModel();
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }

        FU_noteTable.setModel(tableModel);

    }

    private void populateNoteListTableForContact() throws SQLException {
        noteList = controller.getNotes();
        DefaultTableModel noteTableModel = populateNoteTableModel(contactList.get(counter));
        FU_noteTable.setModel(noteTableModel);

    }


    private DefaultTableModel populateNoteTableModel(Contact contact) {
        String col[] = {"Date", "Medium", "Text"};

        DefaultTableModel tableModel = new DefaultTableModel(col,0);

        for(int i = 0; noteList.size()>i;i++){

            if(contact.getID() == noteList.get(i).getContactID()){

                String row[] = {noteList.get(i).getDate(),noteList.get(i).getContactMedium(),noteList.get(i).getNoteText()};
                tableModel.addRow(row);
            }

        }
        return tableModel;

    }

    private void createNoteForCurrentUser(Contact contact, String noteText, String communicationMedium) throws SQLException {
        controller.createNoteForContact(contact,noteText,communicationMedium);
    }


    private void endOfFollowupContacts() {
        clearFollowupFields();
        FU_nextButton.setBorderPainted(false);
        FU_nextButton.setFocusPainted(false);
        FU_nextButton.setText("No More Contacts");
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


    private void clearNewContactFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        artistgroupNameField.setText("");
        facebookNameField.setText("");
        websitesField.setText("");
        referencesField.setText("");
        emailField.setText("");
    }

}
