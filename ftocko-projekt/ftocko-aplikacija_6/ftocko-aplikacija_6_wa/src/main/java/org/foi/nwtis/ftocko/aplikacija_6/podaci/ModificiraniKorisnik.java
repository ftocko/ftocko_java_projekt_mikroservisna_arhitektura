package org.foi.nwtis.ftocko.aplikacija_6.podaci;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class ModificiraniKorisnik {
	
    @Setter 
    @Getter
    @NonNull
	private String ime;
    
    @Setter 
    @Getter
    @NonNull
	private String prezime;
    
    @Setter 
    @Getter
    @NonNull
	private String korime;
    
    @Setter 
    @Getter
    @NonNull
	private String email;
    
    @Setter 
    @Getter
    @NonNull
	private String lozinka;
    
    @Setter 
    @Getter
    @NonNull
	private String razlikuje;
	
}
