package org.foi.nwtis.ftocko.aplikacija_3.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_3.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 * Klasa AerodromiPraceniDAO.
 */
public class AerodromiPraceniDAO {
	
	/** atribut stored za insert upit */
	private String stored = "stored";

	/**
	 * Metoda za povezivanje na bazu podataka.
	 *
	 * @return vraća konekciju na bazu
	 */
	private Connection spojiDb() {

		Connection con = null;

		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) SlusacAplikacije.sc.getAttribute("Postavke");
			String url = pbp.getServerDatabase() + pbp.getUserDatabase();
			String bpkorisnik = pbp.getUserUsername();
			String bplozinka = pbp.getUserPassword();
			Class.forName(pbp.getDriverDatabase(url));
			con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
			
			if(pbp.getServerDatabase().contains("mysql")){
				stored = "`stored`";
			}
		} catch (Exception e) {
			System.out.println("Problem kod konfiguracije BP!");
		}

		return con;
	}

	/**
	 * Metoda dohvaća sve praćene aerodrome iz baze.
	 *
	 * @return vraća kolekciju aerodroma
	 */
	public List<Aerodrom> dohvatiPraceneAerodrome() {

		Connection con = this.spojiDb();
		String upitPraceniAir1 = "SELECT a.ident, a.name, a.iso_country, a.coordinates ";
		String upitPraceniAir2 = "FROM airports a, AERODROMI_PRACENI ap WHERE ap.ident  = a.ident";
		String upitPraceniAir = upitPraceniAir1 + upitPraceniAir2;
		ResultSet rs = null;

		List<Aerodrom> aerodromi = new ArrayList<Aerodrom>();

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upitPraceniAir);) {

				rs = ps.executeQuery();

				while (rs.next()) {
					Aerodrom a = new Aerodrom();
					String icao = rs.getString("ident");
					String naziv = rs.getString("name");
					String drzava = rs.getString("iso_country");
					String lokacija = rs.getString("coordinates");
					String[] l = lokacija.split(",");

					a.setIcao(icao);
					a.setNaziv(naziv);
					a.setDrzava(drzava);
					a.setLokacija(new Lokacija(l[0], l[1]));

					aerodromi.add(a);
				}

				rs.close();
				con.close();

			} catch (SQLException ex) {
				System.out.println("Problem kod SQL konfiguracije!");
				try {
					rs.close();
					con.close();
				} catch (SQLException e) {
					System.out.println("Problem kod zatvaranja konekcije!");
				}
			}
		}

		else {
			System.out.println("Problem kod konekcije na BP!");
		}

		return aerodromi;
	}
	
	public boolean provjeriPostojanjePracenogAerodroma(Aerodrom aerodrom) {
		
		List<Aerodrom> aerodromi = dohvatiPraceneAerodrome();

		boolean postoji = false;
		
		for(Aerodrom a:aerodromi) {
			
			if(aerodrom.getIcao().compareTo(a.getIcao()) == 0) {
				postoji = true;
				break;
			}
			
		}
		
		return postoji;
		
	}

	/**
	 * Metoda za dodavanje novog praćenog aerodroma u bazu.
	 *
	 * @param a aerodrom za dodavanje
	 * @return vraća uspješnost spremanja
	 */
	public int dodajPraceniAerodrom(Aerodrom a) {

		Connection con = this.spojiDb();
		String insertUpit = "INSERT INTO AERODROMI_PRACENI (ident, "+stored+") VALUES (?, ?)";
		int izvrsiUpit = 0;
		
		boolean postojiAerodrom = provjeriPostojanjePracenogAerodroma(a);
		
		if(postojiAerodrom) {
			return izvrsiUpit;
		}

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(insertUpit);) {

				ps.setString(1, a.getIcao());
				long timestamp = new Date().getTime();
				ps.setTimestamp(2, new Timestamp(timestamp));
				izvrsiUpit = ps.executeUpdate();

				con.close();

			} catch (SQLException ex) {
				System.out.println("Problem kod SQL konfiguracije!");
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println("Problem kod zatvaranja konekcije!");
				}
			}
		}

		else {
			System.out.println("Problem kod konekcije na BP!");
		}

		return izvrsiUpit;
	}

}
