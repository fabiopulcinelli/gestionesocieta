package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.exception.RimozioneSocietaAssociata;
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
	
	public void testRimuoviSocieta() throws RimozioneSocietaAssociata {

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
	
	public void testInserimentoDipendente() {
		Societa nuovoSocieta = new Societa("Societa 3", "Via 3", new Date());
		if (nuovoSocieta.getId() != null)
			throw new RuntimeException("testInserimentoDipendente...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovoSocieta);
		if (nuovoSocieta.getId() == null || nuovoSocieta.getId() < 1)
			throw new RuntimeException("testInserimentoDipendente...failed: inserimento fallito");
		
		Dipendente nuovoDipendente = new Dipendente("Mario", "Rossi", new Date(), 15000);
		nuovoDipendente.setSocieta(nuovoSocieta);
		if (nuovoDipendente.getId() != null)
			throw new RuntimeException("testInserimentoDipendente...failed: transient object con id valorizzato");
		// salvo
		dipendenteService.inserisciNuovo(nuovoDipendente);
		if (nuovoDipendente.getId() == null || nuovoDipendente.getId() < 1)
			throw new RuntimeException("testInserimentoDipendente...failed: inserimento fallito");
	}
	
	public void testModificaDipendente() {
		Societa nuovoSocieta = new Societa("Societa 4", "Via 4", new Date());
		if (nuovoSocieta.getId() != null)
			throw new RuntimeException("testModificaDipendente...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovoSocieta);
		if (nuovoSocieta.getId() == null || nuovoSocieta.getId() < 1)
			throw new RuntimeException("testModificaDipendente...failed: inserimento fallito");
		
		Dipendente nuovoDipendente = new Dipendente("Luigi", "Verdi", new Date(), 20000);
		nuovoDipendente.setSocieta(nuovoSocieta);
		if (nuovoDipendente.getId() != null)
			throw new RuntimeException("testModificaDipendente...failed: transient object con id valorizzato");
		// salvo
		dipendenteService.inserisciNuovo(nuovoDipendente);
		if (nuovoDipendente.getId() == null || nuovoDipendente.getId() < 1)
			throw new RuntimeException("testModificaDipendente...failed: inserimento fallito");
		
		//modifico
		nuovoDipendente.setCognome("Gialli");
		nuovoDipendente.setRedditoAnnuoLordo(1000);
		dipendenteService.aggiorna(nuovoDipendente);
		
		// mi aspetto cognome gialli
		if(!dipendenteService.caricaSingoloDipendenti(nuovoDipendente.getId()).getCognome().equals("Gialli")) {
			throw new RuntimeException("testModificaDipendente...failed: modifiche non avvenute!!");
		}
	}
	
	public void testCercaTutteSocietaConDipendentiConRedditoAnnuo() {
		
		List<Societa> societaTrovate = societaService.cercaTutteSocietaConDipendentiConRedditoAnnuo(5000);
		
		//me ne aspetto uno
		if(societaTrovate.size()!=1) {
			throw new RuntimeException("testCercaTutteSocietaConDipendentiConRedditoAnnuo...failed: numero di risultati errato");
		}
	}
	
	public void testFindOldestByYear() {
		
		Dipendente dipendente = null;
		try {
			dipendente = dipendenteService.findOldestByYear(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//il piu anziano e' rossi quindi controllo
		System.out.println(dipendente.toString());
		if(!dipendente.getCognome().equals("Rossi")) {
			throw new RuntimeException("testFindOldestByYear...failed: errore nel trovare il piu anziano");
		}
		
	}
}
