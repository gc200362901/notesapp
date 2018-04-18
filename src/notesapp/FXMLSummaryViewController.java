package notesapp;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Sean Noddin
 */
public class FXMLSummaryViewController implements Initializable {

    @FXML
    private BarChart summaryBarChart;

    @FXML
    private CategoryAxis month;

    @FXML
    private NumberAxis spendingTotal;
    
    private XYChart.Series monthlySpendingTotals;

    @FXML
    private Label mortgageSummaryLabel;

    @FXML
    private Label electricitySummaryLabel;

    @FXML
    private Label gasSummaryLabel;

    @FXML
    private Label waterSummaryLabel;

    @FXML
    private Label internetSummaryLabel;

    @FXML
    private Label carSummaryLabel;

    @FXML
    private Label insuranceSummaryLabel;

    @FXML
    private Label fuelSummaryLabel;

    @FXML
    private Label foodSummaryLabel;

    @FXML
    private Label otherSummaryLabel;

    @FXML
    private Label userIdLabel;

    public void getLoggedInUserId(int loggedInUserId) throws SQLException {
        String userIdString = Integer.toString(loggedInUserId);
        userIdLabel.setText(userIdString);

        ArrayList monthlyBudgets = getAllMonthlyBudgets();
        populateSummaryLabels(monthlyBudgets);
        populateSummaryBarChart(monthlyBudgets);

    }

    public ArrayList getAllMonthlyBudgets() throws SQLException {
        String userIdString = userIdLabel.getText();
        int userId = Integer.parseInt(userIdString);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Budget> monthlyBudgets = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");

            String sql = "SELECT * FROM tb_finance WHERE userId = ?";

            ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Budget monthlyBudget = new Budget(rs.getInt("userId"), rs.getInt("monthId"),
                        rs.getString("monthDescription"), rs.getInt("mortgage"), rs.getInt("electricity"),
                        rs.getInt("gas"), rs.getInt("water"), rs.getInt("internet"), rs.getInt("car"),
                        rs.getInt("insurance"), rs.getInt("fuel"), rs.getInt("food"), rs.getInt("other"));

                monthlyBudgets.add(monthlyBudget);
            }
        } catch (Exception e) {

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
        return monthlyBudgets;
    }
    
    private void populateSummaryBarChart(ArrayList monthlyBudgets) {
        monthlySpendingTotals = new XYChart.Series<>();

        month.setLabel("Months");
        spendingTotal.setLabel("$ Spent");

        monthlySpendingTotals.setName("Monthly Totals");

        getSummaryBarChartData(monthlyBudgets);

        summaryBarChart.getData().addAll(monthlySpendingTotals);
    }
    
    private void getSummaryBarChartData(ArrayList monthlyBudgets) {
        int janTotal = 0, febTotal = 0, marTotal = 0, aprTotal = 0, mayTotal = 0,
                junTotal = 0, julTotal = 0, augTotal = 0, sepTotal = 0, octTotal = 0,
                novTotal = 0, decTotal = 0;
        
        LinkedList<Integer> monthlyTotals = new LinkedList<>();
        ArrayList<Integer> month = new ArrayList<>();
        
        for(Object monthlyBudget : monthlyBudgets) {
            Budget budget = (Budget) monthlyBudget;
                       
            month.add(budget.getMortgage());
            month.add(budget.getElectricity());
            month.add(budget.getGas());
            month.add(budget.getWater());
            month.add(budget.getInternet());
            month.add(budget.getCar());
            month.add(budget.getInsurance());
            month.add(budget.getFuel());
            month.add(budget.getFood());
            month.add(budget.getOther());
            
            int monthTotal = month.stream()
                                  .mapToInt(i -> i.intValue())
                                  .sum();
            
            month.clear();

            monthlyTotals.add(monthTotal);
        }
        
        monthlySpendingTotals.getData().add(new XYChart.Data("Jan", monthlyTotals.get(0)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Feb", monthlyTotals.get(1)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Mar", monthlyTotals.get(2)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Apr", monthlyTotals.get(3)));
        monthlySpendingTotals.getData().add(new XYChart.Data("May", monthlyTotals.get(4)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Jun", monthlyTotals.get(5)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Jul", monthlyTotals.get(6)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Aug", monthlyTotals.get(7)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Sep", monthlyTotals.get(8)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Oct", monthlyTotals.get(9)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Nov", monthlyTotals.get(10)));
        monthlySpendingTotals.getData().add(new XYChart.Data("Dec", monthlyTotals.get(11)));
    }

    private void populateSummaryLabels(ArrayList monthlyBudgets) {
        int mortgage = 0, electricity = 0, gas = 0, water = 0, internet = 0,
                car = 0, insurance = 0, fuel = 0, food = 0, other = 0;

        for (Object monthlyBudget : monthlyBudgets) {
            Budget budget = (Budget) monthlyBudget;
            
            mortgage += budget.getMortgage();
            electricity += budget.getElectricity();
            gas += budget.getGas();
            water += budget.getWater();
            internet += budget.getInternet();
            car += budget.getCar();
            insurance += budget.getInsurance();
            fuel += budget.getFuel();
            food += budget.getFood();
            other += budget.getOther();
        }

        mortgageSummaryLabel.setText("$" + Integer.toString(mortgage));
        electricitySummaryLabel.setText("$" + Integer.toString(electricity));
        gasSummaryLabel.setText("$" + Integer.toString(gas));
        waterSummaryLabel.setText("$" + Integer.toString(water));
        internetSummaryLabel.setText("$" + Integer.toString(internet));
        carSummaryLabel.setText("$" + Integer.toString(car));
        insuranceSummaryLabel.setText("$" + Integer.toString(insurance));
        fuelSummaryLabel.setText("$" + Integer.toString(fuel));
        foodSummaryLabel.setText("$" + Integer.toString(food));
        otherSummaryLabel.setText("$" + Integer.toString(other));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
