package org.foi.nwtis.ftocko.aplikacija_2.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_2.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

/**
 * Klasa AerodromiProblemiDAO.
 */
public class AerodromiProblemiDAO {
	
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
	 * Metoda za povezivanje na bazu podataka s admin korisnikom.
	 *
	 * @return vraća konekciju na bazu
	 */
	private Connection spojiDbAdmin() {

		Connection con = null;

		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) SlusacAplikacije.sc.getAttribute("Postavke");
			String url = pbp.getServerDatabase() + pbp.getUserDatabase();
			String bpkorisnik = pbp.getAdminUsername();
			String bplozinka = pbp.getAdminPassword();
			Class.forName(pbp.getDriverDatabase(url));
			con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
		} catch (Exception e) {
			System.out.println("Problem kod konfiguracije BP!");
		}

		return con;
	}

	/**
	 * Metoda za spremanje problema u bazu.
	 *
	 * @param ap problem
	 * @return vraća uspješnost spremanja
	 */
	public int spremiProblemAerodroma(AerodromProblemi ap) {

		Connection con = this.spojiDb();
		String insertUpit = "INSERT INTO AERODROMI_PROBLEMI (ident,description,"+stored+") VALUES (?,?,now())";

		int izvrsiUpit = 0;

		// System.out.println(insertUpit);

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(insertUpit);) {

				ps.setString(1, ap.getIdent());
				ps.setString(2, ap.getOpis());

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
	
	/**
	 * Metoda vraća datum i vrijeme iz timestampa.
	 *
	 * @param t timestamp
	 * @return vraća string format datuma i vremena
	 */
	private String dajStored(Timestamp t) {
		
		Date datum = new Date(t.getTime());
		Format f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String stored = f.format(datum);
		
		return stored;
		
	}
	
	/**
	 * Metoda za dohvaćanje svih problema aerodroma.
	 *
	 * @return vraća kolekciju problema
	 */
	public List<AerodromProblemi> dohvatiSveProbleme() {

		Connection con = this.spojiDb();
		String upit = "SELECT ident, description, "+stored+" FROM AERODROMI_PROBLEMI";
		List<AerodromProblemi> aProblemi = new ArrayList<AerodromProblemi>();

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);ResultSet rs = ps.executeQuery();) {
				
				while (rs.next()) {
					AerodromProblemi ap = new AerodromProblemi();
					String ident = rs.getString("ident");
					String description = rs.getString("description");
					Timestamp ts = rs.getTimestamp("stored");

					ap.setIdent(ident);
					ap.setOpis(description);
					ap.setStored(dajStored(ts));

					aProblemi.add(ap);
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

		return aProblemi;
	}
	
	/**
	 * Metoda za vraćanje problema iz baze s obzirom na limit i offset.
	 *
	 * @param limit broj zapisa za dohvat
	 * @param offset broj zapisa za preskakanje
	 * @return vraća kolekciju problema
	 */
	public List<AerodromProblemi> dohvatiSveProbleme(int limit, int offset) {

		Connection con = this.spojiDb();
		String upit = "SELECT ident, description, "+stored+" FROM AERODROMI_PROBLEMI LIMIT ? OFFSET ?";
		List<AerodromProblemi> aProblemi = new ArrayList<AerodromProblemi>();
		ResultSet rs = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {
				
				ps.setInt(1, limit);
				ps.setInt(2, offset);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					AerodromProblemi ap = new AerodromProblemi();
					String ident = rs.getString("ident");
					String description = rs.getString("description");
					Timestamp ts = rs.getTimestamp("stored");

					ap.setIdent(ident);
					ap.setOpis(description);
					ap.setStored(dajStored(ts));

					aProblemi.add(ap);
				}

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

		return aProblemi;
	}
	
	/**
	 * Metoda za dohvaćanje problema za zadani id aerodroma.
	 *
	 * @param icao identifikator aerodroma
	 * @return vraća kolekciju problema
	 */
	public List<AerodromProblemi> dohvatiProbleme(String icao) {

		Connection con = this.spojiDb();
		String upit = "SELECT ident, description, "+stored+" FROM AERODROMI_PROBLEMI WHERE ident = ?";
		List<AerodromProblemi> aProblemi = new ArrayList<AerodromProblemi>();
		ResultSet rs = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {
				
				ps.setString(1, icao);
				rs = ps.executeQuery();

				while (rs.next()) {
					AerodromProblemi ap = new AerodromProblemi();
					String ident = rs.getString("ident");
					String description = rs.getString("description");
					Timestamp ts = rs.getTimestamp("stored");

					ap.setIdent(ident);
					ap.setOpis(description);
					ap.setStored(dajStored(ts));

					aProblemi.add(ap);
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

		return aProblemi;
	}
	
	/**
	 * Metoda za brisanje problema za zadani aerodrom.
	 *
	 * @param icao identifikator aerodroma
	 * @return vraća uspješnost brisanja
	 */
	public int obrisiProbleme(String icao) {
		
		Connection con = this.spojiDbAdmin();
		String upit = "DELETE FROM AERODROMI_PROBLEMI WHERE ident = ?";
		int vraceno = 0;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {
				
				ps.setString(1, icao);
				vraceno = ps.executeUpdate();
				
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

		return vraceno;
	}

}
