package controllers;

import application.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.scene.control.PasswordField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.projeMySql.util.VeritabaniUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class uye_girisi_controller {
	
	public uye_girisi_controller() {
		baglanti=VeritabaniUtil.Baglan();
	}
	
	@FXML
    private AnchorPane scene_uye_girisi;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_giris_yap;

    @FXML
    private Button btn_kapat;

    @FXML
    private Hyperlink link_uye_ol;

    @FXML
    private Hyperlink link_yonetici;

    @FXML
    private Hyperlink link_şifre;

    @FXML
    private PasswordField txt_sifre;

    @FXML
    private TextField txt_tc;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    public static String islem_ne;
	public static ObservableList<uye_kayitlari> uye_kayitlar = FXCollections.observableArrayList();

    @FXML
    void btn_giris_yap_click(ActionEvent event) {
    	uye_kayitlar.clear();
    	sql="select * from uyeler where tc=? and sifre=?";
    	try {
			sorguIfadesi=baglanti.prepareStatement(sql);
			sorguIfadesi.setString(1, txt_tc.getText());
			sorguIfadesi.setString(2, VeritabaniUtil.MD5Sifrele(txt_sifre.getText()));
			
			ResultSet getirilen = sorguIfadesi.executeQuery();
			while (getirilen.next()) {
				uye_kayitlar.add(new uye_kayitlari(getirilen.getInt("id"), getirilen.getString("tc"), getirilen.getString("ad_soyad"),
						getirilen.getDate("uyelik_tarihi"), getirilen.getBlob("kimlik_foto1"), getirilen.getBlob("kimlik_foto2"),
						getirilen.getString("sifre"), getirilen.getInt("iller_Id"), getirilen.getInt("ilceler_Id"), 
						getirilen.getInt("hatali_fatura_giris_sayisi"), getirilen.getString("onay")));
			}
			
			if (uye_kayitlar.isEmpty()) {
				JOptionPane.showMessageDialog(new JFrame(), "Kullanıcı adı veya şifre hatalı.", "Uyarı", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (uye_kayitlar.get(0).getOnay().equals("engellenmis")) {
				JOptionPane.showMessageDialog(new JFrame(), "Üyeliğiniz engellenmiştir.", "Uyarı", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if (uye_kayitlar.get(0).getOnay().equals("onaylanmis")) {
				page_operations.page_switch("uye_ekrani.fxml", event);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Üyeliğiniz henüz onaylanmamış", "Uyarı", JOptionPane.WARNING_MESSAGE);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
					"Hata", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    @FXML
    void btn_kapat_click(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void link_uye_ol_click(ActionEvent event) throws IOException {
    	islem_ne="üye olma";
    	try {
    		uye_islem_sayfasina_git(event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
					"Hata", JOptionPane.ERROR_MESSAGE);
		}
    }

    @FXML
    void link_yonetici_click(ActionEvent event) throws IOException {
    	page_operations.page_switch("yonetici_giris.fxml", event);
    }

    @FXML
    void link_şifre_click(ActionEvent event) {
		islem_ne="şifre değiştirme";
    	try {
    		uye_islem_sayfasina_git(event);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
					"Hata", JOptionPane.ERROR_MESSAGE);
		}
    }
   
    @FXML
    void initialize() {
    	page_operations.upload_image_1button(btn_kapat);
    	page_operations.text_format_sayi(txt_tc);
    }

    void uye_islem_sayfasina_git(ActionEvent event) throws IOException {
    	page_operations.page_switch("uye_islem.fxml", event);
    }
}
