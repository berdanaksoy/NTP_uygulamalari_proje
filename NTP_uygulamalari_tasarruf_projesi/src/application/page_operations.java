package application;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class page_operations {
	public static void upload_images_2button(javafx.scene.control.Button kapat, javafx.scene.control.Button geri_don) {
		Image img = new Image(Main.class.getResourceAsStream("/gorseller/close_button.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(40);
        imgView.setFitWidth(40);
        kapat.setGraphic(imgView);
        
        Image img2 = new Image(Main.class.getResourceAsStream("/gorseller/return_button.png"));
        ImageView imgView2 = new ImageView(img2);
        imgView2.setFitHeight(30);
        imgView2.setFitWidth(30);
        geri_don.setGraphic(imgView2);
	}
	
	public static void upload_image_1button(javafx.scene.control.Button kapat) {
		Image img = new Image(Main.class.getResourceAsStream("/gorseller/close_button.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(40);
        imgView.setFitWidth(40);
        kapat.setGraphic(imgView);
	}
	
	public static void page_switch(String acilacak_fxml, Event event) throws IOException {
		try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) FXMLLoader.load(Main.class.getResource(acilacak_fxml));
            Scene scene = new Scene(pane, 800, 600);
            stage.initStyle(StageStyle.UNDECORATED);
			stage.centerOnScreen();
            scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
            stage.setScene(scene);

            stage.show();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void text_format_sayi(javafx.scene.control.TextField textField_yazi) {
	    TextFormatter<String> formatter = new TextFormatter<>(Change -> {
	        String newText = Change.getControlNewText();
	        if (newText.matches("\\d*")) {
	            return Change;
	        } else {
	            return null;
	        }
	    });
	    textField_yazi.setTextFormatter(formatter);
	}
	
	public static void text_format_yazi(javafx.scene.control.TextField textField_sayi) {
		TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("[a-zA-Z\\s]*")) {
                return change;
            }
            return null;
        });
        textField_sayi.setTextFormatter(formatter);
	}
}
