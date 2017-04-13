import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by alexhoffman on 4/12/17.
 */
public class MainPage extends JFrame{
    private JButton runQueryButton;
    private JTextField textField1;
    private MySQLConnector connector;
    private JPanel rootPane;
    private JTabbedPane Tabz;
    private JPanel FollowupTab;
    private JPanel ActiveTab;
    private JPanel ProspectTab;
    private JPanel PendingDepositTab;
    private JPanel Activetab;
    private JPanel RevisionTab;
    private JPanel PendingPayTab;
    private JPanel CompleteTab;
    private JPanel ClientsTab;
    private JPanel ReportsTab;

    public MainPage(MySQLConnector connector) {
        super("BlockTheLogic");
        this.connector = connector;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension (750, 750));

        setContentPane(rootPane);

        setVisible(true);


    }


    private void createUIComponents() {

    }
}
