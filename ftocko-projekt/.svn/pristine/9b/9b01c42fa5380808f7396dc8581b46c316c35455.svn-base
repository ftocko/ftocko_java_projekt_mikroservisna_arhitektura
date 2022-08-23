package org.foi.nwtis.ftocko.aplikacija_6.provjereautorizacije;

import org.foi.nwtis.ftocko.aplikacija_6.klijenti.Klijent;
import org.foi.nwtis.podaci.Korisnik;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class UpraviteljSesije {
	
	private Klijent klijent = new Klijent();
	
	private boolean provjeriZetonKorisnika(String korisnik, String zeton) {
		
		boolean valjan = true;
		
		Korisnik k = klijent.dajKorisnika(korisnik, zeton);
		
		if(k == null) {
			valjan = false;
		}
		
		return valjan;
		
	}

	public boolean provjeriSesiju(ServletRequest req, ServletResponse res) {

		String korisnik = "";
		String zeton = "";
		boolean prijavljen = true;

		try {
			HttpServletRequest zahtjev = (HttpServletRequest) req;
			HttpSession sesija = zahtjev.getSession();
			korisnik = (String) sesija.getAttribute("korisnik");
			zeton = (String) sesija.getAttribute("zeton");
			
			if(korisnik == null || zeton == null) {
				prijavljen = false;
			}

			else {
				boolean valjanZeton = provjeriZetonKorisnika(korisnik,zeton);
				
				if(!valjanZeton) {
					prijavljen = false;
				}
				
			}

		} catch (Exception e) {
			System.out.println("Problem kod sesije!");
		}

		return prijavljen;

	}

}
