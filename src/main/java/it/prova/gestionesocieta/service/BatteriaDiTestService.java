package it.prova.gestionesocieta.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.model.Dipendente;

@Service
public class BatteriaDiTestService {

	@Autowired
	private SocietaService societaService;

	@Autowired
	private DipendenteService dipendenteService;

	public void testInserisciNuovoSocieta() {

		Societa nuovoSocieta = new Societa("Societa 1", "Via 1", new Date());
		if (nuovoSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovoSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovoSocieta);
		if (nuovoSocieta.getId() == null || nuovoSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovoSocieta...failed: inserimento fallito");

		System.out.println("testInserisciNuovoSocieta........OK");
	}
	
	public void testFindByExampleSocieta() {
		Societa nuovoSocieta = new Societa();
		nuovoSocieta.setRagioneSociale("Societa 2");
		nuovoSocieta.setIndirizzo("Via 2");
		nuovoSocieta.setDataFondazione(new Date());
		
		if (nuovoSocieta.getId() != null)
			throw new RuntimeException("testFindByExampleSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovoSocieta);
		if (nuovoSocieta.getId() == null || nuovoSocieta.getId() < 1)
			throw new RuntimeException("testFindByExampleSocieta...failed: inserimento fallito");
		
		Societa societaExample = new Societa();
		nuovoSocieta.setRagioneSociale("Soc");
		nuovoSocieta.setIndirizzo("Via");
		//nuovoSocieta.setDataFondazione(new Date());
		
		List<Societa> societa = societaService.findByExample(societaExample);
		if(societa.size() != 2) {
			throw new RuntimeException("testFindByExampleSocieta...failed: numero di record inaspettato");
		}
		
		System.out.println("testFindByExampleSocieta........OK");
	}
	
	public void testRimuoviSocieta() {

		Societa nuovoSocieta = new Societa("Societa Eliminata", "Via eliminata", new Date());
		if (nuovoSocieta.getId() != null)
			throw new RuntimeException("testRimuoviSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovoSocieta);
		if (nuovoSocieta.getId() == null || nuovoSocieta.getId() < 1)
			throw new RuntimeException("testRimuoviSocieta...failed: inserimento fallito");
		
		// TEST PER SOCIETA ASSOCIATA A DIPENDENTI CHE DEVE LANCIARE ECCEZZIONE
		//nuovoSocieta.getDipendenti().add(new Dipendente());
		
		int numSocieta = societaService.listAllSocieta().size();

		societaService.rimuovi(nuovoSocieta);
		if(numSocieta == societaService.listAllSocieta().size()) {
			throw new RuntimeException("testRimuoviSocieta...failed: rimozione non avvenuta");
		}
		
		System.out.println("testRimuoviSocieta........OK");
	}
}
