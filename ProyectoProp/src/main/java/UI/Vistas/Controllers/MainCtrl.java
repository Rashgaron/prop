

package UI.Vistas.Controllers;

import UI.Vistas.Controllers.helpers.OpenStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class MainCtrl {


    public Stage myStage;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignIn;

    @FXML
    void goToSignIn(ActionEvent event) throws IOException {

        OpenStage openStage = new OpenStage();
        openStage.openStage("Register new user", "/SignIn.fxml");

    }

    @FXML
    void goToLogin(ActionEvent event) throws IOException {

       OpenStage openStage = new OpenStage();
       openStage.openStage("Login","/Login.fxml");
    }


    public void setStage(Stage s) {myStage = s;}

}
