package org.foi.nwtis.ftocko.aplikacija_5.ws;

import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_5.klijenti.Klijent;
import org.foi.nwtis.ftocko.aplikacija_5.wsock.Info;
import org.foi.nwtis.ftocko.aplikacija_5.zrna.jms.PosiljateljPoruke;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.AvionLeti;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;

/**
 * Klasa koja predstavlja web servis aerodromi.
 */
@WebService(serviceName = "aerodromi")
public class WsAerodromi {

	private Klijent klijent = new Klijent();

	@Inject
	private Info info = new Info();

	/** kontekst web servisa */
	@Resource
	private WebServiceContext wsContext;
	
	@EJB
	PosiljateljPoruke posiljatelj;

	@WebMethod
	public List<AvionLeti> dajPolaskeDan(String korisnik, String zeton, String icao, String danOd, String danDo) {

		List<AvionLeti> polasci = null;

		polasci = klijent.dajPolaske(korisnik, zeton, icao, danOd, danDo, 0);

		if (polasci != null) {
			return polasci;
		}

		return new ArrayList<AvionLeti>();

	}

	@WebMethod
	public List<AvionLeti> dajPolaskeVrijeme(String korisnik, String zeton, String icao, String vrijemeOd, String vrijemeDo) {

		List<AvionLeti> polasci = null;
		
		polasci = klijent.dajPolaske(korisnik, zeton, icao, vrijemeOd, vrijemeDo, 1);
		
		if (polasci != null) {
			return polasci;
		}

		return new ArrayList<AvionLeti>();
		
	}

	@WebMethod
	public boolean dodajAerodromPreuzimanje(String korisnik, String zeton, String icao) {

		boolean dodano = false;
		Aerodrom noviPraceniAerodrom = new Aerodrom();

		noviPraceniAerodrom.setIcao(icao);
		dodano = klijent.dodajAerodromPreuzimanje(korisnik, zeton, noviPraceniAerodrom);

		if (dodano) {
			info.posalji(icao);
			posiljatelj.novaAerodromPoruka(noviPraceniAerodrom);
		}

		return dodano;

	}

}
