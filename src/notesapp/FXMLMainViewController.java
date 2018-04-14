/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notesapp;

import java.io.IOException;
import java.net.URL;
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
    
    @FXML private Label welcomeLabel;
    
    public void getLoggedInUsername(String loggedInUsername) {
        this.welcomeLabel.setText("Welcome "+loggedInUsername);
    }

    /**
     * This method changes scenes to the Edit view
     *
     * @param event
     * @throws IOException
     */
    public void sceneChangeEdit(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "FXMLEditView.fxml", "Edit");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
