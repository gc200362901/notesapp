package notesapp;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Sean Noddin
 */
public class FXMLLoginViewController implements Initializable {

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPwField;

    @FXML
    private Label loginErrLabel;

    /**
     * This method changes scenes to the Register view when the register button
     * is pressed
     *
     * @param event
     * @throws IOException
     */
    public void registerNewUser(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "FXMLRegisterView.fxml", "Register");
    }

    /**
     * This method connects to the database and executes the methods for
     * validating the username and password. It displays any errors and closes
     * the connection
     *
     * @param event
     * @throws SQLException
     */
    public void loginButton(ActionEvent event) throws SQLException {
        setLoginErrLabel();

        String username = loginUsernameField.getText();
        String password = loginPwField.getText();

        Connection conn = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");
            statement = conn.createStatement();
            TreeSet usernames = User.collectUsernames(rs, statement);
            try {
                validateUsernameLogin(usernames, username);
                try {
                    String dbHashPw = getDbHashPw(conn, rs, preparedStatement, username);
                    byte[] dbSalt = getDbSalt(conn, rs, preparedStatement, username);
                    validatePassword(password, dbHashPw, dbSalt, event);
                } catch (Exception e) {
                    setLoginErrLabelText(e);
                }
            } catch (Exception e) {
                setLoginErrLabelText(e);
            }
        } catch (Exception e) {
            setLoginErrLabelText(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    
    /**
     * This method queries the database and stores all the usernames in a
     * TreeSet for efficient searching
     *
     * @param rs
     * @param statement
     * @return a TreeSet of usernames
     * @throws SQLException
     */
    /*
    private TreeSet collectUsernames(ResultSet rs, Statement statement) throws SQLException {
        TreeSet<String> usernames = new TreeSet<>();

        rs = statement.executeQuery("SELECT username FROM tb_user");

        while (rs.next()) {
            usernames.add(rs.getString("username"));
        }
        return usernames;
    }*/
    
    /**
     * This method validates the username entered is present in the TreeSet of
     * usernames
     *
     * @param usernames A collection of usernames
     * @param username To set the username entered in the text field
     */
    private void validateUsernameLogin(TreeSet usernames, String username) {
        boolean isValid = usernames.stream()
                                   .anyMatch(u -> u.equals(username));
        if (!isValid) {
            throw new IllegalArgumentException("Not a valid username");
        }
    }

    /**
     * This method queries the database using a prepared statement. It gets
     * the associated hashed password and returns it in a string
     * @param conn
     * @param rs
     * @param preparedStatement
     * @param username The username for the query
     * @return The hashed password
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    private String getDbHashPw(Connection conn, ResultSet rs, PreparedStatement preparedStatement,
            String username) throws SQLException, NoSuchAlgorithmException, IOException {

        String hashedPassword = null;

        String sql = ("SELECT hashPw FROM tb_user "
                + "WHERE username = ?");

        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, username);

        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            hashedPassword = rs.getString("hashPw");
        }
        return hashedPassword;
    }

    /**
     * This method queries the database using a prepared statement. it gets
     * the associated blob and converts it to a byte array 
     * and then returns the array
     * @param conn
     * @param rs
     * @param preparedStatement
     * @param username The username for the query
     * @return The salt
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    private byte[] getDbSalt(Connection conn, ResultSet rs, PreparedStatement preparedStatement,
            String username) throws SQLException, NoSuchAlgorithmException, IOException {

        byte[] salt = null;

        String sql = ("SELECT salt FROM tb_user "
                + "WHERE username = ?");

        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, username);

        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Blob saltBlob = rs.getBlob("salt");

            int blobLength = (int) saltBlob.length();
            salt = saltBlob.getBytes(1, blobLength);
        }
        return salt;
    }

    /**
     * This method uses the password and salt to generate a hashed password.
     * Then it compares the hashed password to the hashed password from the 
     * database. If they match is executes the sceneChangeMain method.
     * @param password The password entered by the user
     * @param dbHashPw The hashed password from the database
     * @param dbSalt The salt from the database
     * @param event
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    private void validatePassword(String password, String dbHashPw, byte[] dbSalt, ActionEvent event) throws NoSuchAlgorithmException, IOException {
        String newHashedPw = PasswordGenerator.getPW(password, dbSalt);

        if (dbHashPw.equals(newHashedPw)) {
            sceneChangeMain(event);
        } else {
            throw new IllegalArgumentException("Not a valid password");
        }
    }

    /**
     * This method changes scenes to the Main view
     * @param event
     * @throws IOException 
     */
    public void sceneChangeMain(ActionEvent event) throws IOException {
        String loggedInUsername = loginUsernameField.getText();
        
        SceneChanger sc = new SceneChanger();
        sc.changeScenesWithUsername(event, "FXMLMainView.fxml", "Main", loggedInUsername);
    }

    /**
     * This method sets the text for the error message label when no exception
     * argument is given
     */
    private void setLoginErrLabel() {
        loginErrLabel.setText("");
    }

    /**
     * This method sets the text for the error message label when an exception
     * argument is given
     *
     * @param e To set the exception message
     */
    private void setLoginErrLabelText(Exception e) {
        loginErrLabel.setText(e.getMessage());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLoginErrLabel();
    }

}
