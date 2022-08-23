package org.foi.nwtis.ftocko.aplikacija_3.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.foi.nwtis.ftocko.aplikacija_3.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

public class ZetoniDAO {

	private long vrijemeTrajanja = 0;

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
			vrijemeTrajanja = Integer.parseInt(pbp.dajPostavku("zeton.trajanje"));
			vrijemeTrajanja = vrijemeTrajanja * 60 * 1000;
		} catch (Exception e) {
			System.out.println("Problem kod konfiguracije BP ili dohvaćanja postavki!");
		}

		return con;
	}

	/**
	 * Metoda za kreiranje žetona.
	 *
	 * @return vraća žeton
	 */
	public Zeton kreirajZeton(String korisnik) {

		Connection con = this.spojiDb();
		String insertUpit = "INSERT INTO zetoni (korisnik, vrijeme_valjanosti, status) VALUES (?,?,?)";
		String selectUpit = "SELECT * FROM zetoni WHERE vrijeme_valjanosti = ?";

		int izvrsiUpit = 0;
		ResultSet rs = null;
		Zeton z = new Zeton();

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(insertUpit);
					PreparedStatement ps2 = con.prepareStatement(selectUpit);) {

				ps.setString(1, korisnik.trim());
				long trenutnoVrijeme = new Date().getTime();
				long vrijemeValjanosti = trenutnoVrijeme + vrijemeTrajanja;
				ps.setLong(2, vrijemeValjanosti);
				ps.setInt(3, 1);
				izvrsiUpit = ps.executeUpdate();

				ps2.setLong(1, vrijemeValjanosti);
				rs = ps2.executeQuery();

				while (rs.next()) {

					z.setId(rs.getInt("zeton_id"));
					z.setKorisnik(rs.getString("korisnik"));
					z.setVrijeme_valjanosti(vrijemeValjanosti);
					z.setStatus(rs.getInt("status"));

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

		return z;

	}

	public Zeton dohvatiZeton(int token) {

		Connection con = this.spojiDb();
		String selectUpit = "SELECT * FROM zetoni WHERE zeton_id = ?";

		ResultSet rs = null;
		Zeton z = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(selectUpit);) {

				ps.setInt(1, token);
				rs = ps.executeQuery();

				while (rs.next()) {

					z = new Zeton();
					z.setId(rs.getInt("zeton_id"));
					z.setKorisnik(rs.getString("korisnik"));
					z.setVrijeme_valjanosti(rs.getLong("vrijeme_valjanosti"));
					z.setStatus(rs.getInt("status"));

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

		return z;

	}

	public int deaktivirajZeton(int token) {

		Connection con = this.spojiDb();
		String updateUpit = "UPDATE zetoni SET status = 0 WHERE zeton_id = ?";

		int izvrsiUpit = 0;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(updateUpit);) {

				ps.setInt(1, token);
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
	
	public int dohvatiBrojAktivnihZetonaKorisnika(String korisnik) {
		
		Long trenutnoVrijeme = new Date().getTime();

		Connection con = this.spojiDb();
		String selectUpit = "SELECT COUNT(*) AS broj_zetona FROM zetoni WHERE korisnik = ? AND status = 1 AND vrijeme_valjanosti > ?";

		ResultSet rs = null;
		int brojZetona = 0;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(selectUpit);) {

				ps.setString(1, korisnik);
				ps.setLong(2, trenutnoVrijeme);
				rs = ps.executeQuery();

				while (rs.next()) {

					brojZetona = rs.getInt("broj_zetona");

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

		return brojZetona;

	}
	
	public int deaktivirajSveZetone(String korisnik) {

		Connection con = this.spojiDb();
		String updateUpit = "UPDATE zetoni SET status = 0 WHERE korisnik = ?";

		int izvrsiUpit = 0;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(updateUpit);) {

				ps.setString(1, korisnik);
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
