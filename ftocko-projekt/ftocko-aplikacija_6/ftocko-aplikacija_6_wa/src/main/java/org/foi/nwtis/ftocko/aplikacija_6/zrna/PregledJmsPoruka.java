package org.foi.nwtis.ftocko.aplikacija_6.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_6.jms.RepozitorijJmsPoruka;
import org.foi.nwtis.ftocko.jms.SAerodrom;
import org.foi.nwtis.ftocko.jms.SMeteoPodaci;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@ViewScoped
@Named("pregledJmsPoruka")
public class PregledJmsPoruka implements Serializable {

	@EJB
	RepozitorijJmsPoruka rjp;

	private List<SMeteoPodaci> meteoPoruke;
	
	private List<SAerodrom> aerodromPoruke;

	public List<SAerodrom> getAerodromPoruke() {
		this.aerodromPoruke = dajAerodromPoruke();
		return aerodromPoruke;
	}

	public void setAerodromPoruke(List<SAerodrom> aerodromPoruke) {
		this.aerodromPoruke = aerodromPoruke;
	}

	public List<SMeteoPodaci> getMeteoPoruke() {
		this.meteoPoruke = dajMeteoPoruke();
		return meteoPoruke;
	}

	public void setMeteoPoruke(List<SMeteoPodaci> meteoPoruke) {
		this.meteoPoruke = meteoPoruke;
	}
	
	private List<SAerodrom> dajAerodromPoruke(){
		
		List<SAerodrom> poruke = new ArrayList<SAerodrom>();
		
		try {

			for (Object poruka : rjp.poruke) {

				if (poruka instanceof SAerodrom) {
					poruke.add((SAerodrom) poruka);
				}

			}

		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja JMS poruka!");
		}
		
		return poruke;
		
	}

	private List<SMeteoPodaci> dajMeteoPoruke() {

		List<SMeteoPodaci> poruke = new ArrayList<SMeteoPodaci>();

		try {

			for (Object poruka : rjp.poruke) {

				if (poruka instanceof SMeteoPodaci) {
					poruke.add((SMeteoPodaci) poruka);
				}

			}

		}

		catch (Exception e) {
			System.out.println("Problem kod dohvaćanja JMS poruka!");
		}

		return poruke;

	}
	
	public void obrisiJmsPoruke() {
		
		rjp.poruke.clear();
	}

}
