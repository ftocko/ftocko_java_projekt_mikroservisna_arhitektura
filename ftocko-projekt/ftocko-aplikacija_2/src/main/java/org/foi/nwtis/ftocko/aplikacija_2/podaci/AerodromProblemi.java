package org.foi.nwtis.ftocko.aplikacija_2.podaci;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Klasa AerodromProblemi.
 */
public class AerodromProblemi {
	
	/**
	 * dohvat id aerodroma.
	 *
	 * @return vraća id aerodroma
	 */
	@Getter
    
    /**
     * postavljanje id aerodroma.
     *
     * @param postavlja id aerodroma
     */
    @Setter 
    @NonNull
	private String ident;
	
	/**
	 * dohvat opisa problema.
	 *
	 * @return vraća opis problema
	 */
	@Getter
    
    /**
     * postavljanje opisa problema.
     *
     * @param postavlja opis problema
     */
    @Setter 
    @NonNull
	private String opis;
	
	/**
	 * dohvat vremena spremanja.
	 *
	 * @return vraća vrijeme spremanja
	 */
	@Getter
    
    /**
     * postavljanje vremena spremanja.
     *
     * @param postavlja vrijeme spremanja
     */
    @Setter 
    @NonNull
	private String stored;

}
