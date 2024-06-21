package application;

import java.sql.Date;

public class uye_kayitlari {
	private Integer id;
	private String tc;
	private String ad_soyad;
	private Date uyelik_tarihi;
	private java.sql.Blob kimlik_foto1;
	private java.sql.Blob kimlik_foto2;
	private String sifre;
	private Integer iller_Id;
	private Integer ilceler_Id;
	private Integer hatalı_fatura_giris_sayisi;
	private String onay;
	
	
	//Üye
	public uye_kayitlari(Integer id, String tc, String ad_soyad, Date uyelik_tarihi, java.sql.Blob blob, 
			java.sql.Blob blob2, String sifre, Integer iller_Id, Integer ilceler_Id, 
			Integer hatali_fatura_giris_sayisi, String onay)
	{
		this.id=id;
		this.tc=tc;
		this.ad_soyad=ad_soyad;
		this.uyelik_tarihi=uyelik_tarihi;
		this.kimlik_foto1=blob;
		this.kimlik_foto2=blob2;
		this.sifre=sifre;
		this.iller_Id=iller_Id;
		this.ilceler_Id=ilceler_Id;
		this.hatalı_fatura_giris_sayisi=hatali_fatura_giris_sayisi;
		this.onay=onay;
	}
	
	public uye_kayitlari(Integer id) {
		this.id=id;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getAd_soyad() {
		return ad_soyad;
	}
	public void setAd_soyad(String ad_soyad) {
		this.ad_soyad = ad_soyad;
	}
	public Date getUyelik_tarihi() {
		return uyelik_tarihi;
	}
	public void setUyelik_tarihi(Date uyelik_tarihi) {
		this.uyelik_tarihi = uyelik_tarihi;
	}
	public java.sql.Blob getKimlik_foto1() {
		return kimlik_foto1;
	}
	public void setKimlik_foto1(java.sql.Blob kimlik_foto1) {
		this.kimlik_foto1 = kimlik_foto1;
	}
	public java.sql.Blob getKimlik_foto2() {
		return kimlik_foto2;
	}
	public void setKimlik_foto2(java.sql.Blob kimlik_foto2) {
		this.kimlik_foto2 = kimlik_foto2;
	}
	public String getSifre() {
		return sifre;
	}
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	public Integer getIller_Id() {
		return iller_Id;
	}
	public void setIllerId(Integer iller_Id) {
		this.iller_Id = iller_Id;
	}
	public Integer getIlceler_Id() {
		return ilceler_Id;
	}
	public void setIlceler_Id(Integer ilceler_Id) {
		this.ilceler_Id = ilceler_Id;
	}
	public Integer getHatalı_fatura_giris_sayisi() {
		return hatalı_fatura_giris_sayisi;
	}
	public void setHatalı_fatura_giris_sayisi(Integer hatalı_fatura_giris_sayisi) {
		this.hatalı_fatura_giris_sayisi = hatalı_fatura_giris_sayisi;
	}
	public String getOnay() {
		return onay;
	}

	public void setOnay(String onay) {
		this.onay = onay;
	}
}
