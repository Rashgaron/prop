package UI.Vistas;

import UI.Vistas.Controllers.FactoryCtrl;
import UI.Vistas.Controllers.MainCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Daniel Rodriguez
 */
public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);

        FactoryCtrl factoryCtrl = FactoryCtrl.Instance();
        MainCtrl main = factoryCtrl.getMainCtrl();
        main.setStage(stage);

        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}