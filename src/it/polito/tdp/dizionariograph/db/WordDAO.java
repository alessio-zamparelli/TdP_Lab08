package it.polito.tdp.dizionariograph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int length) {

		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		List<String> parole = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, length);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(res.getString("nome"));
			}

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

	public Set<String> getAllWordsLike(String word) {
		String sql = "SELECT *  FROM parola WHERE nome LIKE ?;";
		Set<String> parole = new HashSet<String>();
		StringBuilder sb = new StringBuilder(word);
		Character oldChar;
		for (int i = 0; i < word.length(); i++) {
			oldChar = sb.charAt(i);
			sb.setCharAt(i, '_');
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, sb.toString());
				ResultSet res = st.executeQuery();

				while (res.next()) {
					parole.add(res.getString("nome"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Error Connection Database");
			}
			sb.setCharAt(i, oldChar);

		}
		// tolgo la parola di partenza
		parole.remove(word);
		return parole;
	}

}
