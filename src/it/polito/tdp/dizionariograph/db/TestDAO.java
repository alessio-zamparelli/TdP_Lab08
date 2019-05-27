package it.polito.tdp.dizionariograph.db;

import java.util.List;
import java.util.Set;

public class TestDAO {
	
	public static void main(String[] args) {
		
		WordDAO wd = new WordDAO();
		
		System.out.println(wd.getAllWordsFixedLength(4));
		
		Set<String> parole = wd.getAllWordsLike("Case");
		
		System.out.println(parole);
		System.out.println("Dimensione: " + parole.size());
	}
	
	

}
