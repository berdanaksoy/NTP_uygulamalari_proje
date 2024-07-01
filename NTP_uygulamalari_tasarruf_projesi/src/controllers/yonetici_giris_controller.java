package controllers;

import application.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.PasswordField;
import com.projeMySql.util.VeritabaniUtil;

public class yonetici_giris_controller {
	
	public yonetici_giris_controller() {
		baglanti=VeritabaniUtil.Baglan();
	}
	
	@FXML
    private AnchorPane scene_yonetici_giris;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_geri_don;

    @FXML
    private Button btn_giris_yap;

    @FXML
    private Button btn_kapat;

    @FXML
    private PasswordField txt_sifre;

    @FXML
    private TextField txt_yonetici_ad;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
	public static ObservableList<yonetici_kayitlar> yonetici_kayitlar=FXCollections.observableArrayList();


    @FXML
    void btn_geri_don_click(ActionEvent event) {
    	try {
    		page_operations.page_switch("uye_girisi.fxml", event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
					"Hata", JOptionPane.ERROR_MESSAGE);
		}
    }

    @FXML
    void btn_giris_yap_click(ActionEvent event) {
    	yonetici_kayitlar.clear();
    	sql="select * from yoneticiler where ad=? and sifre=?";
    	try {
    	    sorguIfadesi=baglanti.prepareStatement(sql);
    	    sorguIfadesi.setString(1, txt_yonetici_ad.getText());
    	    sorguIfadesi.setString(2, txt_sifre.getText());

    	    ResultSet getirilen = sorguIfadesi.executeQuery();
    	    if (getirilen.next()) {
    	        yonetici_kayitlar.add(new yonetici_kayitlar(getirilen.getInt("id"), getirilen.getString("ad"),
    	                getirilen.getString("sifre"), getirilen.getInt("fatura_tipleri_id")));
    	        
    	        page_operations.page_switch("yonetici_ekrani.fxml", event);
    	    } else {
    	        JOptionPane.showMessageDialog(new JFrame(), "Yönetici adı veya şifre hatalı.", 
    	            "Uyarı", JOptionPane.WARNING_MESSAGE);
    	    }
    	} catch (Exception e) {
    	    System.out.println(e.getMessage().toString());
    	    JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra "
    	        + "tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
    	}

    }

    @FXML
    void btn_kapat_click(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void initialize() {
    	page_operations.upload_images_2button(btn_kapat, btn_geri_don);
    }

}
