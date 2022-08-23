package org.foi.nwtis.ftocko.aplikacija_6.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_6.klijenti.Klijent;
import org.foi.nwtis.podaci.Aerodrom;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@ViewScoped
@Named("pregledAerodroma")
public class PregledAerodroma implements Serializable {

	private Klijent klijent = new Klijent();

	private List<Aerodrom> aerodromi = new ArrayList<Aerodrom>();

	private List<Aerodrom> praceniAerodromi = null;

	private List<Aerodrom> sviAerodromi = null;

	private Aerodrom odabraniAerodrom = null;

	public Aerodrom getOdabraniAerodrom() {
		return odabraniAerodrom;
	}

	public void setOdabraniAerodrom(Aerodrom odabraniAerodrom) {
		this.odabraniAerodrom = odabraniAerodrom;
	}

	private String uneseniNazivAerodroma = "";

	public List<Aerodrom> getPraceniAerodromi() {
		this.praceniAerodromi = dohvatiPraceneAerodrome();
		return praceniAerodromi;
	}

	public void setPraceniAerodromi(List<Aerodrom> praceniAerodromi) {
		this.praceniAerodromi = praceniAerodromi;
	}

	public String getUneseniNazivAerodroma() {
		return uneseniNazivAerodroma;
	}

	public void setUneseniNazivAerodroma(String uneseniNazivAerodroma) {
		this.uneseniNazivAerodroma = uneseniNazivAerodroma;
	}

	public List<Aerodrom> getAerodromi() {
		return aerodromi;
	}

	public void setAerodromi(List<Aerodrom> aerodromi) {
		this.aerodromi = aerodromi;
	}

	private void dodajSveAerodrome() {

		FacesContext facesKontekst = FacesContext.getCurrentInstance();
		HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);

		String korisnik = (String) sesija.getAttribute("korisnik");
		String zeton = (String) sesija.getAttribute("zeton");

		this.sviAerodromi = klijent.dajSveAerodrome(korisnik, zeton);

	}

	public void pretraga() {

		FacesContext facesKontekst = FacesContext.getCurrentInstance();
		HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);

		String korisnik = (String) sesija.getAttribute("korisnik");
		String zeton = (String) sesija.getAttribute("zeton");

		dodajSveAerodrome();
		
		this.aerodromi = new ArrayList<Aerodrom>();

		for (Aerodrom a : this.sviAerodromi) {

			if (a.getIcao().contains(this.uneseniNazivAerodroma) || a.getNaziv().contains(this.uneseniNazivAerodroma)) {
				this.aerodromi.add(a);
			}
		}

	}

	private List<Aerodrom> dohvatiPraceneAerodrome() {

		FacesContext facesKontekst = FacesContext.getCurrentInstance();
		HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);

		String korisnik = (String) sesija.getAttribute("korisnik");
		String zeton = (String) sesija.getAttribute("zeton");

		List<Aerodrom> aerodromiZaPreuzimanje = null;

		aerodromiZaPreuzimanje = klijent.dajPraceneAerodrome(korisnik, zeton);

		return aerodromiZaPreuzimanje;

	}

	public void dodajZaPreuzimanje() {

		FacesContext facesKontekst = FacesContext.getCurrentInstance();
		HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);

		String korisnik = (String) sesija.getAttribute("korisnik");
		String zeton = (String) sesija.getAttribute("zeton");

		klijent.dodajAerodromZaPreuzimanje(korisnik, zeton, this.odabraniAerodrom);

	}

}
