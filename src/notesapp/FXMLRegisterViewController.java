package notesapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Sean Noddin
 */
public class FXMLRegisterViewController implements Initializable {

    @FXML
    private TextField registerUsernameTextfield;

    @FXML
    private PasswordField registerPwField;

    @FXML
    private PasswordField registerConfirmPwfield;

    @FXML
    private Label registerErrLabel;

    /**
     * This method sets the text for the error message label when no exception
     * argument is given
     */
    private void setRegisterErrLabel() {
        registerErrLabel.setText("");
    }

    /**
     * This method sets the text for the error message label when an exception
     * argument is given
     *
     * @param e To set the exception message
     */
    private void setRegisterErrLabelText(Exception e) {
        registerErrLabel.setText(e.getMessage());
    }

    /**
     * This method validates the password entered by the user is longer then 6
     * characters
     *
     * @param password To set the password
     * @return
     */
    private boolean validatePassword(String password) {
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be 6 characters");
        }
        return true;
    }

    /**
     * This method changes scenes to the Login view
     *
     * @param event
     * @throws IOException
     */
    private void sceneChangeToLogin(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "FXMLLoginView.fxml", "Login");
    }

    /**
     * This method displays a notification to the user that their contact has
     * been saved to the database Uses controlsfx-8.40.14.jar from
     * http://fxexperience.com/controlsfx/
     */
    private void submitNotification() {
        Notifications.create()
                .title("Registration Success!")
                .text("          Please Login")
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.CENTER)
                .showInformation();
    }

    /**
     * When the Register button is pressed, this method gets the username, 
     * password and confirm password. It checks that the password and confirm
     * pass word match and then calls the validatePassword method.
     * Then it uses the getSalt method to generate a salt. Then it uses the getPW
     * method with the salt and password as arguments to generate a hashed 
     * password. Then it stores the username, hashed password and salt in a User
     * object and commits the fields to the database. Then queries the database
     * to get the associated user id. It uses the user id to create a new Budget
     * in the tb_finance table for the new register user. Then it changes to
     * the Login scene.
     * @param event 
     */
    public void registerButton(ActionEvent event) {
        setRegisterErrLabel();
        String username = registerUsernameTextfield.getText();
        String password = registerPwField.getText();
        String confirmPassword = registerConfirmPwfield.getText();

        if (password.equals(confirmPassword)) {
            try {
                validatePassword(password);
                byte[] salt = PasswordGenerator.getSalt();
                String hashPw = PasswordGenerator.getPW(password, salt);
                User newUser = new User(username, hashPw, salt);
                newUser.commitToDatabase();
                int userId = newUser.returnDbUserId();
                Budget.createNewBudget(userId);
                submitNotification();
                sceneChangeToLogin(event);
            } catch (Exception e) {
                setRegisterErrLabelText(e);
            }
        } else {
            registerErrLabel.setText("Passwords do not match!");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setRegisterErrLabel();

    }

}
