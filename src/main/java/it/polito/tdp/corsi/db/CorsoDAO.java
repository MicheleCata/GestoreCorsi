package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	//pattern ORM (Object Relational Mapping)
	public List <Corso> getCorsiByPeriodo (int periodo) {
		
		// QUERY IMPORTATA
		String sql ="SELECT * "
				+ "FROM corso "
				+ "where pd= ?";
		
		List<Corso> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,periodo);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso (rs.getString("codins"), rs.getString("nome"), rs.getInt("crediti"),rs.getInt("pd" ));
				result.add(c);
			}
			
			rs.close();
			st.close();
			conn.close();
		}catch (SQLException e) {
			throw new RuntimeException();
		}
		return result;
	}
	
	public Map<Corso, Integer> getIscrittiCorsoByPeriodo (Integer periodo) {
		
		String sql =" SELECT c.codins,c.nome, c.crediti,c.pd, Count(*) as TOT "
				+ "FROM corso c, iscrizione i "
				+ "where c.codins=i.codins AND c.pd=? "
				+ "group by c.codins, c.nome, c.crediti";
		
		Map<Corso,Integer> result = new HashMap<Corso, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,periodo);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso (rs.getString("codins"), rs.getString("nome"), rs.getInt("crediti"),rs.getInt("pd" ));
				Integer n = rs.getInt("TOT");
				result.put(c,n);
			}
			conn.close();
		}catch (SQLException e) {
			throw new RuntimeException();
		}
		return result;
	}
	
	public List<Studente> getStudentiByCorso(Corso corso) {
		
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.CDS "
				+"FROM studente s, iscrizione i "
				+"where s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs = st.executeQuery(); 
			
			while (rs.next()) {
				Studente s = new Studente (rs.getInt("matricola"),rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS"));
				result.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
				
		}catch (SQLException e) {
			throw new RuntimeException();
		}
		
		return result;
	}

	public boolean esisteCorso(Corso corso) {
		String sql = "SELECT * FROM corso where codins=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs = st.executeQuery(); 
			
			if (rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;
			}
			else {
				rs.close();
				st.close();
				conn.close();
				return false;
			}
		}catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
	
	public Map <String, Integer> getDivisioneStudenti (Corso corso) {
		
		// SOLUZIONE 2 interrogando il DAO
		
		String sql= "SELECT s.CDS, Count(*) as TOT "
				+ "From studente s, iscrizione i "
				+ "where s.matricola=i.matricola AND i.codins = ? AND s.CDS <> '' "
				+ "group by s.CDS";
		
		Map <String, Integer> divisione = new HashMap<String, Integer>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs = st.executeQuery(); 
			
			while (rs.next()) {
				divisione.put(rs.getString("CDS"), rs.getInt("TOT"));
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch (SQLException e) {
			throw new RuntimeException();
		}
		return divisione;
	}
}
