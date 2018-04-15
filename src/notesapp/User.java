package notesapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sean Noddin
 */
public class User {

    private String username;
    private String hashPw;
    private byte[] salt;

    public User(String username, String hashPw, byte[] salt) {
        setUsername(username);
        setHashPw(hashPw);
        setSalt(salt);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPw() {
        return hashPw;
    }

    public void setHashPw(String hashPw) {
        this.hashPw = hashPw;
    }

    public byte[] getSalt() {
        return salt;
    }

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
