package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.exception.RimozioneSocietaAssociata;
import it.prova.gestionesocieta.model.Societa;

public interface SocietaService {
	public List<Societa> listAllSocieta() ;

	public Societa caricaSingoloSocieta(Long id);

	public void aggiorna(Societa societaInstance);

	public void inserisciNuovo(Societa societaInstance);

	public void rimuovi(Societa societaInstance) throws RimozioneSocietaAssociata;

	public List<Societa> findByExample(Societa example);
	
	public List<Societa> cercaTutteSocietaConDipendentiConRedditoAnnuo(int etaInput);
}
