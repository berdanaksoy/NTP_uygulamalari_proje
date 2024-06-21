module NTP_uygulamalari_tasarruf_projesi {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.swing;
	requires mysql.connector.j;
	requires java.management;
	requires java.compiler;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
