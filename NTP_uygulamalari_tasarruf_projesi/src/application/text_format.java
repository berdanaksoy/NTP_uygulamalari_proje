package application;

import java.util.function.Function;
import javafx.scene.control.TextFormatter;

public class text_format {
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
