package org.foi.nwtis.ftocko.aplikacija_5.klijenti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_5.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.AvionLeti;

import com.google.gson.Gson;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class Klijent {

	private String adresaWA3 = "";

	public Klijent() {

		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) SlusacAplikacije.sc.getAttribute("Postavke");
			String adresa = pbp.dajPostavku("adresa.wa_3");
			this.adresaWA3 = adresa;

		} catch (Exception e) {
			System.out.println("Nije moguće dohvatiti konfiguracijsku/e postavku/e!");
			return;
		}
	}

	public Aerodrom dajAerodrom(String korisnik, String zeton, String icao) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi").path(icao);
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();
		Aerodrom a = null;
		// System.out.println(restOdgovor.getStatus());
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			a = gson.fromJson(odgovor, Aerodrom.class);

		}
		return a;
	}

	public boolean dodajAerodromPreuzimanje(String korisnik, String zeton, Aerodrom aerodrom) {

		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi");
		Response restOdgovor = webResurs.request().header("korisnik", korisnik).header("zeton", zeton)
				.post(Entity.json(aerodrom));

		if (restOdgovor.getStatus() == 200) {

			return true;
		}

		return false;
	}

	public List<AvionLeti> dajPolaske(String korisnik, String zeton, String icao, String odVrij, String doVrij, int vrsta) {

		List<AvionLeti> polasci = null;

		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi").path(icao).path("polasci")
				.queryParam("vrsta", vrsta).queryParam("od", odVrij).queryParam("do", doVrij);
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();

		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			polasci = new ArrayList<>();
			polasci.addAll(Arrays.asList(gson.fromJson(odgovor, AvionLeti[].class)));
		}

		return polasci;
	}

}
