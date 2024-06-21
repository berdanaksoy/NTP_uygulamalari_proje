package application;

public class yonetici_kayitlar {
	private Integer id;
	private String ad;
	private String sifre;
	private Integer fatura_tipleri_id;
	
	public yonetici_kayitlar(Integer id, String ad, String sifre, Integer fatura_tipleri_id) {
		this.id=id;
		this.ad=ad;
		this.sifre=sifre;
		this.fatura_tipleri_id=fatura_tipleri_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public Integer getFatura_tipleri_id() {
		return fatura_tipleri_id;
	}

	public void setFatura_tipleri_id(Integer fatura_tipleri_id) {
		this.fatura_tipleri_id = fatura_tipleri_id;
	}
	
}
