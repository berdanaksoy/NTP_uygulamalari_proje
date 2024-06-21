package application;

import java.sql.Date;

public class faturalar {
	private Double tutar;
	private Date tarih;
	private Integer uyeler_id;
	private Integer fatura_tipleri_id;
	private Integer ilceler_id;
	private Integer iller_id;
	private String fatura_sahibi_tc;
	
	public faturalar(Double tutar, Date tarih, Integer uyeler_id, Integer fatura_tipleri_id, Integer ilceler_id, Integer iller_id) {
		this.tutar=tutar;
		this.tarih=tarih;
		this.uyeler_id=uyeler_id;
		this.fatura_tipleri_id=fatura_tipleri_id;
		this.ilceler_id=ilceler_id;
		this.iller_id=iller_id;
	}
	
	public String getFatura_sahibi_tc() {
		return fatura_sahibi_tc;
	}

	public void setFatura_sahibi_tc(String fatura_sahibi_tc) {
		this.fatura_sahibi_tc = fatura_sahibi_tc;
	}

	public Double getTutar() {
		return tutar;
	}

	public void setTutar(Double tutar) {
		this.tutar = tutar;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public Integer getUyeler_id() {
		return uyeler_id;
	}

	public void setUyeler_id(Integer uyeler_id) {
		this.uyeler_id = uyeler_id;
	}

	public Integer getFatura_tipleri_id() {
		return fatura_tipleri_id;
	}

	public void setFatura_tipleri_id(Integer fatura_tipleri_id) {
		this.fatura_tipleri_id = fatura_tipleri_id;
	}

	public Integer getIlceler_id() {
		return ilceler_id;
	}

	public void setIlceler_id(Integer ilceler_id) {
		this.ilceler_id = ilceler_id;
	}

	public Integer getIller_id() {
		return iller_id;
	}

	public void setIller_id(Integer iller_id) {
		this.iller_id = iller_id;
	}
}
