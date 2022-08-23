package org.foi.nwtis.ftocko.aplikacija_3.klijenti;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

import org.foi.nwtis.ftocko.aplikacija_3.slusaci.SlusacAplikacije;
import org.foi.nwtis.ftocko.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

public class Klijent {
	
	private String adresaWA1 = "";
	private int portWA1 = 0;

	public Klijent() {
		
		try {
			PostavkeBazaPodataka pbp = (PostavkeBazaPodataka) SlusacAplikacije.sc.getAttribute("Postavke");
			this.adresaWA1 = pbp.dajPostavku("adresa.wa_1");
			this.portWA1 = Integer.parseInt(pbp.dajPostavku("port.wa_1"));
			
		} catch (Exception e) {
			System.out.println("Problem kod konfiguracije BP!");
		}

	}

	public String posaljiKomandu(String komanda) {
		try (Socket veza = new Socket(adresaWA1, portWA1);
				InputStreamReader isr = new InputStreamReader(veza.getInputStream(), Charset.forName("UTF-8"));
				OutputStreamWriter osw = new OutputStreamWriter(veza.getOutputStream(), Charset.forName("UTF-8"));) {

			osw.write(komanda);
			osw.flush();
			veza.shutdownOutput();
			StringBuilder tekst = new StringBuilder();
			while (true) {
				int i = isr.read();
				if (i == -1) {
					break;
				}
				tekst.append((char) i);
			}
			veza.shutdownInput();
			veza.close();
			return tekst.toString();
		} catch (SocketException e) {
			ispis(e.getMessage());
		} catch (IOException ex) {
			ispis(ex.getMessage());
		}
		return null;
	}

	private void ispis(String poruka) {
		System.out.println("KORISNIK: " + poruka);
	}

}
