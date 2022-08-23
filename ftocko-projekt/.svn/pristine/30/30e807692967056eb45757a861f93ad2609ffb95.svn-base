package org.foi.nwtis.ftocko.aplikacija_2.slusaci;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.foi.nwtis.ftocko.aplikacija_2.dretve.PreuzimanjeRasporedaAerodroma;
import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.KonfiguracijaBP;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SlusacAplikacije implements ServletContextListener {

	public static ServletContext sc = null;
	
	private Thread pra = null;

	public SlusacAplikacije() {

	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ucitajPostavke(sce);

		pra = new PreuzimanjeRasporedaAerodroma(sce.getServletContext());
		pra.start();

		ServletContextListener.super.contextInitialized(sce);
	}


	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		obrisiPostavke(sce);
		
		pra.interrupt();

		ServletContextListener.super.contextDestroyed(sce);
	}
	
	private void ucitajPostavke(ServletContextEvent sce) {

		String nazivDatoteke = "";
		ServletContext context = sce.getServletContext();
		sc = context;

		try {
			nazivDatoteke = context.getInitParameter("konfiguracija");
		} catch (Exception e) {
			System.out.println("Nije moguće dohvatiti inicijalni parametar konteksta!");
			return;
		}

		String putanja = context.getRealPath("/WEB-INF");
		nazivDatoteke = putanja + File.separator + nazivDatoteke;

		KonfiguracijaBP konfig = new PostavkeBazaPodataka(nazivDatoteke);

		try {
			konfig.ucitajKonfiguraciju();
		} catch (NeispravnaKonfiguracija e) {
			System.out.println("Neispravna konfiguracija!");
			return;
		}

		System.out.println("Konfiguracija učitana!");
		context.setAttribute("Postavke", konfig);

	}
	
	private void obrisiPostavke(ServletContextEvent sce) {

		ServletContext context = sce.getServletContext();
		context.removeAttribute("Postavke");
		System.out.println("Postavke obrisane!");

	}

}
