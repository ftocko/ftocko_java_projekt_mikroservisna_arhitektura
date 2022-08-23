package org.foi.nwtis.ftocko.aplikacija_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.Konfiguracija;
import org.foi.nwtis.podaci.Aerodrom;

public class Server {

	/** broj porta. */
	private int port;

	/** maksimalni broj čekača. */
	private int maksCekaca;

	/** maksimalni broj dretvi. */
	private int maksDretvi;

	/** veza na mrežnu utičnicu. */
	private Socket veza = null;

	/** status poslužitelja (u početku u stanju hibernacije (0)). */
	protected volatile int status = 0;

	/** kolekcija aerodroma. */
	protected volatile CopyOnWriteArrayList<Aerodrom> aerodromi = new CopyOnWriteArrayList<Aerodrom>();

	/**
	 * Konstruktor za instanciranje objekta klase Server.
	 *
	 * @param port       broj porta
	 * @param maksCekaca maksimalni broj čekača
	 */
	public Server(int port, int maksCekaca, int maksDretvi) {
		super();
		this.port = port;
		this.maksCekaca = maksCekaca;
		this.maksDretvi = maksDretvi;
	}

	public static void main(String[] args) {

		PripremaServera priprema = new PripremaServera();
		Konfiguracija konfig = null;
		Server server = null;

		if (args.length < 1) {
			priprema.ispis("Parametar mora biti naziv konfiguracijske datoteke!");
			System.exit(0);
		}

		konfig = priprema.ucitajKonfiguraciju(args[0]);

		try {
			int port = Integer.parseInt(konfig.dajPostavku("port"));
			int maksCekaca = Integer.parseInt(konfig.dajPostavku("maks.cekaca"));
			int maksDretvi = Integer.parseInt(konfig.dajPostavku("maks.dretvi"));
			server = new Server(port, maksCekaca, maksDretvi);
		} catch (Exception e) {
			priprema.ispis("Problem kod dohvaćanja postavki!");
			System.exit(0);
		}

		server.radiServer(priprema, konfig);

	}

	/**
	 * Pokretanje i životni ciklus servera.
	 *
	 * @param priprema objekt klase PripremaServera
	 * @param konfig   objekt klase Konfiguracija
	 */
	private void radiServer(PripremaServera priprema, Konfiguracija konfig) {

		try (ServerSocket ss = new ServerSocket(this.port, this.maksCekaca)) {
			priprema.ispis("Server je pokrenut na portu " + this.port + "...");
			while (true) {
				
				this.veza = ss.accept();
				
				DretvaServera.brojacDretvi++;
				
				if (DretvaServera.brojacDretvi < maksDretvi) {
					
					DretvaServera dretvaZahtjeva = new DretvaServera(veza, priprema, this, ss);
					dretvaZahtjeva.start();
				}

			}

		} catch (IOException ex) {
			
			if(ex.getMessage().contains("Address already in use")) {
				priprema.ispis("Nije moguće pokrenuti server, jer je port već zauzet!");
			}
			
			else {
				priprema.ispis("Poslužitelj je uspješno zaustavljen!");
			}
		}

	}

}
