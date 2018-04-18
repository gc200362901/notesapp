package notesapp;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Label userIdLabel;

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
    private ComboBox monthComboBox;

    @FXML
    private Label editErrLabel;

    @FXML
    private Button saveButton;

    public void getLoggedInUserId(int loggedInUserId) {
        String userIdString = Integer.toString(loggedInUserId);
        userIdLabel.setText(userIdString);
    }

    public void monthComboBoxSelected() throws SQLException {
        saveButton.setDisable(false);
        String userIdString = userIdLabel.getText();
        int userId = Integer.parseInt(userIdString);
        String monthSelected = monthComboBox.getValue().toString();
        Budget monthlyBudget = getSelectedMonthlyBudget(userId, monthSelected);
        populateEditTextFields(monthlyBudget);

    }

    private Budget getSelectedMonthlyBudget(int userId, String monthSelected) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Budget monthlyBudget = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");

            String sql = "SELECT * FROM tb_finance WHERE userId = ? AND monthDescription = ?";

            ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setString(2, monthSelected);

            rs = ps.executeQuery();

            while (rs.next()) {
                monthlyBudget = new Budget(rs.getInt("userId"), rs.getInt("monthId"),
                        rs.getString("monthDescription"), rs.getInt("mortgage"), rs.getInt("electricity"),
                        rs.getInt("gas"), rs.getInt("water"), rs.getInt("internet"), rs.getInt("car"),
                        rs.getInt("insurance"), rs.getInt("fuel"), rs.getInt("food"), rs.getInt("other"));
            }
        } catch (Exception e) {
            setEditErrLabelText(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return monthlyBudget;
    }

    private void populateEditTextFields(Budget monthlyBudget) {

        mortgageTextField.setText(Integer.toString(monthlyBudget.getMortgage()));
        electricityTextField.setText(Integer.toString(monthlyBudget.getElectricity()));
        gasTextField.setText(Integer.toString(monthlyBudget.getGas()));
        waterTextField.setText(Integer.toString(monthlyBudget.getWater()));
        internetTextField.setText(Integer.toString(monthlyBudget.getInternet()));
        carTextField.setText(Integer.toString(monthlyBudget.getCar()));
        insuranceTextField.setText(Integer.toString(monthlyBudget.getInsurance()));
        fuelTextField.setText(Integer.toString(monthlyBudget.getFuel()));
        foodTextField.setText(Integer.toString(monthlyBudget.getFood()));
        otherTextField.setText(Integer.toString(monthlyBudget.getOther()));

    }

    public void editSaveButton() throws SQLException {
        int mortgage, electricity, gas, water, internet, car, insurance, fuel, food, other;

        String userIdString = userIdLabel.getText();
        int userId = Integer.parseInt(userIdString);
        String monthSelected = monthComboBox.getValue().toString();

        mortgage = Integer.parseInt(mortgageTextField.getText());
        electricity = Integer.parseInt(electricityTextField.getText());
        gas = Integer.parseInt(gasTextField.getText());
        water = Integer.parseInt(waterTextField.getText());
        internet = Integer.parseInt(internetTextField.getText());
        car = Integer.parseInt(carTextField.getText());
        insurance = Integer.parseInt(insuranceTextField.getText());
        fuel = Integer.parseInt(fuelTextField.getText());
        food = Integer.parseInt(foodTextField.getText());
        other = Integer.parseInt(otherTextField.getText());
   
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");

            String sql = "UPDATE tb_finance "
                       + "SET mortgage = ?, electricity = ?, gas = ?, water = ?, internet = ?,"
                       + " car = ?, insurance = ?, fuel = ?, food = ?, other = ? "
                       + "WHERE userId = ? AND monthDescription = ?";

            ps = conn.prepareStatement(sql);

            ps.setInt(1, mortgage);
            ps.setInt(2, electricity);
            ps.setInt(3, gas);
            ps.setInt(4, water);
            ps.setInt(5, internet);
            ps.setInt(6, car);
            ps.setInt(7, insurance);
            ps.setInt(8, fuel);
            ps.setInt(9, food);
            ps.setInt(10, other);
            ps.setInt(11, userId);
            ps.setString(12, monthSelected);
            
            ps.executeUpdate();
        }
        catch (Exception e) {
            setEditErrLabelText(e);
        }
        finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * This method sets the text for the error message label when no exception
     * argument is given
     */
    private void setEditErrLabel() {
        editErrLabel.setText("");
    }

    /**
     * This method sets the text for the error message label when an exception
     * argument is given
     *
     * @param e To set the exception message
     */
    private void setEditErrLabelText(Exception e) {
        editErrLabel.setText(e.getMessage());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setEditErrLabel();
        saveButton.setDisable(true);

        monthComboBox.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    }

}
