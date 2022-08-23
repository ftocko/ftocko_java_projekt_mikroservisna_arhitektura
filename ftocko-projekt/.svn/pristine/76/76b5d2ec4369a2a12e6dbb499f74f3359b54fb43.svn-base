package org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PostavkeBazaPodatakaTest {
	
	private PostavkeBazaPodataka pbp;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
		String nazivDatoteke = "NWTiS.db.config_1.xml";
		pbp = new PostavkeBazaPodataka(nazivDatoteke);
		try {
			pbp.ucitajKonfiguraciju();
			System.out.println(pbp.dajSvePostavke());
		} catch (NeispravnaKonfiguracija e) {
			fail("Nije uspjelo učitavanje!");
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetDriverDatabase() {
		
		String result = pbp.getDriverDatabase();
		assertEquals("org.hsqldb.jdbcDriver",result);
	}

	@Test
	void testGetDriverDatabaseString() {
		assertEquals("com.mysql.jdbc.Driver",pbp.getDriverDatabase("jdbc:mysql://localhost"));
	}

	@Test
	@Disabled
	void testGetDriversDatabase() {
		Properties result = pbp.getDriversDatabase();
		Properties expected = new Properties();
		expected.setProperty("jdbc.mysql", "com.mysql.jdbc.Driver");
		expected.setProperty("jdbc:hsqldb:hsql", "org.hsqldb.jdbcDriver");
		expected.setProperty("jdbc.derby", "org.apache.derby.jdbc.ClientDriver");
		assertEquals(expected,result);
	}

}
