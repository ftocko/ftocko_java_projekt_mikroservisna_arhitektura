package org.foi.nwtis.ftocko.aplikacija_4.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlGenerator {
	
	public String vratiUrl(String poruka, String vrsta) {
		
		StringBuilder sb = null;
		String uri = "";
		
		
		if(vrsta.compareTo("prijava") == 0) {
			uri = "redirect:/administracija/prijava?poruka=";
		}
		
		else if(vrsta.compareTo("registracija") == 0) {
			uri = "redirect:/administracija/registracija?poruka=";
		}
		
		else if(vrsta.compareTo("brisanjeZetonaKorisnika") == 0) {
			uri = "redirect:/administracija/pregledKorisnika?poruka=";
		}
		
		try {
			sb = new StringBuilder(
			        uri).append(
			        URLEncoder.encode(poruka, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Problem kod enkodiranja!");
		}
		
		return sb.toString();
	}

}
