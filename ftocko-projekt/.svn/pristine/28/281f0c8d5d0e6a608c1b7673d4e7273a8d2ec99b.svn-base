package org.foi.nwtis.ftocko.aplikacija_3.rest;

import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_3.podaci.Grupa;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.KorisniciDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.ProvjeraAutorizacije;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;

import com.google.gson.Gson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("korisnici")
public class RestKorisnici {
	
	private ProvjeraAutorizacije pa = new ProvjeraAutorizacije();
	private KorisniciDAO kDao = new KorisniciDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajKorisnike(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {
			
			List<Korisnik> korisnici = kDao.dohvatiSveKorisnike();
			
			odgovor = Response.status(Response.Status.OK).entity(korisnici).build();
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
	public Response dodajKorisnika(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton,
			String noviKorisnik) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {

			Gson gson = new Gson();
			Korisnik noviKor = new Korisnik();
			noviKor = gson.fromJson(noviKorisnik, Korisnik.class);

			int izvrsenUpit = kDao.dodajKorisnika(noviKor);

			if (izvrsenUpit > 0) {
				odgovor = Response.status(Response.Status.OK).entity("Novi korisnik je uspješno dodan!").build();
			}

			else {
				odgovor = Response.status(Response.Status.NOT_ACCEPTABLE)
						.entity("Nisu uneseni valjani korisnički podaci!").build();
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
	@Path("{korisnik}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajKorisnika(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton,
			@PathParam("korisnik") String korime) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {
			Korisnik k = kDao.dohvatiKorisnika(korime);

			if (k != null) {
				odgovor = Response.status(Response.Status.OK).entity(k).build();
			}

			else {
				odgovor = Response.status(Response.Status.NOT_FOUND).entity("Korisnik nije pronađen!").build();
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
	@Path("{korisnik}/grupe")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajGrupeKorisnika(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton,
			@PathParam("korisnik") String korime) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {
			List<Grupa> grupe = kDao.dohvatiGrupeKorisnika(korime);

			if (grupe.size() > 0) {
				odgovor = Response.status(Response.Status.OK).entity(grupe).build();
			}

			else {
				odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nisu pronađene grupe korisnika!").build();
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
