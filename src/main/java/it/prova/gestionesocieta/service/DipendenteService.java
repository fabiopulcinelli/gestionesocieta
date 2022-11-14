package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {
	public List<Dipendente> listAllDipendenti() ;

	public Dipendente caricaSingoloDipendenti(Long id);

	public void aggiorna(Dipendente dipendenteInstance);

	public void inserisciNuovo(Dipendente dipendenteInstance);

	public void rimuovi(Dipendente dipendenteInstance);

	public List<Dipendente> findByExample(Dipendente example);
}
