package org.foi.nwtis.ftocko.aplikacija_3.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_3.klijenti.Klijent;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.AerodromiDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.AerodromiDolasciDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.AerodromiPolasciDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.AerodromiPraceniDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.ProvjeraAutorizacije;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.AvionLeti;

import com.google.gson.Gson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("aerodromi")
public class RestAerodromi {
	
	private ProvjeraAutorizacije pa = new ProvjeraAutorizacije();
	private AerodromiDAO aDao = new AerodromiDAO();
	private AerodromiPraceniDAO aPraceniDao = new AerodromiPraceniDAO();
	private AerodromiPolasciDAO apDao = new AerodromiPolasciDAO();
	private AerodromiDolasciDAO adDao = new AerodromiDolasciDAO();
	private Klijent k = new Klijent();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajAerodrome(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			List<Aerodrom> aerodromi = aDao.dohvatiSveAerodrome();

			odgovor = Response.status(Response.Status.OK).entity(aerodromi).build();
		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;

	}
	
	@GET
	@Path("/preuzimanje")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajAerodromeZaPreuzimanje(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			List<Aerodrom> aerodromi = aPraceniDao.dohvatiPraceneAerodrome();

			odgovor = Response.status(Response.Status.OK).entity(aerodromi).build();
		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;

	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dodajAerodromZaPreuzimanje(@HeaderParam("korisnik") String korisnik,
			@HeaderParam("zeton") String zeton, String aerodrom) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			Gson gson = new Gson();
			Aerodrom a = new Aerodrom();
			a = gson.fromJson(aerodrom, Aerodrom.class);

			int izvrsenUpit = aPraceniDao.dodajPraceniAerodrom(a);

			if (izvrsenUpit > 0) {
				odgovor = Response.status(Response.Status.OK).entity("Aerodrom za preuzimanje je uspješno dodan!")
						.build();
			}

			else {
				odgovor = Response.status(Response.Status.NOT_ACCEPTABLE)
						.entity("Nisu uneseni valjani podaci aerodroma ili isti već postoji!").build();
			}

		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response dajAerodrom(@PathParam("icao") String icao, @HeaderParam("korisnik") String korisnik,
			@HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			Aerodrom a = aDao.dohvatiAerodrom(icao);

			if (a != null) {
				odgovor = Response.status(Response.Status.OK).entity(a).build();
			}

			else {
				odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nije pronađen zadani aerodrom!").build();
			}

		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}/polasci")
	public Response dajPolaske(@PathParam("icao") String icao, @QueryParam("vrsta") String vrsta,
			@QueryParam("od") String datumOd, @QueryParam("do") String datumDo,
			@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;
		List<AvionLeti> polasci = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			if (vrsta.compareTo("1") == 0) {

				int t1 = Integer.parseInt(datumOd);
				int t2 = Integer.parseInt(datumDo);

				polasci = apDao.dajSvePolaske(icao, t1, t2);

				return Response.status(Response.Status.OK).entity(polasci).build();

			}

			else {

				long prviTimestamp = 0;
				long drugiTimestamp = 0;

				String prvoVrijDan = datumOd;
				String drugoVrijDan = datumDo;

				prvoVrijDan = prvoVrijDan += " 00:00:00";
				drugoVrijDan = drugoVrijDan += " 00:00:00";

				try {
					Date prviDatum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(prvoVrijDan);
					prviTimestamp = prviDatum.getTime() / 1000;

					Date drugiDatum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(drugoVrijDan);
					drugiTimestamp = drugiDatum.getTime() / 1000;
				} catch (Exception e) {
					System.out.println("Problem kod parsiranja!");
				}

				polasci = apDao.dajSvePolaske(icao, (int) prviTimestamp, (int) drugiTimestamp);

				if (polasci != null) {
					odgovor = Response.status(Response.Status.OK).entity(polasci).build();
				} else {
					odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema polazaka!").build();
				}

			}

		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}/dolasci")
	public Response dajDolaske(@PathParam("icao") String icao, @QueryParam("vrsta") String vrsta,
			@QueryParam("od") String datumOd, @QueryParam("do") String datumDo,
			@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;
		List<AvionLeti> dolasci = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			if (vrsta.compareTo("1") == 0) {

				int t1 = Integer.parseInt(datumOd);
				int t2 = Integer.parseInt(datumDo);

				dolasci = adDao.dajSveDolaske(icao, t1, t2);

				return Response.status(Response.Status.OK).entity(dolasci).build();

			}

			else {

				long prviTimestamp = 0;
				long drugiTimestamp = 0;

				String prvoVrijDan = datumOd;
				String drugoVrijDan = datumDo;

				prvoVrijDan = prvoVrijDan += " 00:00:00";
				drugoVrijDan = drugoVrijDan += " 00:00:00";

				try {
					Date prviDatum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(prvoVrijDan);
					prviTimestamp = prviDatum.getTime() / 1000;

					Date drugiDatum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(drugoVrijDan);
					drugiTimestamp = drugiDatum.getTime() / 1000;
				} catch (Exception e) {
					System.out.println("Problem kod parsiranja!");
				}

				dolasci = adDao.dajSveDolaske(icao, (int) prviTimestamp, (int) drugiTimestamp);

				if (dolasci != null) {
					odgovor = Response.status(Response.Status.OK).entity(dolasci).build();
				} else {
					odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema dolazaka!").build();
				}

			}

		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao1}/{icao2}")
	public Response dajUdaljenostAerodroma(@PathParam("icao1") String icao1, @PathParam("icao2") String icao2,
			@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			String n = k.posaljiKomandu("DISTANCE " + icao1 + " " + icao2);

			if (n.contains("OK")) {

				String[] p = n.split(" ");
				n = p[1];

				odgovor = Response.status(Response.Status.OK).entity("udaljenost: " + n).build();
			}

			else {
				odgovor = Response.status(Response.Status.NOT_FOUND).entity(n).build();
			}

		}

		else if (statusniKod == 401) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		else if (statusniKod == 408) {
			odgovor = Response.status(Response.Status.REQUEST_TIMEOUT).entity("Žeton je istekao ili više nije valjan!")
					.build();
		}

		else if (statusniKod == 0) {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neautoriziran pristup!").build();
		}

		return odgovor;
	}

}
