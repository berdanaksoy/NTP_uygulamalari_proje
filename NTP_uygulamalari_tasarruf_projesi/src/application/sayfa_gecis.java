package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class sayfa_gecis {
	public sayfa_gecis(String acilacak_fxml, Event event) throws IOException {
		try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource(acilacak_fxml));
            Scene scene = new Scene(pane, 800, 600);
            stage.initStyle(StageStyle.UNDECORATED);
			stage.centerOnScreen();
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);

            stage.show();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
