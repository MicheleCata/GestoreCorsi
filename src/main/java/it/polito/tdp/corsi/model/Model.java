package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(int periodo) {
		return corsoDao.getCorsiByPeriodo(periodo);
	}
	

	public Map<Corso, Integer> getIscrittiCorsoByPeriodo (Integer periodo) {
		return corsoDao.getIscrittiCorsoByPeriodo(periodo);
		
	}
	
	public List<Studente> getStudentiByCorso (String codice) {
		return corsoDao.getStudentiByCorso(new Corso(codice, null, null, null));
	}
	
	public Map <String, Integer> getDivisioneCDS (String codice) {
		//vogliamo ritornare il numero di studenti iscritti al corso per ciascun CDS 
		
		// SOLUZIONE 1 basata su metodi già implementati
		// NOTA!! Questa soluzione è più rischiosa perchè si possono commettere errori
		// nella logica applicativa
		
		/* Map <String, Integer> divisione = new HashMap<String, Integer>();
			List<Studente> studenti = this.getStudentiByCorso(codice);
			for (Studente s: studenti) {
				if (s.getCDS()!="" && s.getCDS()!=null) {
					if (divisione.get(s.getCDS())==null) {
						divisione.put(s.getCDS(), 1);
				} else {
				divisione.put(s.getCDS(), divisione.get(s.getCDS())+1);
				}
			}
		}
		return divisione; */
		
		// è più conveniente interrogare il database!!
		return corsoDao.getDivisioneStudenti(new Corso(codice,null,null,null));
		
	}

	public boolean esisteCorso(String codice) {
		
		return corsoDao.esisteCorso(new Corso(codice, null, null, null));
	}
	
}
