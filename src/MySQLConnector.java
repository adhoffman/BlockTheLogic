import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * Created by alexhoffman on 1/28/17.
 */
public class MySQLConnector {
    private Connection connection;
    private Statement statement;
    private String ipAddress="localhost";
    private String port= "3306";
    private String database = "TEST_LOGICBLOCKSTUDIOS";
    private String user= "student";
    private String password= "student";

    private ResultSetWrapper wrapper;

    public MySQLConnector(){
        wrapper = new ResultSetWrapper();
    }


    public ArrayList<Contact> getFollowup() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://"+this.ipAddress+":"+this.port+"/"+this.database, this.user, this.password);
        statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet result = statement.executeQuery("SELECT * FROM CONTACT WHERE FOLLOWUP_DATE > \"2016-01-01\";");

        return wrapper.getContactList(result);

    }

    public void addContact(String query) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+this.ipAddress+":"+this.port+"/"+this.database, this.user, this.password);
        statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.executeUpdate(query);
    }

    public void updateFollowupDate(String query) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+this.ipAddress+":"+this.port+"/"+this.database, this.user, this.password);
        statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.executeUpdate(query);
    }

    public void createNoteForContact(String query) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+this.ipAddress+":"+this.port+"/"+this.database, this.user, this.password);
        statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.executeUpdate(query);
    }
}

