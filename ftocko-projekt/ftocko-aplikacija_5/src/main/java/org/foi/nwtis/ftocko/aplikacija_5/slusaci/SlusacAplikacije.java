package org.foi.nwtis.ftocko.aplikacija_5.slusaci;
import java.io.File;

import org.foi.nwtis.ftocko.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.KonfiguracijaBP;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

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
    	}
    	catch(Exception e) {
    		System.out.println("Nije moguće dohvatiti inicijalni parametar konteksta!");
			return;
    	}
    	
    	String putanja = context.getRealPath("/WEB-INF");
    	nazivDatoteke = putanja + File.separator+nazivDatoteke;
    	
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

    /**
     * Metoda koja se poziva kod deploya aplikacije.
     *
     * @param sce objekt klase ServletContextEvent
     */
    @Override
	public void contextInitialized(ServletContextEvent sce) {
    	
    	ucitajPostavke(sce);
    	
		ServletContextListener.super.contextInitialized(sce);
	}
    
	/**
	 * Metoda koja se poziva kod undeploya aplikacije.
	 *
	 * @param sce objekt klase ServletContextEvent
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		obrisiPostavke(sce);
		
		ServletContextListener.super.contextDestroyed(sce);
	}
    
}
