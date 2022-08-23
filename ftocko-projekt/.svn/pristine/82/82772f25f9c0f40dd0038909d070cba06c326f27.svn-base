package org.foi.nwtis.ftocko.aplikacija_6.slusaci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_6.jms.RepozitorijJmsPoruka;
import org.foi.nwtis.ftocko.aplikacija_6.jpa.criteriaapi.KorisniciJpa;
import org.foi.nwtis.ftocko.jms.SMeteoPodaci;
import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.KonfiguracijaBP;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Klasa SlusacAplikacije.
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {

	/** servlet kontekst */
	public static ServletContext sc = null;

	public SlusacAplikacije() {

	}

	@EJB
	KorisniciJpa korisniciJpa;

	@EJB
	RepozitorijJmsPoruka rjp;

	/**
	 * Metoda koja obavlja učitavanje postavki.
	 *
	 * @param sce objekt klase ServletContextEvent
	 */
	private void ucitajPostavke(ServletContextEvent sce) {

		String nazivDatoteke = "";
		ServletContext context = sce.getServletContext();
		sc = context;

		try {
			nazivDatoteke = context.getInitParameter("konfiguracija");
		} catch (Exception e) {
			System.out.println("Nije moguće dohvatiti inicijalni parametar konteksta!");
		}

		String putanja = context.getRealPath("/WEB-INF");
		nazivDatoteke = putanja + File.separator + nazivDatoteke;

		KonfiguracijaBP konfig = new PostavkeBazaPodataka(nazivDatoteke);

		try {
			konfig.ucitajKonfiguraciju();
		} catch (NeispravnaKonfiguracija e) {
			System.out.println("Neispravna konfiguracija!");
		}

		System.out.println("Konfiguracija učitana!");
		context.setAttribute("Postavke", konfig);

	}

	/**
	 * Metoda koja obavlja brisanje postavki.
	 *
	 * @param sce objekt klase ServletContextEvent
	 */
	private void obrisiPostavke(ServletContextEvent sce) {

		ServletContext context = sce.getServletContext();
		context.removeAttribute("Postavke");
		System.out.println("Postavke obrisane!");

	}

	private void serijalizirajJmsPoruke(ServletContextEvent sce) {

		String nazivDatoteke = "";

		try {
			KonfiguracijaBP konfig = (KonfiguracijaBP) sce.getServletContext().getAttribute("Postavke");
			nazivDatoteke = konfig.dajPostavku("datoteka.jms");
		}

		catch (Exception e) {
			System.out.println("Problem kod postavki!");
		}

		try (FileOutputStream out = new FileOutputStream(nazivDatoteke, false);
				ObjectOutputStream oos = new ObjectOutputStream(out);) {

			oos.writeObject(rjp.poruke);

			System.out.println("JMS poruke su uspješno serijalizirane na vanjski spremnik, datotekaJms.bin!");

		}

		catch (Exception e) {
			System.out.println("Problem kod serijalizacije na vanjski spremnik!");
		}

	}

	private void deserijalizirajJmsPoruke(ServletContextEvent sce) {

		String nazivDatoteke = "";

		try {
			KonfiguracijaBP konfig = (KonfiguracijaBP) sce.getServletContext().getAttribute("Postavke");
			nazivDatoteke = konfig.dajPostavku("datoteka.jms");
		}

		catch (Exception e) {
			System.out.println("Problem kod postavki!");
		}

		try (FileInputStream in = new FileInputStream(nazivDatoteke);
				ObjectInputStream ois = new ObjectInputStream(in);) {

			rjp.poruke = null;
			rjp.poruke = (List<Object>) ois.readObject();

			System.out.println("JMS poruke su uspješno deserijalizirane s vanjskog spremnika, datotekaJms.bin!");

		}

		catch (Exception e) {
			System.out.println("Problem kod deserijalizacije s vanjskog spremnika!");
		}

	}

	/**
	 * Metoda koja se poziva kod deploya aplikacije.
	 *
	 * @param sce objekt klase ServletContextEvent
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ucitajPostavke(sce);

		deserijalizirajJmsPoruke(sce);
		
		/*for(Object o:rjp.poruke) {
		if(o instanceof SMeteoPodaci) {
			SMeteoPodaci smp = (SMeteoPodaci) o;
			System.out.println(smp.getIcao()+" "+smp.getTemperatureValue());
		}
		}*/

		ServletContextListener.super.contextInitialized(sce);
	}

	/**
	 * Metoda koja se poziva kod undeploya aplikacije.
	 *
	 * @param sce objekt klase ServletContextEvent
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		serijalizirajJmsPoruke(sce);

		obrisiPostavke(sce);

		ServletContextListener.super.contextDestroyed(sce);
	}

}
