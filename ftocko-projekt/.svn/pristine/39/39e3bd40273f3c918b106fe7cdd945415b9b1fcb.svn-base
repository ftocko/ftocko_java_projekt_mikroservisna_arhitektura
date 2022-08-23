package org.foi.nwtis.ftocko.aplikacija_3.rest;

import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_3.klijenti.Klijent;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.AerodromiDAO;
import org.foi.nwtis.ftocko.aplikacija_3.podaci.ProvjeraAutorizacije;
import org.foi.nwtis.podaci.Aerodrom;

import com.google.gson.Gson;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("serveri")
public class RestServeri {
	
	private ProvjeraAutorizacije pa = new ProvjeraAutorizacije();
	private Klijent k = new Klijent();
	private AerodromiDAO aDao = new AerodromiDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajStatus(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {
			
			String status = k.posaljiKomandu("STATUS");

			odgovor = Response.status(Response.Status.OK).entity(status).build();
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
	@Path("{komanda}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response izvrsiKomandu(@PathParam("komanda") String komanda, @HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {
			
			String odgovorServera = k.posaljiKomandu(komanda);
			
			if(odgovorServera.contains("OK")) {
				odgovor = Response.status(Response.Status.OK).build();
			}
			
			else {
				odgovor = Response.status(Response.Status.BAD_REQUEST).entity(odgovorServera).build();
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
	
	@POST
	@Path("LOAD")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response posaljiKolekcijuAerodroma(@HeaderParam("korisnik") String korisnik, @HeaderParam("zeton") String zeton) {

		Response odgovor = null;

		int statusniKod = pa.autorizirajKorisnika(korisnik, zeton);

		if (statusniKod == 200) {
			
			List<Aerodrom> aerodromi = aDao.dohvatiSveAerodrome();
			
			Gson gson = new Gson();
			String kolekcijaAerodroma = gson.toJson(aerodromi);
			String komanda = "LOAD "+kolekcijaAerodroma;
			
			String odgovorServera = k.posaljiKomandu(komanda);
			
			if(odgovorServera.contains("OK")) {
				
				String [] p = odgovorServera.split(" ");
				odgovorServera = "";
				odgovorServera = p[1];
				
				odgovor = Response.status(Response.Status.OK).entity(odgovorServera).build();
			}
			
			else {
				odgovor = Response.status(Response.Status.CONFLICT).entity(odgovorServera).build();
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
