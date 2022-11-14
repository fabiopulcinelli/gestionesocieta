package it.prova.gestionesocieta.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>,QueryByExampleExecutor <Dipendente> {
	//metodi custom
	
	@Query(value="SELECT d.* FROM dipendente d JOIN societa s ON s.id=societa_id WHERE s.datafondazione > ?1 "
			+ "ORDER BY (s.datafondazione - d.dataassunzione) DESC LIMIT 1", nativeQuery=true)
	Dipendente findOldestByYear(Date anno);
}
