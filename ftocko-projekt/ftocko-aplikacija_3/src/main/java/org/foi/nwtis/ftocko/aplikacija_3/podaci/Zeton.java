package org.foi.nwtis.ftocko.aplikacija_3.podaci;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Zeton {
	
	@Getter
    @Setter 
    @NonNull
	private int id;

	@Getter
    @Setter 
    @NonNull
	private String korisnik;
	
	@Getter
    @Setter 
    @NonNull
	private long vrijeme_valjanosti;
	
	@Getter
    @Setter 
    @NonNull
	private int status;

}
