package UI.Vistas.Controllers.helpers;

import UI.Vistas.Controllers.DatosOponenteCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Daniel Rodriguez
 */
public class OpenStage {

    /**
     * Se abre el stage seleccionado en file
     * @param title
     * @param file
     * @throws IOException
     */
    public void openStage(String title, String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);



        stage.show();
    }

    /**
     * Se abre el stage de datosOponente pasando por parametro información
     * @param nextRoute
     * @param titleNextRoute
     * @param backRoute
     * @param titleBackRoute
     * @throws IOException
     */
    public void openStageDatosOponente(String nextRoute, String titleNextRoute, String backRoute, String titleBackRoute) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DatosOponente.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        DatosOponenteCtrl ctrl = fxmlLoader.<DatosOponenteCtrl>getController();

        ctrl.setRouteBackFile(backRoute);
        ctrl.setTitleGoBack(titleBackRoute);

        ctrl.setRouteNextScene(nextRoute);
        ctrl.setTitleNextScene(titleNextRoute);

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Datos Oponente Personalizada");
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);


        stage.show();
    }
}
