package notesapp;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sean Noddin
 */
public class SceneChanger {

    /**
     * This method is used to change scenes with no data passed between
     *
     * @param event
     * @param viewName
     * @param title
     * @throws IOException
     */
    public void changeScenes(ActionEvent event, String viewName, String title) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method is used to change scenes to the main view while passing the
     * logged in username String
     *
     * @param event
     * @param viewName
     * @param title
     * @param loggedInUsername
     * @throws IOException
     */
    public void changeScenesWithUsername(ActionEvent event, String viewName, String title, String loggedInUsername) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        FXMLMainViewController controller = loader.getController();
        controller.getLoggedInUsername(loggedInUsername);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method is used to change to the Edit scene while passing the logged
     * in user Id int
     *
     * @param event
     * @param viewName
     * @param title
     * @param loggedInUserId
     * @throws IOException
     */
    public void changeScenesUserId(ActionEvent event, String viewName, String title, int loggedInUserId) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        FXMLEditViewController controller = loader.getController();
        controller.getLoggedInUserId(loggedInUserId);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method is used to change to the summary scene while passing the 
     * logged in user id int
     * @param event
     * @param viewName
     * @param title
     * @param loggedInUserId
     * @throws IOException
     * @throws SQLException 
     */
    public void changeScenesUserIdToSummary(ActionEvent event, String viewName, String title, int loggedInUserId) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        FXMLSummaryViewController controller = loader.getController();
        controller.getLoggedInUserId(loggedInUserId);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }
}
