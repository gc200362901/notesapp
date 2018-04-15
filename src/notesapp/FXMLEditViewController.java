/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notesapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sean Noddin
 */
public class FXMLEditViewController implements Initializable {

    @FXML
    private TextField mortgageTextField;

    @FXML
    private TextField electricityTextField;

    @FXML
    private TextField gasTextField;

    @FXML
    private TextField waterTextField;

    @FXML
    private TextField internetTextField;

    @FXML
    private TextField carTextField;

    @FXML
    private TextField insuranceTextField;

    @FXML
    private TextField fuelTextField;

    @FXML
    private TextField foodTextField;

    @FXML
    private TextField otherTextField;

    @FXML
    private ComboBox<?> monthComboBox;

    @FXML
    private Label editErrLabel;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
