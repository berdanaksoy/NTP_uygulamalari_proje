package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class upload_gorseller {
	
	public upload_gorseller(javafx.scene.control.Button kapat, javafx.scene.control.Button geri_don) {
		Image img = new Image(getClass().getResourceAsStream("/gorseller/close_button.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(40);
        imgView.setFitWidth(40);
        kapat.setGraphic(imgView);
        
        Image img2 = new Image(getClass().getResourceAsStream("/gorseller/return_button.png"));
        ImageView imgView2 = new ImageView(img2);
        imgView2.setFitHeight(30);
        imgView2.setFitWidth(30);
        geri_don.setGraphic(imgView2);
	}
	
	public upload_gorseller(javafx.scene.control.Button kapat) {
		Image img = new Image(getClass().getResourceAsStream("/gorseller/close_button.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(40);
        imgView.setFitWidth(40);
        kapat.setGraphic(imgView);
	}
}
