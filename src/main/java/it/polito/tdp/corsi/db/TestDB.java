package it.polito.tdp.corsi.db;

import it.polito.tdp.corsi.model.Corso;

public class TestDB {

	public static void main(String[] args) {
		CorsoDAO corsoDao = new CorsoDAO();
		
		System.out.println(corsoDao.getStudentiByCorso(new Corso ("01KSUPG", null, null, null)));

	}

}
