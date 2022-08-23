package org.foi.nwtis.ftocko.aplikacija_3.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_3.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 * Klasa AerodromiDAO.
 */
public class AerodromiDAO {
	
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
		} catch (Exception e) {
			System.out.println("Problem kod konfiguracije BP!");
		}

		return con;
	}

	/**
	 * Metoda za dohvaćanje svih aerodroma iz baze.
	 *
	 * @return vraća kolekciju aerodroma
	 */
	public List<Aerodrom> dohvatiSveAerodrome() {

		Connection con = this.spojiDb();
		String upit = "SELECT ident, name, iso_country, coordinates FROM airports";
		List<Aerodrom> aerodromi = new ArrayList<Aerodrom>();

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit); ResultSet rs = ps.executeQuery();) {

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
					a.setLokacija(new Lokacija(l[1], l[0]));

					aerodromi.add(a);
				}

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

		return aerodromi;
	}

	/**
	 * Metoda dohvaća aerodrom za zadani identifikator aerodroma.
	 *
	 * @param ident identifikator aerodroma
	 * @return vraća aerodrom
	 */
	public Aerodrom dohvatiAerodrom(String ident) {

		String upit = "SELECT ident, name, iso_country, coordinates FROM airports where ident = ?";
		Aerodrom a = null;
		Connection con = this.spojiDb();
		ResultSet rs = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit)) {

				ps.setString(1, ident);
				rs = ps.executeQuery();

				while (rs.next()) {

					a = new Aerodrom();
					String icao = rs.getString("ident");
					String naziv = rs.getString("name");
					String drzava = rs.getString("iso_country");
					String lokacija = rs.getString("coordinates");
					String[] l = lokacija.split(",");

					a.setIcao(icao);
					a.setNaziv(naziv);
					a.setDrzava(drzava);
					a.setLokacija(new Lokacija(l[1], l[0]));

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

		return a;
	}

}
