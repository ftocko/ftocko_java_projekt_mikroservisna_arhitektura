package org.foi.nwtis.ftocko.aplikacija_2.dretve;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_2.podaci.AerodromProblemi;
import org.foi.nwtis.ftocko.aplikacija_2.podaci.AerodromiDolasciDAO;
import org.foi.nwtis.ftocko.aplikacija_2.podaci.AerodromiPolasciDAO;
import org.foi.nwtis.ftocko.aplikacija_2.podaci.AerodromiPraceniDAO;
import org.foi.nwtis.ftocko.aplikacija_2.podaci.AerodromiProblemiDAO;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.klijenti.OSKlijent;
import org.foi.nwtis.rest.podaci.AvionLeti;

import jakarta.servlet.ServletContext;

/**
 * Klasa PreuzimanjeRasporedaAerodroma.
 */
public class PreuzimanjeRasporedaAerodroma extends Thread {

	/** pocetno vrijeme preuzimanja */
	private int preuzimanjeOd;

	/** zavrsno vrijeme preuzimanja */
	private int preuzimanjeDo;

	/** vrijeme preuzimanja za ciklus */
	private int preuzimanjeVrijeme;

	/** iznos pauze za spavanje dretve */
	private long preuzimanjePauza;

	/** odmak od stvarnog preuzimanja */
	private int preuzimanjeOdmak;

	/** vrijeme trajanja ciklusa */
	private long vrijemeCiklusa;

	/** broj ciklusa nakon kojih se radi korekcija */
	private int korekcijaCiklusa;

	/** The vrijeme obrade. */
	private int vrijemeObrade;

	/** korisničko ime za servis */
	private String korime;

	/** lozinka za servis */
	private String lozinka;

	/** klijent za usluge servisa */
	private OSKlijent osKlijent;

	/** stvarni brojac ciklusa */
	private int stvarniBrojac = 0;

	/** virtualni brojac ciklusa */
	private int virtualniBrojac = 0;

	/** DAO klasa za spremanje polaska */
	private AerodromiPolasciDAO apDao = new AerodromiPolasciDAO();

	/** DAO klasa za spremanje dolaska */
	private AerodromiDolasciDAO adDao = new AerodromiDolasciDAO();

	/** DAO klasa za spremanje problema */
	private AerodromiProblemiDAO aprDao = new AerodromiProblemiDAO();

	/** servlet kontekst aplikacije */
	private ServletContext sc = null;
	
	/** zastavica rada dretve */
	private boolean radi = true;

	/**
	 * Instanciranje novog objekta klase PreuzimanjeRasporedaAerodroma.
	 *
	 * @param context kontekst aplikacije
	 */
	public PreuzimanjeRasporedaAerodroma(ServletContext context) {
		this.sc = context;
	}

	/**
	 * Metoda vraća timestamp.
	 *
	 * @param preuzimanje datum preuzimanja u string zapisu
	 * @return vraća timestamp vrijednost
	 */
	private int vratiTimestamp(String preuzimanje) {

		preuzimanje += " 00:00:00";
		long timestamp = 0;

		try {
			Date datum = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(preuzimanje);
			timestamp = datum.getTime() / 1000;
		} catch (Exception e) {
			System.out.println("Problem kod parsiranja!");
		}
		return (int) timestamp;
	}

	/**
	 * Metoda vraća timestamp.
	 *
	 * @return vraća vrijednost trenutnog vremena sustava u timestamp obliku
	 */
	private long vratiTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * Metoda služi za spavanje dretve prema zadanim postavkama.
	 */
	private void odspavaj() {

		try {
			Thread.sleep(preuzimanjePauza);
		} catch (InterruptedException e) {
			radi = false;
			System.out.println("Došlo je do prekida dretve!");
		}

	}

	/**
	 * Metoda služi za spavanje dretve za zadano vrijeme u argumentu.
	 *
	 * @param vrijeme vrijeme u milisekundama
	 */
	private void odspavaj(long vrijeme) {

		try {
			Thread.sleep(vrijeme);
		} catch (InterruptedException e) {
			radi = false;
			System.out.println("Došlo je do prekida dretve!");
		}

	}

	/**
	 * Metoda izračunava vrijeme spavanja nakon svakog ciklusa.
	 *
	 * @param efektivnoVrij vrijeme trajanja obrade u ciklusu
	 * @return vraća iznos vremena spavanja
	 */
	private long izracunajVrijemeSpavanja(long efektivnoVrij) {

		long vrijSpavanja = 0;

		if (efektivnoVrij < this.vrijemeCiklusa) {
			vrijSpavanja = this.vrijemeCiklusa - efektivnoVrij;
		}

		else if (efektivnoVrij > this.vrijemeCiklusa) {
			int n = 2;
			while (n * this.vrijemeCiklusa < efektivnoVrij) {
				n++;
			}
			vrijSpavanja = (n * this.vrijemeCiklusa) - efektivnoVrij;
			this.stvarniBrojac++;
			this.virtualniBrojac += n;
		}

		return vrijSpavanja;

	}

	/**
	 * Metoda izračunava vrijeme spavanja za korekciju ciklusa.
	 *
	 * @param pocetakPrvogCiklusa vrijeme pocetka prvog ciklusa
	 * @return vraća izračunato vrijeme spavanja
	 */
	private long izracunajVrijemeSpavanjaKorekcija(long pocetakPrvogCiklusa) {

		long vrijSpavanja = pocetakPrvogCiklusa + (this.virtualniBrojac * this.vrijemeCiklusa);

		return vrijSpavanja;

	}

	/**
	 * Metoda poziva spremanje liste polazaka ili dolazaka u bazu.
	 *
	 * @param lista    lista polazaka ili dolazaka
	 * @param raspored mogu biti polasci/dolasci
	 */
	private void spremiRasporedeAir(List<AvionLeti> lista, String raspored) {

		if (raspored.equals("polasci")) {
			apDao.spremiSvePolaskeAerodroma(lista);
		} else {
			adDao.spremiSveDolaskeAerodroma(lista);
		}
	}

	/**
	 * Metoda poziva spremanje problema u bazu.
	 *
	 * @param icao   identifikator aerodroma
	 * @param poruka opis problema
	 */
	private void spremiProblemAir(String icao, String poruka) {

		AerodromProblemi ap = new AerodromProblemi();
		ap.setIdent(icao);
		ap.setOpis(poruka);
		aprDao.spremiProblemAerodroma(ap);
	}

	/**
	 * Metoda služi za inicijalizaciju postavki dretve i pokretanje dretve.
	 */
	@Override
	public synchronized void start() {

		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) sc.getAttribute("Postavke");
			String sPreuzimanjeOd = pbp.dajPostavku("preuzimanje.od");
			String sPreuzimanjeDo = pbp.dajPostavku("preuzimanje.do");
			this.preuzimanjeOd = vratiTimestamp(sPreuzimanjeOd);
			this.preuzimanjeDo = vratiTimestamp(sPreuzimanjeDo);
			int preuzimanjeVrij = Integer.parseInt(pbp.dajPostavku("preuzimanje.vrijeme"));
			this.preuzimanjeVrijeme = preuzimanjeVrij * 60 * 60;
			this.preuzimanjePauza = Long.parseLong(pbp.dajPostavku("preuzimanje.pauza"));
			this.preuzimanjeOdmak = Integer.parseInt(pbp.dajPostavku("preuzimanje.odmak"));
			this.preuzimanjeOdmak = this.preuzimanjeOdmak * 24 * 60 * 60;
			this.vrijemeCiklusa = Integer.parseInt(pbp.dajPostavku("ciklus.vrijeme"));
			this.vrijemeCiklusa = this.vrijemeCiklusa * 1000;
			this.korekcijaCiklusa = Integer.parseInt(pbp.dajPostavku("ciklus.korekcija"));
			this.vrijemeObrade = this.preuzimanjeOd;
			this.korime = pbp.dajPostavku("OpenSkyNetwork.korisnik");
			this.lozinka = pbp.dajPostavku("OpenSkyNetwork.lozinka");
			this.osKlijent = new OSKlijent(korime, lozinka);

		}

		catch (Exception e) {
			System.out.println("Problem kod postavki!");
			return;
		}

		super.start();
	}

	/**
	 * Metoda pokreće dretvu.
	 */
	@Override
	public void run() {

		List<Aerodrom> aerodromi = null;
		AerodromiPraceniDAO apraDao = new AerodromiPraceniDAO();
		aerodromi = apraDao.dohvatiPraceneAerodrome();
		int prethodnoVrijeme = 0;
		long pocetnoVrijeme = 0;
		long zavrsnoVrijeme = 0;
		long efektivnoVrijeme = 0;
		long vrijemeSpavanja = 0;
		long pocetakPrvogCiklusa = 0;
		long jedanDan = 24 * 60 * 60 * 1000;

		if (aerodromi != null) {

			while (vrijemeObrade < preuzimanjeDo && radi) {

				prethodnoVrijeme = vrijemeObrade;
				vrijemeObrade += preuzimanjeVrijeme;

				this.stvarniBrojac++;
				this.virtualniBrojac++;

				pocetnoVrijeme = vratiTimestamp();

				if (this.stvarniBrojac == 1) {
					pocetakPrvogCiklusa = pocetnoVrijeme;
				}

				System.out.println("------ " + prethodnoVrijeme + " - " + vrijemeObrade + " ------");

				for (Aerodrom a : aerodromi) {
					System.out.println("Polasci s aerodroma: " + a.getIcao());
					List<AvionLeti> avioniPolasci;

					try {
						avioniPolasci = osKlijent.getDepartures(a.getIcao(), prethodnoVrijeme, vrijemeObrade);
						if (avioniPolasci != null) {
							System.out.println("Broj letova: " + avioniPolasci.size());
							for (AvionLeti avion : avioniPolasci) {

								System.out.println(
										"Avion: " + avion.getIcao24() + " Odredište: " + avion.getEstArrivalAirport());
							}

							spremiRasporedeAir(avioniPolasci, "polasci");
						}
					} catch (NwtisRestIznimka e) {
						spremiProblemAir(a.getIcao(), e.getMessage());
					}

					System.out.println("Dolasci na aerodrom: " + a.getIcao());
					List<AvionLeti> avioniDolasci;
					try {
						avioniDolasci = osKlijent.getArrivals(a.getIcao(), prethodnoVrijeme, vrijemeObrade);
						if (avioniDolasci != null) {
							System.out.println("Broj letova: " + avioniDolasci.size());
							for (AvionLeti avion : avioniDolasci) {

								System.out.println("Avion: " + avion.getIcao24() + " Odredište: "
										+ avion.getEstDepartureAirport());
							}

							spremiRasporedeAir(avioniDolasci, "dolasci");
						}
					} catch (NwtisRestIznimka e) {
						spremiProblemAir(a.getIcao(), e.getMessage());

					}

					odspavaj();
				}

				zavrsnoVrijeme = vratiTimestamp();
				efektivnoVrijeme = zavrsnoVrijeme - pocetnoVrijeme;
				System.out.println("Efektivno vrijeme ciklusa iznosi: " + efektivnoVrijeme);

				long trVrijL = new Date().getTime() / 1000;
				int trVrij = (int) trVrijL;

				if (vrijemeObrade > trVrij - this.preuzimanjeOdmak) {
					this.virtualniBrojac += jedanDan / this.vrijemeCiklusa;
					this.stvarniBrojac++;
					odspavaj(jedanDan);
				}

				if (this.stvarniBrojac % korekcijaCiklusa == 0) {
					vrijemeSpavanja = izracunajVrijemeSpavanjaKorekcija(pocetakPrvogCiklusa);
				}

				else {
					vrijemeSpavanja = izracunajVrijemeSpavanja(efektivnoVrijeme);
				}

				System.out.println("Vrijeme spavanja iznosi: " + vrijemeSpavanja);

				odspavaj(vrijemeSpavanja);

			}

		}

		else {
			System.out.println("Ne postoje praćeni aerodromi!");
		}

		System.out.println("Preuzimanje završeno!");

	}

	/**
	 * Metoda za prekidanje dretve.
	 */
	@Override
	public void interrupt() {
		radi = false;
		super.interrupt();
	}

}
