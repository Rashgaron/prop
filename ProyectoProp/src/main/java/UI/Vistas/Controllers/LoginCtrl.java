package UI.Vistas.Controllers;

import Domain.Controllers.AuthController;
import UI.Vistas.Controllers.helpers.OpenStage;
import UI.Vistas.Controllers.helpers.ValidateUserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class LoginCtrl {

    private static AuthController ac = new AuthController();

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Button btnConfirmRegister;

    @FXML
    private Button btnGoBack;

    @FXML
    private Label registerError;


    @FXML
    private Button closeButton;

    @FXML
    private Label loginError;

    @FXML
    private Label sesionIniciada;

    @FXML
    void closeWindow(ActionEvent event) {
        closeButtonAction();
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void tryToLogin(ActionEvent event) throws IOException {

        String email = inputEmail.getText();
        String password = inputPassword.getText();
        ValidateUserData val = new ValidateUserData();

        if(!val.validateUserData(email, password))
            loginError.setVisible(true);
        else
            loginError.setVisible(false);


        boolean logged = ac.loginUser(email, password);

        if(logged) {

            OpenStage openStage = new OpenStage();
            openStage.openStage("Home Menu", "/HomeMenu.fxml");
            closeButtonAction();
            sesionIniciada.setVisible(true);
        }else{
            sesionIniciada.setVisible(false);
            loginError.setVisible(true);
        }
    }

}
