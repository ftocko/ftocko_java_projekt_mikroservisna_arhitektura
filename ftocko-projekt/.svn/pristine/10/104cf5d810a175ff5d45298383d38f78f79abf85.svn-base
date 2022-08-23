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
import org.foi.nwtis.rest.podaci.AvionLeti;

/**
 * Klasa AerodromiDolasciDAO.
 */
public class AerodromiDolasciDAO {
	
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
	 * Metoda vraća sve dolaske za zadani aerodrom na određeni dan.
	 *
	 * @param icao identifikator aerodroma
	 * @param t1 pocetno vrijeme dana
	 * @param t2 zavrsno vrijeme dana
	 * @return vraća kolekciju dolazaka
	 */
	public List<AvionLeti> dajSveDolaske(String icao, int t1, int t2) {

		Connection con = this.spojiDb();
		String upit = "SELECT * FROM AERODROMI_DOLASCI WHERE estarrivalairport = ? AND lastseen BETWEEN ? AND ? ORDER BY lastSeen ASC";
		List<AvionLeti> dolasci = new ArrayList<AvionLeti>();
		ResultSet rs = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {

				ps.setString(1, icao);
				ps.setInt(2, t1);
				ps.setInt(3, t2);

				rs = ps.executeQuery();

				while (rs.next()) {

					AvionLeti a = new AvionLeti();
					a.setIcao24(rs.getString("icao24"));
					a.setFirstSeen(rs.getInt("firstseen"));
					a.setEstDepartureAirport(rs.getString("estdepartureairport"));
					a.setLastSeen(rs.getInt("lastseen"));
					a.setEstArrivalAirport(rs.getString("estarrivalairport"));
					a.setCallsign(rs.getString("callsign"));
					a.setEstDepartureAirportHorizDistance(rs.getInt("estdepartureairporthorizdistance"));
					a.setEstDepartureAirportVertDistance(rs.getInt("estdepartureairportvertdistance"));
					a.setEstArrivalAirportHorizDistance(rs.getInt("estarrivalairporthorizdistance"));
					a.setEstArrivalAirportVertDistance(rs.getInt("estarrivalairportvertdistance"));
					a.setDepartureAirportCandidatesCount(rs.getInt("departureairportcandidatescount"));
					a.setArrivalAirportCandidatesCount(rs.getInt("arrivalairportcandidatescount"));

					dolasci.add(a);

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

		return dolasci;

	}
	
	/**
	 * Metoda sprema sve dolaske u bazu.
	 *
	 * @param lista kolekcija dolazaka za spremanje u bazu
	 * @return vraća uspješnost spremanja
	 */
	public int spremiSveDolaskeAerodroma(List<AvionLeti> lista) {

		Connection con = this.spojiDb();
		String upit1 = "INSERT INTO AERODROMI_DOLASCI (icao24, firstseen, estdepartureairport, lastseen, estarrivalairport, ";
		String upit2 = "callsign, estdepartureairporthorizdistance, estdepartureairportvertdistance, estarrivalairporthorizdistance, ";
		String upit3 = "estarrivalairportvertdistance, departureairportcandidatescount, arrivalairportcandidatescount, "+stored+") ";
		String upit4 = "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
		String insertUpit = upit1 + upit2 + upit3 + upit4;
		int izvrsenUpit = 0;
		PreparedStatement ps = null;

		if (con != null) {
			
			for (AvionLeti al : lista) {
				
				try {

					ps = null;
					ps = con.prepareStatement(insertUpit);

					if (al.getEstDepartureAirport() != null) {
						ps.setString(1, al.getIcao24());
						ps.setInt(2, al.getFirstSeen());
						ps.setString(3, al.getEstDepartureAirport());
						ps.setInt(4, al.getLastSeen());
						ps.setString(5, al.getEstArrivalAirport());
						ps.setString(6, al.getCallsign());
						ps.setInt(7, al.getEstDepartureAirportHorizDistance());
						ps.setInt(8, al.getEstDepartureAirportVertDistance());
						ps.setInt(9, al.getEstArrivalAirportHorizDistance());
						ps.setInt(10, al.getEstArrivalAirportVertDistance());
						ps.setInt(11, al.getDepartureAirportCandidatesCount());
						ps.setInt(12, al.getArrivalAirportCandidatesCount());

						izvrsenUpit = ps.executeUpdate();
						ps.close();
					}
					

				} catch (SQLException e) {
					System.out.println("Zapis u tablici dolasci već postoji!");
					try {
						ps.close();
					} catch (SQLException ex) {
						System.out.println("Problem kod zatvaranja instrukcije!");
					}
				}
				
			}

			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Problem kod zatvaranja konekcije!");
			}
			
		}

		else {
			System.out.println("Problem kod konekcije na BP!");
		}

		return izvrsenUpit;
	}
}
