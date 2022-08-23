package org.foi.nwtis.ftocko.aplikacija_6.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.foi.nwtis.ftocko.aplikacija_6.jpa.criteriaapi.KorisniciJpa;
import org.foi.nwtis.ftocko.aplikacija_6.klijenti.Klijent;
import org.foi.nwtis.ftocko.aplikacija_6.podaci.ModificiraniKorisnik;
import org.foi.nwtis.ftocko.jpa.entiteti.Korisnici;
import org.foi.nwtis.podaci.Korisnik;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@ViewScoped
@Named("pregledKorisnika")
public class PregledKorisnika implements Serializable {

	private Klijent klijent = new Klijent();
	
	@EJB
	KorisniciJpa korisniciJpa;

	private List<ModificiraniKorisnik> korisnici = null;

	public List<ModificiraniKorisnik> getKorisnici() {

		List<ModificiraniKorisnik> dohvaceniKorisnici = dajKorisnike();

		this.korisnici = dohvaceniKorisnici;

		return korisnici;
	}

	public void setKorisnici(List<ModificiraniKorisnik> korisnici) {
		this.korisnici = korisnici;
	}

	private List<ModificiraniKorisnik> dajKorisnike() {

		Vector<Korisnici> bazniKorisnici = null;
		List<Korisnik> dohvaceniKorisnici = null;
		List<ModificiraniKorisnik> modificiraniKorisnici = new ArrayList<ModificiraniKorisnik>();

		FacesContext facesKontekst = FacesContext.getCurrentInstance();
		HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);

		String korisnik = (String) sesija.getAttribute("korisnik");
		String zeton = (String) sesija.getAttribute("zeton");

		try {
			
			dohvaceniKorisnici = klijent.dajSveKorisnike(korisnik, zeton);
			bazniKorisnici = (Vector<Korisnici>) korisniciJpa.dajSveKorisnike();
			boolean pronadjen = false;
			
			for(int i=0;i<dohvaceniKorisnici.size();i++) {
				
				pronadjen = false;
				
				ModificiraniKorisnik mk = new ModificiraniKorisnik();
				mk.setIme(dohvaceniKorisnici.get(i).getIme());
				mk.setPrezime(dohvaceniKorisnici.get(i).getPrezime());
				mk.setKorime(dohvaceniKorisnici.get(i).getKorIme());
				mk.setEmail(dohvaceniKorisnici.get(i).getEmail());
				mk.setLozinka(dohvaceniKorisnici.get(i).getLozinka());
				
				for(int j=0;j<bazniKorisnici.size();j++) {
					
					if(dohvaceniKorisnici.get(i).getKorIme().compareTo(
							bazniKorisnici.get(j).getKorisnik()) == 0) {
						pronadjen = true;
						break;
					}
					
				}
				
				if(pronadjen) {
					mk.setRazlikuje("ne");
				}
				
				else {
					mk.setRazlikuje("da");
				}
				
				modificiraniKorisnici.add(mk);
			}
			
		}
		
		catch(Exception e) {
			System.out.println("Problem kod dohvaćanja korisnika!");
		}

		return modificiraniKorisnici;

	}
	
	public void sinkroniziraj() {
				
		korisniciJpa.unesiKorisnike(this.korisnici);
		
	}

}
