package org.foi.nwtis.ftocko.aplikacija_5.ws;

import org.foi.nwtis.ftocko.aplikacija_5.klijenti.Klijent;
import org.foi.nwtis.ftocko.aplikacija_5.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.aplikacija_5.zrna.jms.PosiljateljPoruke;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;

/**
 * Klasa WsMeteo koja predstavlja web servis meteo.
 */
@WebService(serviceName = "meteo")
public class WsMeteo {

	private Klijent klijent = new Klijent();

	/** kontekst web servisa */
	@Resource
	private WebServiceContext wsContext;
	
	@EJB
	PosiljateljPoruke posiljatelj;

	@WebMethod
	public MeteoPodaci dajMeteo(String korisnik, String zeton, String icao) {

		MeteoPodaci mp = null;
		Aerodrom a = null;
		String apiKljuc = "";

		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) SlusacAplikacije.sc.getAttribute("Postavke");
			apiKljuc = pbp.dajPostavku("OpenWeatherMap.apikey");
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja postavki!");
		}

		a = klijent.dajAerodrom(korisnik, zeton, icao);
		
		OWMKlijent owmk = new OWMKlijent(apiKljuc);
		
		try {
			if(a!=null) {
				mp = owmk.getRealTimeWeather(a.getLokacija().getLatitude(),a.getLokacija().getLongitude());
				posiljatelj.novaMeteoPoruka(mp, icao);
				return mp;
			}
		} catch (NwtisRestIznimka e) {
			System.out.println("Problem kod dohvaćanja meteo podataka!");
		}

		return new MeteoPodaci();
	}

}
