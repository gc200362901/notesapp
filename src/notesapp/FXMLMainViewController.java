/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

        System.out.println(loggedInUsername);
    }

    public String getLabelUsernameFromLabel() {
        String labelText = welcomeLabel.getText();

        return labelText;
    }

    public String parseLabelText(String labelText) {
        String username = labelText.substring(8);

        return username;
    }

    public void editButtonPressed(ActionEvent event) throws IOException, SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String labelText = getLabelUsernameFromLabel();
        String username = parseLabelText(labelText);
        int userId = User.returnDbUserId(username);

        ObservableList<Budget> userBudget = FXCollections.observableArrayList();
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");
            String sql = ("SELECT userId, monthId, monthDescription, mortgage, electricity, "
                    + "gas, water, internet, car, insurance, fuel, food, other "
                    + "FROM tb_finance WHERE userId = ?");
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Budget budget = new Budget(rs.getInt("userId"), rs.getInt("monthId"), 
                        rs.getString("monthDescription"), rs.getInt("mortgage"), 
                        rs.getInt("electricity"), rs.getInt("gas"), rs.getInt("water"), 
                        rs.getInt("internet"), rs.getInt("car"), rs.getInt("insurance"), 
                        rs.getInt("fuel"), rs.getInt("food"), rs.getInt("other"));
                
                userBudget.add(budget);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        sceneChangeEdit(event);
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

    }

}
