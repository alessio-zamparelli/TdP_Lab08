package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	WordDAO dao;
	Graph<String, DefaultEdge> graph;

	public Model() {
		dao = new WordDAO();
	}

	public void createGraph(int numeroLettere) {

		// creo il grafo semplice non pesato
		graph = new SimpleGraph<>(DefaultEdge.class);
		List<String> words = dao.getAllWordsFixedLength(numeroLettere);

		// aggiungo tutti i vertici
		Graphs.addAllVertices(graph, words);

		System.out.println(graph.vertexSet());
	}

	public List<String> displayNeighbours(String parolaInserita) {

		if(!graph.containsVertex(parolaInserita))
			return new ArrayList<>();
		Set<String> words = dao.getAllWordsLike(parolaInserita);
		for (String w : words) {
			graph.addEdge(parolaInserita, w);
		}
		return Graphs.neighborListOf(graph, parolaInserita).parallelStream().sorted().collect(Collectors.toList());
	}

	public int findMaxDegree() {
		int maxDegValue = -1;
		int currDeg;
		for (String v : graph.vertexSet()) {
			currDeg = graph.degreeOf(v);
			if (currDeg > maxDegValue) {
				maxDegValue = currDeg;
			}
		}
		return maxDegValue;
	}

	private boolean isWordValid(String word) {
		return true;
	}

	public int getGraphVertexSize() {
		return this.graph.vertexSet().size();
	}

	public int getGraphEdgeSize() {
		return this.graph.edgeSet().size();
	}

	public void clear() {
		this.graph = null;
	}

	public Set<String> getAllWords() {
		return this.graph.vertexSet();
	}
}
