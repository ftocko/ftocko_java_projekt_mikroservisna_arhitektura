package org.foi.nwtis.ftocko.aplikacija_1;
import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.Konfiguracija;
import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.NeispravnaKonfiguracija;

/**
 * Apstraktna klasa za pripremu servera koja implementira sučelje IPripremaServera.
 */
public class PripremaServera{
	
	/**
	 * Metoda učitava konfiguraciju za poslužitelj iz datoteke.
	 *
	 * @param args polje unesenih parametara programa
	 * @return vraća objekt konfiguracije
	 */
	public Konfiguracija ucitajKonfiguraciju(String args) {
		
		String nazivDatoteke = args;
		Konfiguracija konfiguracija = null;
		
		try {
			konfiguracija = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
		} catch (NeispravnaKonfiguracija e) {
			ispis("Neispravna konfiguracija!");
			System.exit(0);
		}
		catch(Exception e) {
			ispis("Neispravna konfiguracija!");
			System.exit(0);
		}
		
		return konfiguracija;
		
	}
	
	/**
	 * Metoda za ispis konzolnih poruka.
	 *
	 * @param poruka poruka za ispis
	 */
	public void ispis(String poruka) {
		System.out.println(poruka);
	}

}
