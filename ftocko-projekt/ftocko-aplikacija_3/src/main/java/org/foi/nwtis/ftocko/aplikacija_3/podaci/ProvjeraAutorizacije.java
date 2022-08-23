package org.foi.nwtis.ftocko.aplikacija_3.podaci;

import java.util.Date;

public class ProvjeraAutorizacije {
	
	private ZetoniDAO zDao = new ZetoniDAO();
	
	public int autorizirajKorisnika(String korisnik, String token) {
		
		int statusniKod = 0;
		int vrijZetona = 0;
		
		if(korisnik == null) korisnik = "";
		
		if(token != null) {
			vrijZetona = Integer.parseInt(token);
		}
		
		Zeton z = zDao.dohvatiZeton(vrijZetona);
		long trenutnoVrijeme = new Date().getTime();
		
		if(z!=null) {
			
			if(z.getKorisnik().compareTo(korisnik) != 0) {
				statusniKod = 401;
			}
			
			else if(z.getStatus() == 0) {
				statusniKod = 408;
			}
			
			else if(z.getVrijeme_valjanosti() <= trenutnoVrijeme) {
				
				if(z.getStatus() == 1) {
					
					zDao.deaktivirajZeton(z.getId());
					
				}
				statusniKod = 408;
			}
			
			else {
				statusniKod = 200;
			}
			
		}
		
		return statusniKod;
		
	}

}
