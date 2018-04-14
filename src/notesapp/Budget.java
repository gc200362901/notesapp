package notesapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Sean Noddin
 */
public class Budget {

    private int userId, monthId, mortgage, electricity, gas, water, internet, car, insurance,
            fuel, food, other;
    private String monthDescription;
    
    public Budget(int userId, int monthId, String monthDescription, int mortgage, int electricity,
            int gas, int water, int internet, int car, int insurance) {
        validateUserId(userId);
        validateMonthId(monthId);
        validateMortgage(mortgage);
        validateElectricity(electricity);
        validateGas(gas);
        validateWater(water);
        validateInternet(internet);
        validateCar(car);
        validateInsurance(insurance);
        validateFuel(fuel);
        validateFood(food);
        validateOther(other);
        validateMonthDescription(monthDescription);
    }

    public int getUserId() {
        return userId;
    }

    private void validateUserId(int userId) {
        if (userId == (int)userId) {
            this.userId = userId;
        }
        else {
            throw new IllegalArgumentException("Not a valid user ID");
        }
    }
    
    public void setUserId(int userId) {
        validateUserId(userId);
    }

    /**
     * This method gets the month number
     *
     * @return the month number
     */
    public int getMonthId() {
        return monthId;
    }

    /**
     * This method validates the month number is an integer and is greater than
     * 0 and less than 13
     *
     * @param monthId To set the monthId
     */
    private void validateMonthId(int monthId) {
        if (monthId == (int) monthId && monthId > 0 && monthId < 13) {
            this.monthId = monthId;
        } else {
            throw new IllegalArgumentException("Not a valid month number");
        }
    }

    /**
     * This method sets the month number after it has been validated
     *
     * @param monthId To set the monthId
     */
    public void setMonthId(int monthId) {
        validateMonthId(monthId);
    }

    /**
     * This method returns the mortgage amount
     *
     * @return the mortgage
     */
    public int getMortgage() {
        return mortgage;
    }

    /**
     * This method validates the mortgage is an integer and is equal or greater
     * than 0
     *
     * @param mortgage To set the mortgage
     */
    private void validateMortgage(int mortgage) {
        if (mortgage == (int) mortgage && mortgage >= 0) {
            this.mortgage = mortgage;
        } else {
            throw new IllegalArgumentException("Not a valid mortgage amount");
        }
    }

    /**
     * This method sets the mortgage after it has been validated
     *
     * @param mortgage To set the mortgage
     */
    public void setMortgage(int mortgage) {
        validateMortgage(mortgage);
    }

    /**
     * This method returns the electricity amount
     *
     * @return
     */
    public int getElectricity() {
        return electricity;
    }

    /**
     * This method validates the electricity amount is an integer and is equal
     * or greater than 0
     *
     * @param elecricity To set the electricity
     */
    private void validateElectricity(int electricity) {
        if (electricity == (int) electricity && electricity >= 0) {
            this.electricity = electricity;
        } else {
            throw new IllegalArgumentException("Not a valid electricity amount");
        }
    }

    /**
     * This sets the electricity amount after if has been validated
     *
     * @param electricty To set the electricity
     */
    public void setElectricity(int electricty) {
        validateElectricity(electricity);
    }

    /**
     * This method returns the gas amount
     *
     * @return the gas amount
     */
    public int getGas() {
        return gas;
    }

    /**
     * This method validates the gas amount is an integer and greater than or
     * equal to 0
     *
     * @param gas To set the gas amount
     */
    private void validateGas(int gas) {
        if (gas == (int) gas && gas >= 0) {
            this.gas = gas;
        } else {
            throw new IllegalArgumentException("Not a valid gas amount");
        }
    }

    /**
     * This method sets the gas amount after it has been validated
     *
     * @param gas To set the gas amount
     */
    public void setGas(int gas) {
        validateGas(gas);
    }

    /**
     * This method gets the water amount
     *
     * @return the water amount
     */
    public int getWater() {
        return water;
    }

    /**
     * This method validates the water amount is an integer and greater than or
     * equal to 0
     *
     * @param water To set the water amount
     */
    private void validateWater(int water) {
        if (water == (int) water && water >= 0) {
            this.water = water;
        } else {
            throw new IllegalArgumentException("Not a valid water amount");
        }
    }

    /**
     * This method sets the water amount after it has been validated
     *
     * @param water To set the water amount
     */
    public void setWater(int water) {
        validateWater(water);
    }

    /**
     * This method returns the internet amount
     *
     * @return the internet amount
     */
    public int getInternet() {
        return internet;
    }

    /**
     * This metod validates the internet amount is an integer and greater than
     * or equal to 0
     *
     * @param internet To set the internet amount
     */
    private void validateInternet(int internet) {
        if (internet == (int) internet && internet >= 0) {
            this.internet = internet;
        } else {
            throw new IllegalArgumentException("Not a valid internet amount");
        }
    }

    /**
     * This method sets the internet amount after is has validated
     *
     * @param internet To set the internet amount
     */
    public void setInternet(int internet) {
        validateInternet(internet);
    }

    /**
     * This method returns the car amount
     *
     * @return the car amount
     */
    public int getCar() {
        return car;
    }

    /**
     * This method validates the car amount is an integer and is greater than or
     * equal to 0
     *
     * @param car To set the car amount
     */
    private void validateCar(int car) {
        if (car == (int) car && car >= 0) {
            this.car = car;
        } else {
            throw new IllegalArgumentException("Not a valid car amount");
        }
    }

    /**
     * This method sets the car amount after is has been validated
     *
     * @param car To set the car amount
     */
    public void setCar(int car) {
        validateCar(car);
    }

    /**
     * This method returns the insurance amount
     *
     * @return the insurance amount
     */
    public int getInsurance() {
        return insurance;
    }

    /**
     * This method validates the insurance amount is an integer and is greater
     * than or equal to 0
     *
     * @param insurance To set the inerance amount
     */
    private void validateInsurance(int insurance) {
        if (insurance == (int) insurance && insurance >= 0) {
            this.insurance = insurance;
        } else {
            throw new IllegalArgumentException("Not a valid insurance amount");
        }
    }

    /**
     * This method sets the insurance amount after it has been validated
     *
     * @param insurance To set the insurance amount
     */
    public void setInsurance(int insurance) {
        validateInsurance(insurance);
    }

    /**
     * This method returns the fuel amount
     *
     * @return the fuel amount
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * This method validtes the fuel amount is an integer and greater than or
     * equal to 0
     *
     * @param fuel To set the fuel amount
     */
    private void validateFuel(int fuel) {
        if (fuel == (int) fuel && fuel >= 0) {
            this.fuel = fuel;
        } else {
            throw new IllegalArgumentException("Not a valid fuel amount");
        }
    }

    /**
     * This method sets the fuel amount after it has been validated
     *
     * @param fuel To set the fuel amount
     */
    public void setFuel(int fuel) {
        validateFuel(fuel);
    }

    /**
     * This method returns the food amount
     *
     * @return the food amount
     */
    public int getFood() {
        return food;
    }

    /**
     * This method validates the food amount is an integer and is greater than
     * or equal to 0
     *
     * @param food
     */
    private void validateFood(int food) {
        if (food == (int) food && food >= 0) {
            this.food = food;
        } else {
            throw new IllegalArgumentException("Not a valid food amount");
        }
    }

    /**
     * This method sets the food amount after it has been validated
     *
     * @param food To set the food amount
     */
    public void setFood(int food) {
        validateFood(food);
    }

    /**
     * This method returns the other amount
     *
     * @return the other amount
     */
    public int getOther() {
        return other;
    }

    /**
     * This method validates the other amount is an integer and is greater than
     * or equal to 0
     *
     * @param other To set the other amount
     */
    private void validateOther(int other) {
        if (other == (int) other && other >= 0) {
            this.other = other;
        } else {
            throw new IllegalArgumentException("Not a valid other amount");
        }
    }

    /**
     * This method sets the other amount after it has been validated
     *
     * @param other To set the other amount
     */
    public void setOther(int other) {
        validateOther(other);
    }

    /**
     * This method returns the description of the month
     *
     * @return the description of the month
     */
    public String getMonthDescription() {
        return monthDescription;
    }

    /**
     * This method validates the month string is greater then 0
     *
     * @param monthDescription To set the month description
     */
    private void validateMonthDescription(String monthDescription) {
        if (!monthDescription.isEmpty()) {
            this.monthDescription = monthDescription;
        } else {
            throw new IllegalArgumentException("Not a valid month name");
        }
    }

    /**
     * This method sets the month description
     *
     * @param monthDescription To set the month description
     */
    public void setMonthDescription(String monthDescription) {
        validateMonthDescription(monthDescription);
    }

    public static void createNewBudget(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");
            int monthIdCount=1;
            for (String month : months) {
                
                String sql = "INSERT INTO tb_finance (userId, monthId, monthDescription) "
                        + "VALUES (?,?, ?)";

                preparedStatement = conn.prepareStatement(sql);
           
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, monthIdCount);
                preparedStatement.setString(3, month);

                preparedStatement.executeUpdate();
                
                monthIdCount++;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
