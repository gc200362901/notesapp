package notesapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Sean Noddin
 */
public class FXMLSummaryViewController implements Initializable {

    @FXML
    private Label userIdLabel;
    
    public void getLoggedInUserId(int loggedInUserId) {
        String userIdString = Integer.toString(loggedInUserId);
        userIdLabel.setText(userIdString);
    }
        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize
        (URL url, ResourceBundle rb
        
        
    

) {
        // TODO
    }    
    
}
