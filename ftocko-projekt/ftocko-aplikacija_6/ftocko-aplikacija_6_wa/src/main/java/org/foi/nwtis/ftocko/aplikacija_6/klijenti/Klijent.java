package org.foi.nwtis.ftocko.aplikacija_6.klijenti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_6.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;

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
	
	public String dajZeton(String korisnik, String lozinka) {

		String zeton = "";

		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "provjere");
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("lozinka", lozinka).get();

		if (restOdgovor.getStatus() == 200) {

			String odgovor = restOdgovor.readEntity(String.class);

			if (odgovor != null && odgovor.compareTo("") != 0) {

				try {
					String[] p1 = odgovor.split(",");
					String[] p2 = p1[0].split(" ");
					zeton = p2[1];
				}

				catch (Exception e) {
					System.out.println("Problem kod splitanja!");
				}
			}

		}
		return zeton;
	}
	
	public int obrisiZeton(String korisnik, String lozinka, String token) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "provjere").path(token);
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik",korisnik)
				.header("lozinka",lozinka).delete();

		return restOdgovor.getStatus();
	}
	
	public boolean provjeriZeton(String korisnik, String lozinka, String token) {

		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "provjere").path(token);
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("lozinka", lozinka).get();

		if (restOdgovor.getStatus() == 200) {

			return true;

		}
		return false;
	}

	public List<Korisnik> dajSveKorisnike(String korisnik, String zeton) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "korisnici");
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();
		List<Korisnik> korisnici = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			korisnici = new ArrayList<>();
			korisnici.addAll(Arrays.asList(gson.fromJson(odgovor, Korisnik[].class)));
		}
		return korisnici;
	}
	
	public Korisnik dajKorisnika(String korisnik, String zeton) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "korisnici").path(korisnik);
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();
		Korisnik k = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			k = gson.fromJson(odgovor, Korisnik.class);

		}
		return k;
	}
	
	public List<Aerodrom> dajSveAerodrome(String korisnik, String zeton) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi");
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();
		List<Aerodrom> aerodromi = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromi = new ArrayList<>();
			aerodromi.addAll(Arrays.asList(gson.fromJson(odgovor, Aerodrom[].class)));
		}
		return aerodromi;
	}
	
	public List<Aerodrom> dajPraceneAerodrome(String korisnik, String zeton) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi").path("preuzimanje");
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();
		List<Aerodrom> aerodromi = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromi = new ArrayList<>();
			aerodromi.addAll(Arrays.asList(gson.fromJson(odgovor, Aerodrom[].class)));
		}
		return aerodromi;
	}
	
	public Aerodrom dajAerodrom(String korisnik, String zeton, String icao) {
		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi").path(icao);
		Response restOdgovor = webResurs.request().header("Accept", "application/json").header("korisnik", korisnik)
				.header("zeton", zeton).get();
		Aerodrom a = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			a = gson.fromJson(odgovor, Aerodrom.class);

		}
		return a;
	}
	
	public int dodajAerodromZaPreuzimanje(String korisnik, String zeton, Aerodrom aerodrom) {

		Client klijent = ClientBuilder.newClient();
		WebTarget webResurs = klijent.target(adresaWA3 + "aerodromi");
		Response restOdgovor = webResurs.request().header("korisnik", korisnik).header("zeton", zeton)
				.post(Entity.json(aerodrom));

		if (restOdgovor.getStatus() == 200) {
			return 200;
		}

		return 500;
	}

}