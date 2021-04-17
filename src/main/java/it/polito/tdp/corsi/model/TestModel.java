package it.polito.tdp.corsi.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m= new Model();
		
		List<Corso> corsiPeriodo = m.getCorsiByPeriodo(2);
		
		System.out.println(corsiPeriodo.toString());

	}

}
