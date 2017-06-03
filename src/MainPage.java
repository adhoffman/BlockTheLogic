import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private ArrayList<Project> activeProjectList;
    private ArrayList<Project> revisionProjectsList;
    private ArrayList<Project> pendingPaymentProjectList;
    private ArrayList<Project> completeProjectsList;

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
    private JButton PP_ChangeProjectStatusButton;
    private JButton PD_ChangeProjectStatusButton;
    private JComboBox PD_PromoteStatusOptions;
    private JComboBox PP_StatusOptions;
    private JPanel ActiveTab;
    private JComboBox AP_StatusOptions;
    private JButton AP_ChangeProjectStatus;
    private JTable AP_ActiveTable;
    private JComboBox RP_StatusOptions;
    private JTable RP_RevisionProjectsTable;
    private JButton RP_ChangeStatus;
    private JPanel RevisionTab;
    private JPanel PendingPaymentTab;
    private JTable PendP_PendingPaymentTable;
    private JButton PendP_ChangeStatusButton;
    private JComboBox PendP_StatusOptions;
    private JPanel CompleteTab;
    private JTable CP_CompleteTable;
    private JButton CP_ChangeStatus;
    private JComboBox CP_StatusOptions;
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


        populatProjectStatusOptionsComboBoxes();

        populateProjectsTables();


        addProspectButton.addActionListener((ActionEvent e) -> {
            try {

                controller.addContact(new Contact(firstNameField.getText(),lastNameField.getText(),artistgroupNameField.getText(),emailField.getText(),facebookNameField.getText(),websitesField.getText(), referencesField.getText()));

                clearNewContactFields();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });

        FU_nextButton.addActionListener((ActionEvent e) -> {


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

        });
        FU_grabFollowupContactsButton.addActionListener((ActionEvent e) -> {
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
        });

        PA_addProjectButton.addActionListener(e -> {


            try {
                controller.addProject(new Project(PA_projectTitleText.getText(),PA_contactCombobox.getSelectedItem().toString(),PA_startDateText.getText(),PA_endDateText.getText(),PA_dueDateText.getText(),PA_songCountCombo.getSelectedItem().toString(),PA_serviceTypeCombo.getSelectedItem().toString(),PA_totalCost.getText()));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            resetAddProjectFields();

        });


        /*
        PP_ChangeProjectStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                for(int i=0; i< PP_projectTable.getColumnCount();i++) {

                    for(int j = 0; j< potentialProjectList.size(); j++){
                        {
                            String currentCell = PP_projectTable.getValueAt(PP_projectTable.getSelectedRow(), i).toString();
                            if (potentialProjectList.get(j).getContactEmail().equals(currentCell));

                                try {
                                    controller.changeProjectStatus(potentialProjectList.get(j), PP_StatusOptions.getSelectedItem().toString());
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


        PD_ChangeProjectStatusButton.addActionListner(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            for(int i=0; i< PD_pendDepProjectTable.getColumnCount();i++) {

                for(int j = 0; j< pendingDepositProjectList.size(); j++){
                    {
                        String currentCell = PD_pendDepProjectTable.getValueAt(PD_pendDepProjectTable.getSelectedRow(), i).toString();
                        if (pendingDepositProjectList.get(j).getContactEmail().equals(currentCell)){

                            if(!PD_PromoteStatusOptions.getSelectedItem().equals("")) {
                                try {
                                    controller.changeProjectStatus(pendingDepositProjectList.get(j), PD_PromoteStatusOptions.getSelectedItem().toString());

                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }

                        }
                    }

                }
            }
            try {
                refreshPendingDepositProjectsAndPopulateWindow();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    });



        AP_ChangeProjectStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(int i=0; i< AP_ActiveTable.getColumnCount();i++) {

                    for(int j = 0; j< activeProjectList.size(); j++){
                        {
                            System.out.println(AP_ActiveTable.getValueAt(AP_ActiveTable.getSelectedRow(), i).toString());
                            String currentCell = AP_ActiveTable.getValueAt(AP_ActiveTable.getSelectedRow(), i).toString();
                            if (activeProjectList.get(j).getContactEmail().equals(currentCell)){
                                System.out.println(currentCell+ "  "+activeProjectList.get(j).getTitle());

                                if(!AP_StatusOptions.getSelectedItem().equals("")) {
                                    try {
                                        controller.changeProjectStatus(activeProjectList.get(j), AP_StatusOptions.getSelectedItem().toString());

                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                }

                            }
                        }

                    }
                }
                try {
                    refreshAndPopulateActiveProjects();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        RP_ChangeStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(int i=0; i< RP_RevisionProjectsTable.getColumnCount();i++) {

                    for(int j = 0; j< revisionProjectsList.size(); j++){
                        {
                            String currentCell = RP_RevisionProjectsTable.getValueAt(RP_RevisionProjectsTable.getSelectedRow(), i).toString();
                            if (revisionProjectsList.get(j).getContactEmail().equals(currentCell)){

                                if(!RP_StatusOptions.getSelectedItem().equals("")) {
                                    try {
                                        controller.changeProjectStatus(revisionProjectsList.get(j), RP_StatusOptions.getSelectedItem().toString());

                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                }

                            }
                        }

                    }
                }
                try {
                    poopulateRevisionProjectsTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        */
    }



    private void populatProjectStatusOptionsComboBoxes() {

        populatePotentialStatusComboBox();
        populatePendingDepositStatusComboBox();
        populateActiveStatusComboBox();
        populateRevisionStatusCombobox();
        populatePendingPaymentStatusCombobox();
        populateCompleteStatusCombobox();


    }
    private void populatePendingDepositStatusComboBox() {

        PD_PromoteStatusOptions.addItem(ProjectStatus.AWAITING_DATE);
        PD_PromoteStatusOptions.addItem(ProjectStatus.TRACKING);
        PD_PromoteStatusOptions.addItem(ProjectStatus.MIXING);
        PD_PromoteStatusOptions.addItem(ProjectStatus.MASTERING);
        PD_PromoteStatusOptions.addItem(ProjectStatus.CANCELLED);

    }


    private void populateCompleteStatusCombobox() {
        CP_StatusOptions.addItem(ProjectStatus.ARCHIVE);
        CP_StatusOptions.addItem(ProjectStatus.PENDING_PAYMENT);
        CP_StatusOptions.addItem(ProjectStatus.REVISION);
        CP_StatusOptions.addItem(ProjectStatus.CANCELLED);

    }

    private void populatePendingPaymentStatusCombobox() {
        PendP_StatusOptions.addItem(ProjectStatus.COMPLETE);
        PendP_StatusOptions.addItem(ProjectStatus.REVISION);
        PendP_StatusOptions.addItem(ProjectStatus.CANCELLED);

    }

    private void populateRevisionStatusCombobox() {
        RP_StatusOptions.addItem(ProjectStatus.AWAITING_DATE);
        RP_StatusOptions.addItem(ProjectStatus.TRACKING);
        RP_StatusOptions.addItem(ProjectStatus.MIXING);
        RP_StatusOptions.addItem(ProjectStatus.MASTERING);
        RP_StatusOptions.addItem(ProjectStatus.PENDING_PAYMENT);
        RP_StatusOptions.addItem(ProjectStatus.COMPLETE);
        RP_StatusOptions.addItem(ProjectStatus.CANCELLED);
    }

    private void populateActiveStatusComboBox() {

        AP_StatusOptions.addItem(ProjectStatus.AWAITING_DATE);
        AP_StatusOptions.addItem(ProjectStatus.TRACKING);
        AP_StatusOptions.addItem(ProjectStatus.MIXING);
        AP_StatusOptions.addItem(ProjectStatus.MASTERING);
        AP_StatusOptions.addItem(ProjectStatus.REVISION);
        AP_StatusOptions.addItem(ProjectStatus.PENDING_PAYMENT);
        AP_StatusOptions.addItem(ProjectStatus.COMPLETE);
        AP_StatusOptions.addItem(ProjectStatus.CANCELLED);
    }

    private void populatePotentialStatusComboBox() {
        PP_StatusOptions.addItem(ProjectStatus.PENDING_DEPOSIT);
        PP_StatusOptions.addItem(ProjectStatus.CANCELLED);
    }

    private void populateProjectsTables() throws SQLException {
        populatePotentialProjectsTable();
        populatePendingDepositProjectsTable();
        populateActiveProjectTable();
        poopulateRevisionProjectsTable();
        populatePendingPaymentTable();
        populateCompleteTable();

    }


    private void populatePendingDepositProjectsTable() throws SQLException {
        pendingDepositProjectList = controller.getPendingDepsitProjects();
        PD_pendDepProjectTable.setModel(populateProjectTable(pendingDepositProjectList));
    }


    private void populatePotentialProjectsTable() throws SQLException {

        potentialProjectList = controller.getPotentialProjects();
        PP_projectTable.setModel(populateProjectTable(potentialProjectList));

    }

    private void populateActiveProjectTable() throws SQLException {
        activeProjectList = controller.getActiveProjects();
        AP_ActiveTable.setModel(populateProjectTable(activeProjectList));
    }


    private void populateCompleteTable() throws SQLException {
        completeProjectsList = controller.getCompleteProjects();
        CP_CompleteTable.setModel(populateProjectTable(completeProjectsList));
    }

    private void populatePendingPaymentTable() throws SQLException {

        pendingPaymentProjectList = controller.getPendingPaymentProjects();
        PendP_PendingPaymentTable.setModel(populateProjectTable(pendingPaymentProjectList));
    }


    private void poopulateRevisionProjectsTable() throws SQLException {

        revisionProjectsList = controller.getRevisionProjects();
        RP_RevisionProjectsTable.setModel(populateProjectTable(revisionProjectsList));
    }

    private DefaultTableModel populateProjectTable(ArrayList<Project> list) throws SQLException {


        String col[] = {"Status","Title","Email","Services","Song Count","Due Date","Total Cost"};

        DefaultTableModel tableModel = new DefaultTableModel(col,0);

        for(int i = 0;i<list.size();i++){
            Project project = list.get(i);

            String row[] = {project.getStatus(),project.getTitle(),project.getContactEmail(), project.getServiceType(), Integer.toString(project.getSongCount()),project.getDueDate(),project.getTotalCost().toString()};
            tableModel.addRow(row);

        }

        return tableModel;
    }


    private void setupAutoSortTableHeaders() {
        PP_projectTable.setAutoCreateRowSorter(true);
        PD_pendDepProjectTable.setAutoCreateRowSorter(true);

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
        PA_songCountCombo.addItem("20+");
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
