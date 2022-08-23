package org.foi.nwtis.ftocko.aplikacija_6.zrna;

import java.io.Serializable;
import java.util.Vector;

import org.foi.nwtis.ftocko.aplikacija_6.jpa.criteriaapi.KorisniciJpa;
import org.foi.nwtis.ftocko.aplikacija_6.klijenti.Klijent;
import org.foi.nwtis.ftocko.jpa.entiteti.Korisnici;
import org.foi.nwtis.podaci.Korisnik;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@ViewScoped
@Named("prijava")
public class Prijava implements Serializable{
	
	private String korime;
	private String lozinka;

	@EJB
	KorisniciJpa korisniciJpa;
	
	private Klijent klijent = new Klijent();
	
	public String getKorime() {
		return korime;
	}
	public void setKorime(String korime) {
		this.korime = korime;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	private void kreirajSesiju(String zeton) {
		
		FacesContext facesKontekst = FacesContext.getCurrentInstance();
		HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);
		sesija.setAttribute("korisnik",korime);
		sesija.setAttribute("zeton",zeton);
		
	}
	
	public Object prijaviKorisnika() {
		
		boolean autenticiran = false;
		
		try {
			
			Vector<Korisnici> korisnici = (Vector<Korisnici>) korisniciJpa.dajSveKorisnike();
			
			for(int i=0;i<korisnici.size();i++) {
				if(korisnici.get(i).getKorisnik().compareTo(korime) == 0 &&
						korisnici.get(i).getLozinka().compareTo(lozinka) == 0) {
					autenticiran = true;
					break;
				}
			}
			
		}
		
		catch(Exception e) {
			System.out.println("Problem kod prijave korisnika!");
		}
		
		if(autenticiran) {
			
			String zeton = klijent.dajZeton(korime, lozinka);
			
			if(zeton.compareTo("") != 0) {
				
				kreirajSesiju(zeton);
				return "OK";
			}
			
		}
		
		return "ERROR";
		
	}
	
	public Object odjaviKorisnika() {
		
		String preusmjeriNaPogresku = "NE";
		
		try {
			
			FacesContext facesKontekst = FacesContext.getCurrentInstance();
			HttpSession sesija = (HttpSession) facesKontekst.getExternalContext().getSession(true);
			
			String korisnik = (String) sesija.getAttribute("korisnik");
			String zeton = (String) sesija.getAttribute("zeton");
			
			if(korisnik == null || zeton == null) {
				preusmjeriNaPogresku = "DA";
			}
			
			else {
				Korisnik k = klijent.dajKorisnika(korisnik, zeton);
				
				if(k == null) {
					preusmjeriNaPogresku = "DA";
				}
				
				else {
					int status = klijent.obrisiZeton(k.getKorIme(), k.getLozinka(), zeton);
					
					if(status != 200) {
						preusmjeriNaPogresku = "DA";
					}
				}
			}	
			
		}
		
		catch(Exception e) {
			System.out.println("Problem kod odjave korisnika!");
		}
		
		System.out.println(preusmjeriNaPogresku);
		
		return preusmjeriNaPogresku;
			
	}
	
}
