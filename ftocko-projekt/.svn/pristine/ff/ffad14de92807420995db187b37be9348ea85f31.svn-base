package org.foi.nwtis.ftocko.aplikacija_4.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_4.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;

import com.google.gson.Gson;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Controller
@Path("administracija")
@RequestScoped
public class KontrolerAdministracije {

	private Klijent klijent = new Klijent();
	private KlijentPosluzitelja klijentP = new KlijentPosluzitelja();
	private UrlGenerator ug = new UrlGenerator();

	@Inject
	private Models model;

	@GET
	@Path("pocetak")
	@View("index.jsp")
	public void pocetak(@Context HttpServletRequest hsr) {

		String token = "";

		HttpSession sesija = hsr.getSession();

		try {
			String korisnik = (String) sesija.getAttribute("korisnik");

			String zeton = (String) sesija.getAttribute("zeton");

			Korisnik k = klijent.dajKorisnika(korisnik, zeton);

			boolean zetonValjan = klijent.provjeriZeton(korisnik, k.getLozinka(), zeton);

			if (zetonValjan) {
				token = "valjan";
			}

			model.put("token", token);
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja podataka iz sesije!");
		}

	}

	@GET
	@Path("registracija")
	@View("registracija.jsp")
	public void registracija(@QueryParam("poruka") String poruka) {
		
		if(poruka != null) {
			model.put("poruka", poruka);
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("registracija")
	public String registracijaKorisnika(@FormParam("ime") String ime, @FormParam("prezime") String prezime,
			@FormParam("email") String email, @FormParam("korime") String korime,
			@FormParam("lozinka") String lozinka) {

		String korisnik = "";
		String korLozinka = "";
		String zeton = "";

		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) SlusacAplikacije.sc.getAttribute("Postavke");
			korisnik = pbp.dajPostavku("sustav.korisnik");
			korLozinka = pbp.dajPostavku("sustav.lozinka");
			zeton = klijent.dajZeton(korisnik, korLozinka);
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja postavki ili žetona!");
		}

		if (ime.compareTo("") == 0 || prezime.compareTo("") == 0 || email.compareTo("") == 0 || 
				korime.compareTo("") == 0 || lozinka.compareTo("") == 0) {
			String poruka = "Neuspješna registracija (nisu unesena sva polja)!";
			return ug.vratiUrl(poruka,"registracija");
		}

		else if (zeton != "") {

			Korisnik noviKorisnik = new Korisnik();
			noviKorisnik.setIme(ime);
			noviKorisnik.setPrezime(prezime);
			noviKorisnik.setEmail(email);
			noviKorisnik.setKorIme(korime);
			noviKorisnik.setLozinka(lozinka);

			int registriran = klijent.registrirajKorisnika(noviKorisnik, korisnik, zeton);

			if (registriran == 200) {
				return "redirect:/administracija/prijava";
			}

			String poruka = "Nije moguće registrirati novog korisnika!";
			return ug.vratiUrl(poruka,"registracija");
		}

		String poruka = "Nije moguće registrirati novog korisnika!";
		return ug.vratiUrl(poruka,"registracija");

	}

	@GET
	@Path("prijava")
	@View("prijava.jsp")
	public void prijava(@QueryParam("poruka") String poruka) {
		
		if(poruka != null) {
			model.put("poruka", poruka);
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("prijava")
	public String prijavaKorisnika(@Context HttpServletRequest hsr, @FormParam("korisnik") String korisnik,
			@FormParam("lozinka") String lozinka) {

		String zeton = "";

		zeton = klijent.dajZeton(korisnik, lozinka);

		if (zeton.compareTo("") != 0) {

			HttpSession sesija = hsr.getSession();
			sesija.setAttribute("korisnik", korisnik);
			sesija.setAttribute("zeton", zeton);

			return "redirect:/administracija/pocetak";
		}
		
		String poruka = "Neuspješna prijava (pogrešno korisničko ime ili lozinka)!";
		
		return ug.vratiUrl(poruka, "prijava");

	}

	@GET
	@Path("pregledKorisnika")
	@View("pregledKorisnika.jsp")
	public void pregledKorisnika(@Context HttpServletRequest hsr, @QueryParam("poruka") String poruka) {

		String korisnik = "";
		String zeton = "";
		String admin = "";
		boolean provjeriAdmina = false;

		HttpSession sesija = hsr.getSession();

		try {
			korisnik = (String) sesija.getAttribute("korisnik");

			zeton = (String) sesija.getAttribute("zeton");
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja podataka iz sesije!");
		}

		if (korisnik != null && zeton != null) {
			provjeriAdmina = klijent.provjeriAdmina(korisnik, zeton);
		}

		if (provjeriAdmina) {
			admin = "admin";
		}

		List<Korisnik> korisnici = klijent.dajSveKorisnike(korisnik, zeton);

		if (korisnici != null) {
			
			if(poruka != null) {
				model.put("poruka", poruka);
			}

			model.put("korisnici", korisnici);
			model.put("korisnik", korisnik);
			model.put("admin", admin);
		}

	}

	@GET
	@Path("obrisiZetoneKorisnika")
	public String obrisiZetoneKorisnika(@Context HttpServletRequest hsr, @QueryParam("korime") String korime) {

		String korisnik = "";
		String zeton = "";
		int odgovor = 0;

		HttpSession sesija = hsr.getSession();

		try {
			korisnik = (String) sesija.getAttribute("korisnik");

			zeton = (String) sesija.getAttribute("zeton");
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja podataka iz sesije!");
		}

		Korisnik k = klijent.dajKorisnika(korisnik, zeton);

		odgovor = klijent.obrisiZetoneKorisnika(korisnik, k.getLozinka(), korime);
		
		if(odgovor != 200) {
			String poruka = "Nije moguće obrisati žetone korisnika "+korime+"!";
			return ug.vratiUrl(poruka, "brisanjeZetonaKorisnika");
		}

		String poruka = "Žetoni korisnika "+korime+" su uspješno obrisani!";
		return ug.vratiUrl(poruka, "brisanjeZetonaKorisnika");

	}

	@GET
	@Path("obrisiVlastitiZeton")
	public String obrisiVlastitiZeton(@Context HttpServletRequest hsr) {

		String korisnik = "";
		String zeton = "";
		int odgovor = 0;

		HttpSession sesija = hsr.getSession();

		try {
			korisnik = (String) sesija.getAttribute("korisnik");

			zeton = (String) sesija.getAttribute("zeton");
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja podataka iz sesije!");
		}

		Korisnik k = klijent.dajKorisnika(korisnik, zeton);

		odgovor = klijent.obrisiZeton(korisnik, k.getLozinka(), zeton);

		return "redirect:/administracija/pregledKorisnika";

	}

	@GET
	@Path("upravljanjePosluziteljem")
	@View("upravljanjePosluziteljem.jsp")
	public void upravljanjePosluziteljem(@Context HttpServletRequest hsr, @QueryParam("statusKomande") String tekst,
			@QueryParam("statusServera") String statusServera) {

		String korisnik = "";
		String zeton = "";
		String status = null;

		HttpSession sesija = hsr.getSession();

		try {
			korisnik = (String) sesija.getAttribute("korisnik");

			zeton = (String) sesija.getAttribute("zeton");

			Korisnik k = klijent.dajKorisnika(korisnik, zeton);

			boolean zetonValjan = klijent.provjeriZeton(korisnik, k.getLozinka(), zeton);

			if (zetonValjan) {
				
				if(tekst != null) {
					model.put("tekst", tekst);
				}
				
				if(statusServera != null) {
					model.put("status", statusServera);
				}
				
				else {
					status = klijentP.posaljiKomandu("STATUS");
					
					String [] p = status.split(" ");
					
					model.put("status", p[1]);
				}

			}
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja podataka iz sesije!");
		}

	}
	
	@POST
	@Path("posaljiKomandu")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String posaljiKomandu(@Context HttpServletRequest hsr, @FormParam("komanda") String komanda) {

		String korisnik = "";
		String zeton = "";
		StringBuilder sb = null;
		String pravaKomanda = "";
		
		HttpSession sesija = hsr.getSession();

		try {
			korisnik = (String) sesija.getAttribute("korisnik");

			zeton = (String) sesija.getAttribute("zeton");
		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja podataka iz sesije!");
		}
		
		switch(komanda) {
		case "Inicijalizacija poslužitelja":
			pravaKomanda = "INIT";
			break;
		case "Prekid rada poslužitelja":
			pravaKomanda = "QUIT";
			break;
		case "Učitavanje podataka":
			Gson gson = new Gson();
			List<Aerodrom> aerodromi = klijent.dajSveAerodrome(korisnik, zeton);
			String kolekcijaAerodroma = gson.toJson(aerodromi);
			pravaKomanda = "LOAD "+kolekcijaAerodroma;
			break;
		case "Brisanje podataka":
			pravaKomanda = "CLEAR";
			break;
		}
		
		String odgovorServera = klijentP.posaljiKomandu(pravaKomanda);
		
		if(odgovorServera.compareTo("Socket zatvoren") == 0) {
			return "redirect:/administracija/ispisPogreskeUpravljanjaPosluziteljem";
		}
		
		try {
			if(pravaKomanda.compareTo("QUIT") == 0) {
				sb = new StringBuilder(
				        "redirect:/administracija/upravljanjePosluziteljem?statusKomande=").append(
				        URLEncoder.encode(odgovorServera, "UTF-8")).append("&statusServera=").append(URLEncoder.encode("Zaustavljen", "UTF-8"));
			}
			else {
				sb = new StringBuilder(
				        "redirect:/administracija/upravljanjePosluziteljem?statusKomande=").append(
				        URLEncoder.encode(odgovorServera, "UTF-8"));
			}
			
		} catch (Exception e) {
			System.out.println("Problem kod enkodiranja!");
		}
		
		return sb.toString();

	}
	
	@GET
	@Path("ispisPogreskeUpravljanjaPosluziteljem")
	@View("ispisPogreskeUpravljanjaPosluziteljem.jsp")
	public void ispisPogreskeUpravljanjaPosluziteljem() {
		
	}

}
