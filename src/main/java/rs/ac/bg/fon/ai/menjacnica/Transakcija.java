package rs.ac.bg.fon.ai.menjacnica;

import java.time.LocalDate;

public class Transakcija {
	private String izvornaValuta;
	private String krajnjaValuta;
	private double pocetniIznos;
	private double konvertovaniIznos;
	private LocalDate datumTransakcije;
	
	
	public Transakcija() {
		super();
	}

	public Transakcija(String izvornaValuta, String krajnjaValuta, double pocetniIznos, double konvertovaniIznos,
			LocalDate datumTransakcije) {
		super();
		this.izvornaValuta = izvornaValuta;
		this.krajnjaValuta = krajnjaValuta;
		this.pocetniIznos = pocetniIznos;
		this.konvertovaniIznos = konvertovaniIznos;
		this.datumTransakcije = datumTransakcije;
	}

	public String getIzvornaValuta() {
		return izvornaValuta;
	}
	
	public void setIzvornaValuta(String izvornaValuta) {
		this.izvornaValuta = izvornaValuta;
	}
	
	public String getKrajnjaValuta() {
		return krajnjaValuta;
	}
	
	public void setKrajnjaValuta(String krajnjaValuta) {
		this.krajnjaValuta = krajnjaValuta;
	}
	
	public double getPocetniIznos() {
		return pocetniIznos;
	}
	
	public void setPocetniIznos(double pocetniIznos) {
		this.pocetniIznos = pocetniIznos;
	}
	
	public double getKonvertovaniIznos() {
		return konvertovaniIznos;
	}
	
	public void setKonvertovaniIznos(double konvertovaniIznos) {
		this.konvertovaniIznos = konvertovaniIznos;
	}
	
	public LocalDate getDatumTransakcije() {
		return datumTransakcije;
	}
	
	public void setDatumTransakcije(LocalDate datumTransakcije) {
		this.datumTransakcije = datumTransakcije;
	}

	@Override
	public String toString() {
		return "Transakcija [izvornaValuta=" + izvornaValuta + ", krajnjaValuta=" + krajnjaValuta + ", pocetniIznos="
				+ pocetniIznos + ", konvertovaniIznos=" + konvertovaniIznos + ", datumTransakcije=" + datumTransakcije
				+ "]";
	}
}
