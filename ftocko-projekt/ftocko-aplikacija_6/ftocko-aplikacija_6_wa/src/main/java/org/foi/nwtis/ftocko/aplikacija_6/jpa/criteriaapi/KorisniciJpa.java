package org.foi.nwtis.ftocko.aplikacija_6.jpa.criteriaapi;

import java.util.List;

import org.foi.nwtis.ftocko.aplikacija_6.podaci.ModificiraniKorisnik;
import org.foi.nwtis.ftocko.jpa.entiteti.Korisnici;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Stateless
public class KorisniciJpa {
	
	@PersistenceContext(unitName = "NWTiS_ftocko_PU")
	private EntityManager em;
	private CriteriaBuilder cb;
	
	@PostConstruct
	public void init() {
		cb = em.getCriteriaBuilder();
	}
	
	public List<Korisnici> dajSveKorisnike() {
		CriteriaQuery<Korisnici> cq = cb.createQuery(Korisnici.class);
		cq.select(cq.from(Korisnici.class));
		return em.createQuery(cq).getResultList();
	}
	
	public void unesiKorisnike(List<ModificiraniKorisnik> korisnici) {
		
		for(ModificiraniKorisnik mk:korisnici){
			
			if(mk.getRazlikuje() == "da") {
				
				Korisnici korisnik = new Korisnici();
				korisnik.setIme(mk.getIme());
				korisnik.setPrezime(mk.getPrezime());
				korisnik.setKorisnik(mk.getKorime());
				korisnik.setEmail(mk.getEmail());
				korisnik.setLozinka(mk.getLozinka());
				
				em.persist(korisnik);
			}
		}
		
	}
	
	
	
}
