package org.foi.nwtis.ftocko.aplikacija_3.podaci;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_3.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Korisnik;


/**
 * Klasa KorisniciDAO.
 */
public class KorisniciDAO {
	
	private String grupaAdmina = "";
	
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
			grupaAdmina = pbp.dajPostavku("sustav.administratori");
			Class.forName(pbp.getDriverDatabase(url));
			con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
		} catch (Exception e) {
			System.out.println("Problem kod konfiguracije BP!");
		}

		return con;
	}

	/**
	 * Metoda za autentikaciju korisnika.
	 *
	 * @return vraća korisničko ime autenticiranog korisnika
	 */
	public String autenticirajKorisnika(String korisnik, String lozinka) {

		Connection con = this.spojiDb();
		String upit = "SELECT korisnik FROM korisnici WHERE korisnik = ? AND lozinka = ?";
		String korime = "";
		ResultSet rs = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {

				ps.setString(1, korisnik);
				ps.setString(2, lozinka);
				
				rs = ps.executeQuery();
				
				while(rs.next()) {
					korime = rs.getString("korisnik");
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

		return korime;
	}
	
	public String provjeriAdmina(String korisnik) {

		Connection con = this.spojiDb();
		String upit = "SELECT k.korisnik FROM korisnici k, uloge u WHERE u.korisnik = ? AND u.grupa = ?";
		String korime = "";
		ResultSet rs = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {

				ps.setString(1, korisnik);
				ps.setString(2, grupaAdmina);
				
				rs = ps.executeQuery();
				
				while(rs.next()) {
					korime = rs.getString("korisnik");
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

		return korime;
	}
	
	public List<Korisnik> dohvatiSveKorisnike() {

		Connection con = this.spojiDb();
		String upit = "SELECT * FROM korisnici";
		List<Korisnik> korisnici = new ArrayList<Korisnik>();

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);ResultSet rs = ps.executeQuery();) {
				
				while(rs.next()) {
					
					Korisnik k = new Korisnik();
					k.setKorIme(rs.getString("korisnik"));
					k.setIme(rs.getString("ime"));
					k.setPrezime(rs.getString("prezime"));
					k.setLozinka(rs.getString("lozinka"));
					k.setEmail(rs.getString("email"));
					korisnici.add(k);
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

		return korisnici;
	}
	
	public int dodajKorisnika(Korisnik k) {

		Connection con = this.spojiDb();
		String insertUpit = "INSERT INTO korisnici (korisnik, ime, prezime, lozinka, email) VALUES (?,?,?,?,?)";

		int izvrsiUpit = 0;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(insertUpit);) {

				ps.setString(1, k.getKorIme());
				ps.setString(2, k.getIme());
				ps.setString(3, k.getPrezime());
				ps.setString(4, k.getLozinka());
				ps.setString(5, k.getEmail());
				
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
	
	public Korisnik dohvatiKorisnika(String korime) {

		Connection con = this.spojiDb();
		String upit = "SELECT * FROM korisnici WHERE korisnik = ?";
		ResultSet rs  = null;
		Korisnik k = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {
				
				ps.setString(1, korime);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					
					k = new Korisnik();
					k.setKorIme(rs.getString("korisnik"));
					k.setIme(rs.getString("ime"));
					k.setPrezime(rs.getString("prezime"));
					k.setLozinka(rs.getString("lozinka"));
					k.setEmail(rs.getString("email"));
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

		return k;
	}
	
	public List<Grupa> dohvatiGrupeKorisnika(String korime) {

		Connection con = this.spojiDb();
		String upit = "SELECT g.grupa, g.naziv FROM grupe g, korisnici k, uloge u WHERE u.korisnik = k.korisnik AND u.grupa = g.grupa AND k.korisnik = ?";
		ResultSet rs  = null;
		List<Grupa> grupe = null;

		if (con != null) {
			try (PreparedStatement ps = con.prepareStatement(upit);) {
				
				ps.setString(1, korime);
				rs = ps.executeQuery();
				
				grupe = new ArrayList<Grupa>();
				
				while(rs.next()) {
					
					Grupa g = new Grupa();
					g.setGrupa(rs.getString("grupa"));
					g.setNaziv(rs.getString("naziv"));
					grupe.add(g);
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

		return grupe;
	}

}
