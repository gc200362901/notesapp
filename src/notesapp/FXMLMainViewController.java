package notesapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public void getLoggedInUsername(String loggedInUsername) {
        this.welcomeLabel.setText("Welcome " + loggedInUsername);
    }

    public String getLabelUsernameFromLabel() {
        String labelText = welcomeLabel.getText();

        return labelText;
    }

    public String parseLabelText(String labelText) {
        String username = labelText.substring(8);
        
        return username;
    }
    
    public void summaryButtonPressed(ActionEvent event) throws SQLException, IOException {               
        String labelText = getLabelUsernameFromLabel();
        String username = parseLabelText(labelText);
        int loggedInUserId = User.returnDbUserId(username);

        sceneChangeSummary(event, loggedInUserId);
    }

    public void editButtonPressed(ActionEvent event) throws IOException, SQLException {
        
        String labelText = getLabelUsernameFromLabel();
        String username = parseLabelText(labelText);
        int loggedInUserId = User.returnDbUserId(username);

        sceneChangeEdit(event, loggedInUserId);
    }

    /**
     * This method changes scenes to the Edit view
     *
     * @param event
     * @throws IOException
     */
    public void sceneChangeEdit(ActionEvent event, int loggedInUserId) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenesUserId(event, "FXMLEditView.fxml", "Edit", loggedInUserId);
    }
    
    
    /**
     * This method changes scenes to the Edit view
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
