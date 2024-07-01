package controllers;

import application.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.projeMySql.util.VeritabaniUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class yonetici_islem_controller {
	
	public yonetici_islem_controller() {
		baglanti=VeritabaniUtil.Baglan();
	}
	
	@FXML
    private AnchorPane scene_yonetici_islem;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_geri_don;

    @FXML
    private Button btn_kapat;

    @FXML
    private Button btn_sifre_degistir;

    @FXML
    private PasswordField txt_sifre;

    @FXML
    private PasswordField txt_yeni_sifre;

    @FXML
    private PasswordField txt_yeni_sifre2;

    @FXML
    private TextField txt_yonetici_ad;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    public static yonetici_kayitlar giris_yapilan_id;

    @FXML
    void btn_geri_don_click(ActionEvent event) {
    	try {
    		page_operations.page_switch("yonetici_ekrani.fxml", event);
    		} catch (Exception e) {
			System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
		}
    }

    @FXML
    void btn_kapat_click(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void btn_sifre_degistir_click(ActionEvent event) {
    	ObservableList<yonetici_kayitlar> yonetici_kayitlar = FXCollections.observableArrayList();
    	sql = "select * from yoneticiler where id=?";
    	try {
    	    sorguIfadesi = baglanti.prepareStatement(sql);
    	    sorguIfadesi.setInt(1, giris_yapilan_id.getId());

    	    ResultSet getirilen = sorguIfadesi.executeQuery();
    	    if (!getirilen.isBeforeFirst()) {
    	        JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
    	        		"Hata", JOptionPane.ERROR_MESSAGE);
    	    } else {
    	        while (getirilen.next()) {
    	            yonetici_kayitlar.add(new yonetici_kayitlar(getirilen.getInt("id"), getirilen.getString("ad"),
    	                    getirilen.getString("sifre"), getirilen.getInt("fatura_tipleri_id")));
    	        }
    	        String giris_yapan_ad = yonetici_kayitlar.get(0).getAd();
    	        String giris_yapan_sifre = yonetici_kayitlar.get(0).getSifre();

    	        if (txt_yonetici_ad.getText().equals(giris_yapan_ad) && txt_sifre.getText().equals(giris_yapan_sifre) && 
    	        		txt_yeni_sifre.getText().equals(txt_yeni_sifre2.getText())) {
    	            sql = "update yoneticiler set sifre=? where id=?";
    	            sorguIfadesi = baglanti.prepareStatement(sql);
    	            sorguIfadesi.setString(1, VeritabaniUtil.MD5Sifrele(txt_yeni_sifre.getText()));
    	            sorguIfadesi.setInt(2, giris_yapilan_id.getId());

    	            sorguIfadesi.executeUpdate();

    	            page_operations.page_switch("yonetici_ekrani.fxml", event);
    	            JOptionPane.showMessageDialog(new JFrame(), "İşleminiz başarıyla tamamlanmıştır.", "Bilgilendirme",
    	                    JOptionPane.INFORMATION_MESSAGE);
    	        } else {
    	            JOptionPane.showMessageDialog(new JFrame(),
    	                    "Yönetici adı veya eski şifre yanlış girildi. Güvenliğiniz için çıkış yapılıyor.", "Uyarı",
    	                    JOptionPane.WARNING_MESSAGE);
    	            page_operations.page_switch("uye_girisi.fxml", event);
    	        }
    	    }
    	} catch (Exception e) {
    	    System.out.println(e.getMessage().toString());
            JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
    	}
    }

    @FXML
    void initialize() {
    	page_operations.upload_images_2button(btn_kapat, btn_geri_don);
    	
    	giris_yapilan_id=yonetici_giris_controller.yonetici_kayitlar.get(0);
    }

}
