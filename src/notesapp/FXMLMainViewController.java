package notesapp;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author warmcheese
 */
public class FXMLMainViewController implements Initializable {

    @FXML
    private Label welcomeLabel;

    /**
     * This takes the logged in username from the login scene as an argument and
     * sets it in a label
     * @param loggedInUsername To set the logged in username
     */
    public void getLoggedInUsername(String loggedInUsername) {
        this.welcomeLabel.setText("Welcome " + loggedInUsername);
    }

    /**
     * This method gets the username string from a label
     * @return a username string
     */
    public String getLabelUsernameFromLabel() {
        String labelText = welcomeLabel.getText();

        return labelText;
    }

    /**
     * This method removes just the username from the string
     * @param labelText
     * @return 
     */
    public String parseLabelText(String labelText) {
        String username = labelText.substring(8);
        
        return username;
    }
    
    /**
     * When the Summary scene change button is pressed, this method gets the 
     * username string from the label. Then it calls the returnDbUserName 
     * method with the username string as an argument and does a query for the 
     * matching user id. Then it calls the sceneChangeSummary method
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    public void summaryButtonPressed(ActionEvent event) throws SQLException, IOException {               
        String labelText = getLabelUsernameFromLabel();
        String username = parseLabelText(labelText);
        int loggedInUserId = User.returnDbUserId(username);

        sceneChangeSummary(event, loggedInUserId);
    }

    /**
     * When the Edit scene change button is pressed, this method gets the 
     * username string from the label. Then it calls the returnDbUserName 
     * method with the username string as an argument and does a query for the 
     * matching user id. Then it calls the sceneChangeEdit method
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    public void editButtonPressed(ActionEvent event) throws IOException, SQLException {
        
        String labelText = getLabelUsernameFromLabel();
        String username = parseLabelText(labelText);
        int loggedInUserId = User.returnDbUserId(username);

        sceneChangeEdit(event, loggedInUserId);
    }

    /**
     * This method changes scenes to the Edit view using the logged in user id
     * as an argument to be passed to the Edit scene
     * @param event
     * @throws IOException
     */
    public void sceneChangeEdit(ActionEvent event, int loggedInUserId) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenesUserId(event, "FXMLEditView.fxml", "Edit", loggedInUserId);
    }
    
    
    /**
     * This method changes scenes to the Summary view using the logged in user id
     * as an argument to be passed to the Summary scene
     *
     * @param event
     * @throws IOException
     */
    public void sceneChangeSummary(ActionEvent event, int loggedInUserId) throws IOException, SQLException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenesUserIdToSummary(event, "FXMLSummaryView.fxml", "Summary", loggedInUserId);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
