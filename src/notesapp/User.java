package notesapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

/**
 *
 * @author Sean Noddin
 */
public class User {

    private String username;
    private String hashPw;
    private byte[] salt;

    /**
     * This constructer takes in a username, hashed password and salt to build 
     * a User object
     * @param username To set the username
     * @param hashPw To set the hashed password
     * @param salt To set the salt
     */
    public User(String username, String hashPw, byte[] salt) {
        setUsername(username);
        setHashPw(hashPw);
        setSalt(salt);
    }

    /**
     * This method returns the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets the username
     * @param username To set the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns the hashed password
     * @return the hashed password
     */
    public String getHashPw() {
        return hashPw;
    }

    /**
     * This method sets the hashed password
     * @param hashPw To set the hashed password
     */
    public void setHashPw(String hashPw) {
        this.hashPw = hashPw;
    }

    /**
     * This method returns the salt
     * @return the salt
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * This method sets the salt
     * @param salt To set the salt
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * This method connects to the database, attempts to input data into the
     * database and then closes the connection
     *
     * @throws SQLException If the connection to the database or the data insert
     * can not be made
     */
    public void commitToDatabase() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");

            String sql = "INSERT INTO tb_user (username, hashPw, salt) "
                    + "VALUES (?,?,?)";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashPw);
            preparedStatement.setBlob(3, new javax.sql.rowset.serial.SerialBlob(salt));

            preparedStatement.executeUpdate();

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
    
     /**
     * This method queries the database and stores all the usernames in a
     * TreeSet for efficient searching
     *
     * @param rs
     * @param statement
     * @return a TreeSet of usernames
     * @throws SQLException
     */
    public static TreeSet collectUsernames(ResultSet rs, Statement statement) throws SQLException {
        TreeSet<String> usernames = new TreeSet<>();

        rs = statement.executeQuery("SELECT username FROM tb_user");

        while (rs.next()) {
            usernames.add(rs.getString("username"));
        }
        return usernames;
    }

    /**
     * This method uses the username to query the database and return the 
     * associated user id
     * @return the user Id
     * @throws SQLException 
     */
    public int returnDbUserId() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        int dbUserId = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");

            String sql = "SELECT userId FROM tb_user WHERE username = ?";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, username);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                dbUserId = rs.getInt("userId");
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
        return dbUserId;
    }

    public static int returnDbUserId(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        int dbUserId = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/gc200362901", "gc200362901", "y2RKsUKFUX");

            String sql = "SELECT userId FROM tb_user WHERE username = ?";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, username);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                dbUserId = rs.getInt("userId");
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
        return dbUserId;
    }

}
