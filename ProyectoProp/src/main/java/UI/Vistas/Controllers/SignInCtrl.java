package UI.Vistas.Controllers;

import Domain.Controllers.AuthController;
import Domain.Controllers.IAuthController;
import UI.Vistas.Controllers.helpers.ValidateUserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class SignInCtrl {

    private static IAuthController ac = new AuthController();



    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputPassword;

    @FXML
    private Button btnConfirmRegister;

    @FXML
    private Button btnGoBack;

    @FXML
    private Label registerError;

    @FXML private javafx.scene.control.Button closeButton;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void closeWindow(ActionEvent event){
        closeButtonAction();
    }

    @FXML
    void tryToRegister(ActionEvent event) throws IOException {

        String email = inputEmail.getText();
        String password = inputPassword.getText();

        boolean redirectToMain = registerUser(email, password, ac);

        if(redirectToMain)
            closeButtonAction();
        else
            registerError.setVisible(true);
    }

    public boolean registerUser(String email, String password, IAuthController ac){
        ValidateUserData val = new ValidateUserData();

        if(!val.validateUserData(email,password)){
            return false;
        }

        boolean registered = ac.registerNewUser(email, password);
        if(!registered){
            return false;
        }
        return true;
    }





}
