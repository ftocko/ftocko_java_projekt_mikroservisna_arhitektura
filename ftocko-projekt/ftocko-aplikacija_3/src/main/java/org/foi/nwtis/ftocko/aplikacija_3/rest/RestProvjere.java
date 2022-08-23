package org.foi.nwtis.ftocko.aplikacija_3.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.foi.nwtis.ftocko.aplikacija_3.podaci.KorisniciDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.Zeton;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.ZetoniDAO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Klasa RestProvjere.
 */
@Path("provjere")
public class RestProvjere {
	
	private KorisniciDAO kDao = new KorisniciDAO();
	private ZetoniDAO zDao = new ZetoniDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response autenticirajKorisnika(@HeaderParam("korisnik") String korisnik,
			@HeaderParam("lozinka") String lozinka) {

		Response odgovor = null;

		String korime = kDao.autenticirajKorisnika(korisnik, lozinka);

		if (korime.compareTo("") != 0) {

			ZetoniDAO zDao = new ZetoniDAO();
			Zeton z = zDao.kreirajZeton(korime);
			Date vrijValjanostiZetona = new Date(z.getVrijeme_valjanosti());
			String sVrijValjanostiZetona = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(vrijValjanostiZetona);
			
			odgovor = Response.status(Response.Status.OK)
					.entity("zeton: " + z.getId() + ", vrijeme: " + sVrijValjanostiZetona).build();
		}

		else {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Neuspjela autentikacija!").build();
		}

		return odgovor;
	}

	@GET
	@Path("{token}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response provjeriZeton(@HeaderParam("korisnik") String korisnik, @HeaderParam("lozinka") String lozinka,
			@PathParam("token") String token) {

		Response odgovor = null;

		Zeton z = zDao.dohvatiZeton(Integer.parseInt(token));
		String korime = kDao.autenticirajKorisnika(korisnik, lozinka);
		
		boolean korisnikovZeton = false;
		long trenutnoVrijeme = new Date().getTime();
		
		if(korime.compareTo("") == 0) return Response.status(Response.Status.UNAUTHORIZED).entity("Neuspjela autentikacija!").build();
		
		if(z == null) return Response.status(Response.Status.UNAUTHORIZED).entity("Žeton ne pripada korisniku!").build();

		if (z.getKorisnik().compareTo(korisnik) == 0) {

			korisnikovZeton = true;

		}

		if (korisnikovZeton) {

			if (z.getVrijeme_valjanosti() > trenutnoVrijeme) {

				if (z.getStatus() == 1) {
					odgovor = Response.status(Response.Status.OK).entity("Žeton je aktivan!").build();
				}

				else {
					odgovor = Response.status(Response.Status.REQUEST_TIMEOUT)
							.entity("Vrijeme valjanosti žetona je isteklo jer isti više nije važeći!").build();
				}

			}

			else {
				
				if(z.getStatus() == 1) {
					zDao.deaktivirajZeton(z.getId());
				}
				
				odgovor = Response.status(Response.Status.REQUEST_TIMEOUT)
						.entity("Vrijeme valjanosti žetona je isteklo!").build();
			}

		}

		else {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Žeton ne pripada korisniku!").build();
		}

		return odgovor;
	}

	@DELETE
	@Path("{token}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deaktivirajZeton(@HeaderParam("korisnik") String korisnik, @HeaderParam("lozinka") String lozinka,
			@PathParam("token") String token) {

		Response odgovor = null;

		Zeton z = zDao.dohvatiZeton(Integer.parseInt(token));
		String korime = kDao.autenticirajKorisnika(korisnik, lozinka);
		
		boolean korisnikovZeton = false;
		long trenutnoVrijeme = new Date().getTime();
		
		if(korime.compareTo("") == 0) return Response.status(Response.Status.UNAUTHORIZED).entity("Neuspjela autentikacija!").build();
		
		if(z == null) return Response.status(Response.Status.UNAUTHORIZED).entity("Žeton ne pripada korisniku!").build();

		if (z.getKorisnik().compareTo(korisnik) == 0) {

			korisnikovZeton = true;

		}

		if (korisnikovZeton) {

			if (z.getVrijeme_valjanosti() > trenutnoVrijeme) {

				if (z.getStatus() == 1) {
					zDao.deaktivirajZeton(Integer.parseInt(token));
					odgovor = Response.status(Response.Status.OK).entity("Žeton je deaktiviran!").build();
				}

				else {
					odgovor = Response.status(Response.Status.REQUEST_TIMEOUT)
							.entity("Vrijeme valjanosti žetona je isteklo jer isti više nije važeći!").build();
				}

			}

			else {
				odgovor = Response.status(Response.Status.REQUEST_TIMEOUT)
						.entity("Vrijeme valjanosti žetona je isteklo!").build();
			}

		}

		else {
			odgovor = Response.status(Response.Status.UNAUTHORIZED).entity("Žeton ne pripada korisniku!").build();
		}

		return odgovor;
	}

	@DELETE
	@Path("korisnik/{korisnik}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response obrisiZetoneKorisnika(@HeaderParam("korisnik") String korimeAdmina,
			@HeaderParam("lozinka") String lozinka, @PathParam("korisnik") String korisnik) {

		Response odgovor = null;

		String korimeAutent = kDao.autenticirajKorisnika(korimeAdmina, lozinka);
		int brojZetona = zDao.dohvatiBrojAktivnihZetonaKorisnika(korisnik);
		String korime = kDao.provjeriAdmina(korimeAdmina);
		
		if(korimeAutent.compareTo("") == 0) return Response.status(Response.Status.UNAUTHORIZED).entity("Neuspjela autentikacija!").build();

		if (brojZetona < 1) {
			return Response.status(Response.Status.NOT_FOUND).entity("Korisnik nema aktivne žetone!").build();
		}

		else {

			if (korime.compareTo("") == 0) {
				odgovor = Response.status(Response.Status.UNAUTHORIZED)
						.entity("Korisnik nema ovlaštenje za brisanje/deaktiviranje žetona!").build();
			}

			else {
				zDao.deaktivirajSveZetone(korisnik);
				odgovor = Response.status(Response.Status.OK)
						.entity("Žetoni korisnika su uspješno deaktivirani/obrisani!").build();
			}

		}

		return odgovor;
	}

}
