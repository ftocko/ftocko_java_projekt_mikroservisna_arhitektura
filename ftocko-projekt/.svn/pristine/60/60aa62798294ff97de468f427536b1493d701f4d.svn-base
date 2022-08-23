package org.foi.nwtis.ftocko.aplikacija_5.zrna.jms;

import java.io.Serializable;

import org.foi.nwtis.ftocko.jms.SAerodrom;
import org.foi.nwtis.ftocko.jms.SMeteoPodaci;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageProducer;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Queue;
import jakarta.jms.Session;

@Stateless
public class PosiljateljPoruke {

	@Resource(mappedName = "jms/NWTiS_ftocko_QueueFactory")
	private ConnectionFactory cf;
	@Resource(mappedName = "jms/NWTiS_ftocko")
	private Queue red;
	
	private SMeteoPodaci dajSMeteoPodatke(MeteoPodaci meteoPodaci, String icao) {
		
		SMeteoPodaci sMeteoPodaci = new SMeteoPodaci();
		
		sMeteoPodaci.setCloudsName(meteoPodaci.getCloudsName());
		sMeteoPodaci.setCloudsValue(meteoPodaci.getCloudsValue());
		sMeteoPodaci.setHumidityUnit(meteoPodaci.getHumidityUnit());
		sMeteoPodaci.setHumidityValue(meteoPodaci.getHumidityValue());
		sMeteoPodaci.setLastUpdate(meteoPodaci.getLastUpdate());
		sMeteoPodaci.setPrecipitationMode(meteoPodaci.getPrecipitationMode());
		sMeteoPodaci.setPrecipitationUnit(meteoPodaci.getPrecipitationUnit());
		sMeteoPodaci.setPrecipitationValue(meteoPodaci.getPrecipitationValue());
		sMeteoPodaci.setPressureUnit(meteoPodaci.getPressureUnit());
		sMeteoPodaci.setPressureValue(meteoPodaci.getPressureValue());
		sMeteoPodaci.setSunRise(meteoPodaci.getSunRise());
		sMeteoPodaci.setSunSet(meteoPodaci.getSunSet());
		sMeteoPodaci.setTemperatureMax(meteoPodaci.getTemperatureMax());
		sMeteoPodaci.setTemperatureMin(meteoPodaci.getTemperatureMin());
		sMeteoPodaci.setTemperatureUnit(meteoPodaci.getTemperatureUnit());
		sMeteoPodaci.setTemperatureValue(meteoPodaci.getTemperatureValue());
		sMeteoPodaci.setVisibility(meteoPodaci.getVisibility());
		sMeteoPodaci.setWeatherIcon(meteoPodaci.getWeatherIcon());
		sMeteoPodaci.setWeatherNumber(meteoPodaci.getWeatherNumber());
		sMeteoPodaci.setWeatherValue(meteoPodaci.getWeatherValue());
		sMeteoPodaci.setWindDirectionCode(meteoPodaci.getWindDirectionCode());
		sMeteoPodaci.setWindDirectionName(meteoPodaci.getWindDirectionName());
		sMeteoPodaci.setWindDirectionValue(meteoPodaci.getWindDirectionValue());
		sMeteoPodaci.setWindSpeedName(meteoPodaci.getWindSpeedName());
		sMeteoPodaci.setWindSpeedValue(meteoPodaci.getWindSpeedValue());
		sMeteoPodaci.setIcao(icao);
		
		return sMeteoPodaci;
	}
	
	private SAerodrom dajSAerodrom(Aerodrom a) {
		
		SAerodrom sAerodrom = new SAerodrom();
		
		sAerodrom.setIcao(a.getIcao());
		
		return sAerodrom;
	}

	public boolean novaMeteoPoruka(MeteoPodaci meteoPodaci, String icao) {
		
		boolean status = true;

		try {
			Connection konekcija = cf.createConnection();
			Session sesija = konekcija.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer mp = sesija.createProducer(red);
			SMeteoPodaci sMeteoPodaci = dajSMeteoPodatke(meteoPodaci, icao);
			ObjectMessage msg = sesija.createObjectMessage((Serializable) sMeteoPodaci);
			mp.send(msg);
			mp.close();
			konekcija.close();
		} catch (Exception e) {
			System.out.println("Problem kod JMS-a!");
			status = false;
		}
		return status;
	}
	
	public boolean novaAerodromPoruka(Aerodrom dodaniAerodrom) {
		
		boolean status = true;

		try {
			Connection konekcija = cf.createConnection();
			Session sesija = konekcija.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer mp = sesija.createProducer(red);
			SAerodrom sAerodrom = dajSAerodrom(dodaniAerodrom);
			ObjectMessage msg = sesija.createObjectMessage((Serializable) sAerodrom);
			mp.send(msg);
			mp.close();
			konekcija.close();
		} catch (Exception e) {
			System.out.println("Problem kod JMS-a!");
			status = false;
		}
		return status;
	}

}
