package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.projeMySql.util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;



public class uye_ekrani_controller {
	public uye_ekrani_controller() {
		baglanti=VeritabaniUtil.Baglan();
	}
	
	@FXML
	private AnchorPane scene_uye_ekrani;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_dogalgaz_talep_olustur;

    @FXML
    private Button btn_elektrik_talep_olustur;

    @FXML
    private Button btn_geri_don;

    @FXML
    private Button btn_kapat;

    @FXML
    private Button btn_su_talep_olustur;

    @FXML
    private BarChart<String, Double> chart_dogalgaz_1ay;

    @FXML
    private BarChart<String, Double> chart_dogalgaz_3ay;

    @FXML
    private BarChart<String, Double> chart_elektrik_1ay;

    @FXML
    private BarChart<String, Double> chart_elektrik_3ay;

    @FXML
    private BarChart<String, Double> chart_su_1ay;

    @FXML
    private BarChart<String, Double> chart_su_3ay;

    @FXML
    private DatePicker date_dogalgaz;

    @FXML
    private DatePicker date_elektrik;

    @FXML
    private DatePicker date_su;

    @FXML
    private TextArea txt_dogalgaz_oneriler;

    @FXML
    private TextField txt_dogalgaz_tutar;

    @FXML
    private TextArea txt_elektrik_oneriler;

    @FXML
    private TextField txt_elektrik_tutar;

    @FXML
    private TextArea txt_su_oneriler;

    @FXML
    private TextField txt_su_tutar;
    
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
	uye_kayitlari uye_kayitlari;
	LocalDate selected_date;

    @FXML
    void btn_dogalgaz_talep_olustur_click(ActionEvent event) {
    	selected_date=date_dogalgaz.getValue();
    	if (!txt_dogalgaz_tutar.getText().trim().isEmpty() && selected_date!=null) {
        	faturaOlustur(3, txt_dogalgaz_tutar.getText(), date_dogalgaz.getValue());
        	date_dogalgaz.setValue(null);
    		txt_dogalgaz_tutar.setText(null);
		}
    	else {
            JOptionPane.showMessageDialog(new JFrame(), "Tutar ve tarih alanlarını boş bırakamazsınız.", "Uyarı", JOptionPane.WARNING_MESSAGE);
		}
    	selected_date=null;
    }

    @FXML
    void btn_elektrik_talep_olustur_click(ActionEvent event) {
    	selected_date=date_elektrik.getValue();
    	if (!txt_elektrik_tutar.getText().trim().isEmpty() && selected_date!=null) {
    		faturaOlustur(1, txt_elektrik_tutar.getText(), date_elektrik.getValue());
    		date_elektrik.setValue(null);
    		txt_elektrik_tutar.setText(null);
		}
    	else {
            JOptionPane.showMessageDialog(new JFrame(), "Tutar ve tarih alanlarını boş bırakamazsınız.", "Uyarı", JOptionPane.WARNING_MESSAGE);
		}
    	selected_date=null;
    }

    @FXML
    void btn_geri_don_click(ActionEvent event) {
    	try {
    		sayfa_gecis sayfa_gecis=new sayfa_gecis("uye_girisi.fxml", event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
					"Hata", JOptionPane.ERROR_MESSAGE);		}
    }

    @FXML
    void btn_kapat_click(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void btn_su_talep_olustur_click(ActionEvent event) {
    	selected_date=date_su.getValue();
    	if (!txt_su_tutar.getText().trim().isEmpty() && selected_date!=null) {
    		faturaOlustur(2, txt_su_tutar.getText(), date_su.getValue());
    		date_su.setValue(null);
    		txt_su_tutar.setText(null);
		}
    	else {
            JOptionPane.showMessageDialog(new JFrame(), "Tutar ve tarih alanlarını boş bırakamazsınız.", "Uyarı", JOptionPane.WARNING_MESSAGE);
		}
    	selected_date=null;
    }

    @FXML
    void initialize() {
    	text_format.text_format_sayi(txt_dogalgaz_tutar);
    	text_format.text_format_sayi(txt_elektrik_tutar);
    	text_format.text_format_sayi(txt_su_tutar);
    	
    	upload_gorseller upload_gorseller=new upload_gorseller(btn_kapat,btn_geri_don);

        uye_kayitlari=uye_girisi_controller.uye_kayitlar.get(0);
    	
        ileri_tarih_kontrolu(date_elektrik, date_su, date_dogalgaz);
        
        tablo_getir(chart_elektrik_1ay, 1, 1);
        tablo_getir(chart_elektrik_3ay, 1, 3);
        tablo_getir(chart_su_1ay, 2, 1);
        tablo_getir(chart_su_3ay, 2, 3);
        tablo_getir(chart_dogalgaz_1ay, 3, 1);
        tablo_getir(chart_dogalgaz_3ay, 3, 3);
        
        txt_elektrik_oneriler.setText("Enerji Verimliliği: Daha verimli elektrikli cihazlar kullanarak enerji tüketimini azaltabilirsiniz. Örneğin, enerji verimliliği yüksek olan LED lambalar kullanabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Isıtma ve Soğutma: Isıtma ve soğutma sistemlerini daha akıllıca kullanarak enerji tasarrufu sağlayabilirsiniz. Kışın termostatı düşük, yazın ise yüksek tutarak tasarruf edebilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Standby Modu: Cihazların stand-by modunda bile enerji tükettiğini unutmayın. Mümkünse cihazları fişten çekerek enerji israfını önleyebilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Su Isıtıcıları: Sıcak su kullanımını azaltarak enerji tasarrufu yapabilirsiniz. Düşük debili musluk ve duş başlıkları kullanarak su tüketimini azaltabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Güneş Enerjisi: Güneş enerjisi sistemleriyle elektrik tüketiminizi azaltabilir ve hatta kendi elektriğinizi üretebilirsiniz.\r\n"
        		+ "\r\n"
        		+ "İzolasyon: Ev veya iş yerinizin izolasyonunu iyileştirerek, ısıtma ve soğutma maliyetlerini azaltabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Düzenli Bakım: Elektrikli cihazların düzenli bakımını yaparak verimliliğini artırabilir ve enerji tüketimini azaltabilirsiniz.");
        txt_su_oneriler.setText("Düşük Debili Musluk ve Duş Başlıkları: Düşük debili musluk ve duş başlıkları kullanarak su tüketimini azaltabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Tamirat ve Sızıntı Kontrolü: Sızıntı yapan muslukları veya boruları tamir ettirerek su israfını önleyebilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Bulaşık ve Çamaşır Makineleri: Bulaşık ve çamaşır makinelerini tam kapasite kullanarak daha az su tüketebilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Bahçe Sulaması: Bahçe sulamasını akıllı sulama sistemleri veya yağmurlama sistemleriyle yaparak su tasarrufu yapabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Çift Çekme Klozetler: Çift çekme özelliğine sahip klozetler kullanarak su tüketimini azaltabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Yağmur Suyu Toplama Sistemi: Yağmur suyunu toplayarak bahçe sulaması gibi alanlarda kullanarak içme suyu tasarrufu yapabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Bilinçli Kullanım: Diş fırçalama, tıraş olma gibi aktivitelerde musluğu kapatmayı unutmayarak su tasarrufu yapabilirsiniz.");
        txt_dogalgaz_oneriler.setText("Düzenli Bakım: Isıtma sisteminin düzenli bakımını yaptırarak verimliliğini artırabilir ve doğalgaz tüketimini azaltabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Isı Yalıtımı: Ev veya iş yerinizin yalıtımını iyileştirerek, ısı kaybını azaltabilir ve doğalgaz faturasında tasarruf sağlayabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Termostat Ayarı: Kışın termostatı 20-22°C, yazın ise 24-26°C olarak ayarlayarak enerji tasarrufu yapabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Pencereler ve Kapılar: Pencereleri ve kapıları sızdırmaz hale getirerek, ısı kaybını önleyebilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Enerji Verimli Cihazlar: Enerji verimliliği yüksek olan ısıtıcıları, kombileri ve su ısıtıcıları tercih ederek doğalgaz tüketimini azaltabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Su Isıtıcısı Ayarı: Su ısıtıcısının sıcaklığını gereksiz yüksek tutmayarak enerji tasarrufu yapabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Kısa Süreli Isınma: Odaları kısa süreli ve yoğun olarak ısıtıp, sonra termostatı düşük bir seviyeye getirerek tasarruf yapabilirsiniz.\r\n"
        		+ "\r\n"
        		+ "Güneş Işığından Yararlanma: Gün ışığından daha fazla yararlanarak doğalgaz kullanımını azaltabilirsiniz.");
    }
    
    void faturaOlustur(int faturaTipiId, String tutar, LocalDate tarih) {
        //son 20 gün içinde aynı üye tarafından aynı fatura tipinde fatura oluşturulup oluşturulmadığını kontrol etme
        sql = "SELECT * FROM faturalar WHERE uyeler_id = (select id from uyeler where id=?) AND "
        		+ "tarih BETWEEN ? AND ? AND fatura_tipleri_id=(select id from fatura_tipleri where id=?)";
        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setInt(1, uye_kayitlari.getId());
            sorguIfadesi.setDate(2, Date.valueOf(tarih.minusDays(20)));
            sorguIfadesi.setDate(3, Date.valueOf(tarih.plusDays(20)));
            sorguIfadesi.setInt(4, faturaTipiId);

            getirilen = sorguIfadesi.executeQuery();
            if (getirilen.next()) {
                JOptionPane.showMessageDialog(new JFrame(), "Girilen tarihten sonraki veya önceki 20 gün fatura girişiniz var. Talebiniz reddedildi.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage().toString());
            JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Faturayı veritabanına ekleme
        sql = "INSERT INTO faturalar (tutar, tarih, uyeler_id, fatura_tipleri_id, ilceler_id, iller_id) VALUES "
        		+ "(?, ?, (select id from uyeler where id=?), (select id from fatura_tipleri where id=?), "
        		+ "(select id from ilceler where id=?), (select id from iller where id=?))";
        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setDouble(1, Double.parseDouble(tutar));
            sorguIfadesi.setDate(2, Date.valueOf(tarih));
            sorguIfadesi.setInt(3, uye_kayitlari.getId());
            sorguIfadesi.setInt(4, faturaTipiId);
            sorguIfadesi.setInt(5, uye_kayitlari.getIlceler_Id());
            sorguIfadesi.setInt(6, uye_kayitlari.getIller_Id());

            int eklemeSonucu = sorguIfadesi.executeUpdate();
            if (eklemeSonucu == 1) {
                JOptionPane.showMessageDialog(new JFrame(), "Fatura başarıyla oluşturuldu.", "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Fatura oluşturulurken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage().toString());
            JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void ileri_tarih_kontrolu(DatePicker datePicker1, DatePicker datePicker2, DatePicker datePicker3) {
    	DatePicker datePicker;
    	for (Integer i = 0; i<3; i++) {
			switch (i) {
			case 0: {
				datePicker=datePicker1;
				break;
			}
			case 1:{
				datePicker=datePicker2;
				break;
			}
			case 2:{
				datePicker=datePicker3;
				break;
			}
			default:
				throw new IllegalArgumentException("Beklenmedik değer: " + i);
			}
			datePicker.setDayCellFactory(picker -> new DateCell() {
	    		@Override
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                setDisable(date.isAfter(LocalDate.now()));
	            }
	        });
		}
    }
    
    void tablo_getir(BarChart<String, Double> barchart, Integer fatura_tipi_id, Integer kac_ay) {
    	double ortalama_tum=0.0;
    	double ortalama_kullanici=0.0;
    	if (kac_ay==3) {
    		sql = "SELECT AVG(tutar) FROM faturalar WHERE fatura_tipleri_id=(select id from fatura_tipleri where id=?) "
    				+ "and tarih>=DATE_SUB(CURDATE(),INTERVAL 3 MONTH) and onay='onaylanmis'";
    		try {
        		sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setInt(1, fatura_tipi_id);
                
                getirilen=sorguIfadesi.executeQuery();
                if (getirilen.next()) {
    				ortalama_tum=getirilen.getDouble(1);
    			}
    		} catch (Exception e) {
    			System.out.println(e.getMessage().toString());
                JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
    		}

    		sql = "SELECT AVG(tutar) FROM faturalar WHERE uyeler_id = (select id from uyeler where id=?) "
            		+ "and fatura_tipleri_id=(select id from fatura_tipleri where id=?) and tarih>=DATE_SUB(CURDATE(),INTERVAL 3 MONTH) and onay='onaylanmis'";
    		try {
        		sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setInt(1, uye_kayitlari.getId());
                sorguIfadesi.setInt(2, fatura_tipi_id);
                
                getirilen=sorguIfadesi.executeQuery();
                if (getirilen.next()) {
    				ortalama_kullanici=getirilen.getDouble(1);
    			}
    		} catch (Exception e) {
    			System.out.println(e.getMessage().toString());
                JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
    		}
		}
    	else {
    		sql = "SELECT AVG(tutar) FROM faturalar WHERE fatura_tipleri_id=(select id from fatura_tipleri where id=?) "
    				+ "and tarih>=DATE_SUB(CURDATE(),INTERVAL 1 MONTH) and onay='onaylanmis'";
    		try {
        		sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setInt(1, fatura_tipi_id);
                
                getirilen=sorguIfadesi.executeQuery();
                if (getirilen.next()) {
    				ortalama_tum=getirilen.getDouble(1);
    			}
    		} catch (Exception e) {
    			System.out.println(e.getMessage().toString());
                JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
    		}

    		sql = "SELECT AVG(tutar) FROM faturalar WHERE uyeler_id = (select id from uyeler where id=?) "
            		+ "and fatura_tipleri_id=(select id from fatura_tipleri where id=?) and tarih>=DATE_SUB(CURDATE(),INTERVAL 1 MONTH) and onay='onaylanmis'";
    		try {
        		sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setDouble(1, uye_kayitlari.getId());
                sorguIfadesi.setDouble(2, fatura_tipi_id);
                
                getirilen=sorguIfadesi.executeQuery();
                if (getirilen.next()) {
    				ortalama_kullanici=getirilen.getDouble(1);
    			}
    		} catch (Exception e) {
    			System.out.println(e.getMessage().toString());
                JOptionPane.showMessageDialog(new JFrame(), "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
    		}
		}

        CategoryAxis xAxis=new CategoryAxis();
        NumberAxis yAxis=new NumberAxis();
        
        XYChart.Series data = new XYChart.Series();
        
        data.getData().add(new XYChart.Data("Ortalama",ortalama_tum));
        data.getData().add(new XYChart.Data("Siz",ortalama_kullanici));

        barchart.getData().add(data);
    }
}
