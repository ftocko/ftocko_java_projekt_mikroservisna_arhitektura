package org.foi.nwtis.ftocko.aplikacija_1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

import org.foi.nwtis.rest.klijenti.LIQKlijent;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 * Glavna klasa korisnika koji šalje zahtjeve glavnom poslužitelju.
 */
public class KorisnikGlavni {


	public static void main(String[] args) {
		
		String komanda = "";
		
		if(args.length>0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			komanda = sb.toString().trim();
		}
		
		KorisnikGlavni kg = new KorisnikGlavni();
		String odgovor = kg.posaljiKomandu("localhost", 8000, komanda);
		System.out.println(odgovor);
	}
	
	public String posaljiKomandu(String adresa, int port, String komanda) {
        try (
                 Socket veza = new Socket(adresa, port);
                 InputStreamReader isr = new InputStreamReader(veza.getInputStream(),
                        Charset.forName("UTF-8"));
                 OutputStreamWriter osw = new OutputStreamWriter(veza.getOutputStream(),
                        Charset.forName("UTF-8"));) {  

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

	private void ispis(String message) {
		System.out.println("KORISNIK: "+message);
	}

}
