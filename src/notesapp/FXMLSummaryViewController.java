package notesapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Sean Noddin
 */
public class FXMLSummaryViewController implements Initializable {

    @FXML
    private PieChart summaryPieChart;

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

    /**
     * This method takes in the logged in user id from the scene change,
     * ocnverts it to a string and sets the string in a label. Then it calls the
     * methods to populate all the summary information for the scene
     *
     * @param loggedInUserId To set the logged in user id
     * @throws SQLException
     */
    public void getLoggedInUserId(int loggedInUserId) throws SQLException {
        String userIdString = Integer.toString(loggedInUserId);
        userIdLabel.setText(userIdString);

        ArrayList monthlyBudgets = getAllMonthlyBudgets();
        populateSummaryLabels(monthlyBudgets);
        populateSummaryBarChart(monthlyBudgets);
        populateSummaryPieChart(monthlyBudgets);
    }

    /**
     * This method gets the user id from a label and converts it to an int. It
     * uses the user id to query the database and returns a collection of
     * monthly budgets
     *
     * @return a collection of monthly budgets
     * @throws SQLException
     */
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

    /**
     * This method takes in a collection of monthly budgets and uses it to
     * populate a pie chart
     *
     * @param monthlyBudgets To set the arraylist of monthly budgets
     */
    private void populateSummaryPieChart(ArrayList monthlyBudgets) {

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
        ObservableList<PieChart.Data> summaryPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Mortgage", mortgage),
                new PieChart.Data("Electricity", electricity),
                new PieChart.Data("Gas", gas),
                new PieChart.Data("Water", water),
                new PieChart.Data("Internet", internet),
                new PieChart.Data("Car", car),
                new PieChart.Data("Insurance", insurance),
                new PieChart.Data("Fuel", fuel),
                new PieChart.Data("Food", food),
                new PieChart.Data("Other", other));

        summaryPieChart.setData(summaryPieChartData);
    }

    /**
     * This method configures the bar chart and calls the getSummaryBarChartData
     * method to populate the bar chart
     *
     * @param monthlyBudgets To set the arraylist of monthly budgets
     */
    private void populateSummaryBarChart(ArrayList monthlyBudgets) {
        monthlySpendingTotals = new XYChart.Series<>();

        month.setLabel("Months");
        spendingTotal.setLabel("$ Spent");

        monthlySpendingTotals.setName("Monthly Totals");

        getSummaryBarChartData(monthlyBudgets);

        summaryBarChart.getData().addAll(monthlySpendingTotals);
    }

    /**
     * This method takes in a collection of monthly budgets and uses it to get
     * the total spending amount for each month. Then it puts the results in a
     * bar chart
     *
     * @param monthlyBudgets To set the arraylist of monthly budgets
     */
    private void getSummaryBarChartData(ArrayList monthlyBudgets) {

        LinkedList<Integer> monthlyTotals = new LinkedList<>();
        ArrayList<Integer> month = new ArrayList<>();

        for (Object monthlyBudget : monthlyBudgets) {
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

    /**
     * This method takes in a collection of monthly budgets and calculates the
     * amount spent for each category. Then it puts the results in labels.
     *
     * @param monthlyBudgets To set the arraylist of monthly budgets
     */
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
     * When the edit button is pushed, this method gets the user id string from
     * a label and converts it to an int. Then it passes it as an argument in
     * the sceneChangeEdit method and executes it
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void editButtonPressed(ActionEvent event) throws IOException, SQLException {
        String userIdString = userIdLabel.getText();
        int loggedInUserId = Integer.parseInt(userIdString);

        sceneChangeEdit(event, loggedInUserId);
    }

    /**
     * This method changes scenes to the Edit view using the logged in user id
     * as an argument
     *
     * @param event
     * @throws IOException
     */
    public void sceneChangeEdit(ActionEvent event, int loggedInUserId) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenesUserId(event, "FXMLEditView.fxml", "Edit", loggedInUserId);
    }

    /**
     * This method logs the user out and takes them to the log in scene is
     * pressed
     *
     * @param event
     * @throws IOException
     */
    public void logoutButton(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "FXMLLoginView.fxml", "Login");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
