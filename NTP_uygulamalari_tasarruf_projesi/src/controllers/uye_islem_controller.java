package controllers;

import application.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.projeMySql.util.VeritabaniUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class uye_islem_controller {
	
	public uye_islem_controller() {
		baglanti=VeritabaniUtil.Baglan();
	}
	
	@FXML
    private AnchorPane scene_uye_islem;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_geri_don;

    @FXML
    private Button btn_islem_tamamla;

    @FXML
    private Button btn_kapat;

    @FXML
    private Button btn_kimlik_arka;

    @FXML
    private Button btn_kimlik_on;

    @FXML
    private ComboBox<String> combo_il;

    @FXML
    private ComboBox<String> combo_ilce;

    @FXML
    private PasswordField txt_sifre;

    @FXML
    private PasswordField txt_sifre2;

    @FXML
    private ImageView img_kimlik_arka;

    @FXML
    private ImageView img_kimlik_on;

    @FXML
    private TextField txt_ad_soyad;

    @FXML
    private TextField txt_tc;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql, islem_ne, onay;

    @FXML
    void btn_geri_don_click(ActionEvent event) {
    	try {
    		girise_don(event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
					"Hata", JOptionPane.ERROR_MESSAGE);
		}
    }

    @FXML
    void btn_islem_tamamla_click(ActionEvent event) {
    	ObservableList<uye_kayitlari> uye_kayitlari = FXCollections.observableArrayList();
    	String sql = "select * from uyeler where tc=?";
    	        
    	if (txt_tc.getText().length() == 11 && !txt_ad_soyad.getText().isEmpty() && !txt_sifre.getText().isEmpty() && 
    	    txt_sifre.getText().equals(txt_sifre2.getText()) && combo_il.getSelectionModel().getSelectedItem() != null && 
    	    combo_ilce.getSelectionModel().getSelectedItem() != null && img_kimlik_on.getImage() != null && img_kimlik_arka.getImage() != null) {
    	    try {
    	        sorguIfadesi = baglanti.prepareStatement(sql);
    	        sorguIfadesi.setString(1, txt_tc.getText());
    	        
    	        ResultSet getirilen = sorguIfadesi.executeQuery();
    	        while (getirilen.next()) {
    	            uye_kayitlari.add(new uye_kayitlari(getirilen.getInt("id"), getirilen.getString("tc"), getirilen.getString("ad_soyad"),
    	                    getirilen.getDate("uyelik_tarihi"), getirilen.getBlob("kimlik_foto1"), getirilen.getBlob("kimlik_foto2"),
    	                    getirilen.getString("sifre"), getirilen.getInt("ilceler_id"), getirilen.getInt("iller_id"), 
    	                    getirilen.getInt("hatali_fatura_giris_sayisi"), getirilen.getString("onay")));
    	        }

    	        if ("şifre değiştirme".equals(islem_ne)) {
    	            if (!uye_kayitlari.isEmpty()) {
    	                uye_kayitlari uye = uye_kayitlari.get(0);
    	                String onay = uye.getOnay();
    	                String ad_soyad=uye.getAd_soyad();
    	                
    	                if (uye_kayitlari.get(0).getOnay().equals("engellenmis")) {
    	                	JOptionPane.showMessageDialog(new JFrame(), "Üyeliğiniz engellenmiştir.", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	    				return;
    	                } else if ("onaylanmis".equals(onay) && txt_ad_soyad.getText().equals(ad_soyad)) {
    	                    // Şifre değiştirme işlemi
    	                    sql = "UPDATE uyeler SET sifre = ?, onay = 'onaylanmamis', kimlik_foto1=?, kimlik_foto2=?, ilceler_id=(select id from ilceler where ad=?) , iller_id=(select id from iller where ad=?) WHERE tc = ?";
    	                    sorguIfadesi = baglanti.prepareStatement(sql);
    	                    sorguIfadesi.setString(1, VeritabaniUtil.MD5Sifrele(txt_sifre.getText()));
    	                    sorguIfadesi.setBytes(2, imageToBytes(img_kimlik_on.getImage()));
    	                    sorguIfadesi.setBytes(3, imageToBytes(img_kimlik_arka.getImage()));
    	                    sorguIfadesi.setString(4, combo_ilce.getSelectionModel().getSelectedItem().toString());
    	                    sorguIfadesi.setString(5, combo_il.getSelectionModel().getSelectedItem().toString());
    	                    sorguIfadesi.setString(6, txt_tc.getText());
    	                    sorguIfadesi.executeUpdate();

    	                    girise_don(event);
    	                    JOptionPane.showMessageDialog(new JFrame(), "Şifre değiştirme talebiniz alınmıştır. Talebiniz onaylandıktan sonra giriş yapabilirsiniz.", "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
    	                    return;
    	                } else if("onaylanmamis".equals(onay)){
    	                    girise_don(event);
    	                    JOptionPane.showMessageDialog(new JFrame(), "Şifre değiştirme talebinizin alınması için önce hesabınızın onaylanması gerekir.", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	                    return;
    	                }
    	                else {
    	                    JOptionPane.showMessageDialog(new JFrame(), "Bilgilerinizi doğru girdiğinizden emin olunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	                    return;
						}
    	            } else {
    	                JOptionPane.showMessageDialog(new JFrame(), "Girdiğiniz TC numarası kayıtlı değil.", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	                return;
    	            }
    	        } else if ("üye olma".equals(islem_ne)) {
    	            if (uye_kayitlari.isEmpty()) {
    	                // Üye olma işlemi
    	                DatePicker datePicker = new DatePicker(LocalDate.now());
    	                LocalDate localDate = datePicker.getValue();
    	                sql = "INSERT INTO uyeler (id, tc, ad_soyad, uyelik_tarihi, kimlik_foto1, kimlik_foto2, sifre, ilceler_id, iller_id, hatali_fatura_giris_sayisi) VALUES (NULL, ?, ?, ?, ?, ?, ?, (select id from ilceler where ad=?), (select id from iller where ad=?), 0)";
    	                sorguIfadesi = baglanti.prepareStatement(sql);
    	                sorguIfadesi.setString(1, txt_tc.getText().toString());
    	                sorguIfadesi.setString(2, txt_ad_soyad.getText().toString());
    	                sorguIfadesi.setString(3, localDate.toString());
    	                sorguIfadesi.setBytes(4, imageToBytes(img_kimlik_on.getImage()));
    	                sorguIfadesi.setBytes(5, imageToBytes(img_kimlik_arka.getImage()));
    	                sorguIfadesi.setString(6, VeritabaniUtil.MD5Sifrele(txt_sifre.getText()));
    	                sorguIfadesi.setString(7, combo_ilce.getSelectionModel().getSelectedItem().toString());
    	                sorguIfadesi.setString(8, combo_il.getSelectionModel().getSelectedItem().toString());
    	                sorguIfadesi.executeUpdate();

    	                girise_don(event);
    	                JOptionPane.showMessageDialog(new JFrame(), "Kaydınız başarılı. Yönetici tarafından onaylandıktan sonra giriş yapabilirsiniz.", "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
    	            } else {
    	                JOptionPane.showMessageDialog(new JFrame(), "Girdiğiniz TC numarası zaten kayıtlı.", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	            }
    	        }
    	    } catch (Exception e) {
    	        JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
    	        System.out.println(e.getMessage().toString());
    	    }
    	} else {
    	    JOptionPane.showMessageDialog(new JFrame(), "Hatalı bilgi girişi yapıldı.", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	}

    }

    @FXML
    void btn_kapat_click(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void btn_kimlik_arka_click(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Kimliğinizin arka yüzünün bulunduğu fotoğrafı seçiniz.");
    	File dosya =fileChooser.showOpenDialog(new Stage());
    	Image foto = new Image(dosya.toURI().toString(), 240, 135, false, false);
    	img_kimlik_arka.setImage(foto);
    }

    @FXML
    void btn_kimlik_on_click(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Kimliğinizin ön yüzünün bulunduğu fotoğrafı seçiniz.");
    	File dosya =fileChooser.showOpenDialog(new Stage());
    	Image foto = new Image(dosya.toURI().toString(), 240, 135, false, false);
    	img_kimlik_on.setImage(foto);
    }
    
    @FXML
    void combo_il_click(ActionEvent event) {
    	ObservableList<String> veriListesi = FXCollections.observableArrayList();
        sql="select ad from ilceler where il_id=?";
        try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
        	sorguIfadesi.setInt(1, combo_il.getSelectionModel().getSelectedIndex()+1);
        	ResultSet getirilen=sorguIfadesi.executeQuery();
        	String veri;
        	while (getirilen.next()) {
				veri=getirilen.getString("ad");
				veriListesi.add(veri);
			}
        	combo_ilce.setItems(veriListesi);
		} catch (Exception e) {
    		JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
    				"Hata", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage().toString());
		}
    }
    
    @FXML
    void initialize() throws IOException {
    	page_operations.upload_images_2button(btn_kapat, btn_geri_don);
    	
    	page_operations.text_format_sayi(txt_tc);
    	page_operations.text_format_yazi(txt_ad_soyad);
    	
    	String islem=uye_girisi_controller.islem_ne;
    	islem_ne=islem;
    	
    	if (islem_ne=="şifre değiştirme") {
			btn_islem_tamamla.setText("Şifreyi Değiştir.");
		}
    	else {
			btn_islem_tamamla.setText("Kayıt Ol.");
		}
        
        ObservableList<String> veriListesi = FXCollections.observableArrayList();
        sql="select ad from iller";
        try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
        	ResultSet getirilen=sorguIfadesi.executeQuery();
        	String veri;
        	while (getirilen.next()) {
				veri=getirilen.getString("ad");
				veriListesi.add(veri);
			}
        	combo_il.setItems(veriListesi);
		} catch (Exception e) {
    		JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
    				"Hata", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage().toString());
			System.exit(0);
		}
    }
    
    private byte[] imageToBytes(Image image) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (Exception e) {
            try {
            	ImageIO.write(bufferedImage, "jpeg", outputStream);
			} catch (Exception e2) {
        		JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
        				"Hata", JOptionPane.ERROR_MESSAGE);
				System.out.println(e.getMessage().toString());
			}
        }
        return outputStream.toByteArray();
    }
    
    void girise_don(ActionEvent event) throws IOException {
    	page_operations.page_switch("uye_girisi.fxml", event);
    }
    
    boolean uye_var_mi() {
    	sql="select * from uyeler where tc=?";
    	boolean uye_var_mi;
    	try {
    		sorguIfadesi=baglanti.prepareStatement(sql);
			sorguIfadesi.setString(1, txt_tc.getText());
			
			ResultSet getirilen = sorguIfadesi.executeQuery();
			if (getirilen.next()) {
				uye_var_mi=true;
			}
			else {
				uye_var_mi=false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			uye_var_mi=false;
		}
    	
    	return uye_var_mi;
    }
}
