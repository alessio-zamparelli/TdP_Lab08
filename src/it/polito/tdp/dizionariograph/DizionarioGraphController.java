package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

	private Model model;

	public void setModel(Model model) {
		this.model = model;
	}

	// qui inizial la parte di fxml

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField lettere;

	@FXML
	private TextField parola;

	@FXML
	private TextArea txtResult;

	@FXML
	void doGeneraGrafo(ActionEvent event) {
		int numeroCaratteri;
		try {
			numeroCaratteri = Integer.parseInt(lettere.getText());
		} catch (NumberFormatException e) {
			txtResult.setText("Numero di lettere non valido");
			return;
		}
		model.createGraph(numeroCaratteri);
		txtResult.setText("Creato il grafo\n");
		txtResult.appendText(String.format("Numero di parole: %d\nEcco le prime 100:\n", model.getGraphVertexSize()));
		model.getAllWords().stream().limit(100).forEach(a -> txtResult.appendText(a + "\n"));
		
	}

	@FXML
	void doReset(ActionEvent event) {
		model.clear();
		txtResult.clear();
		parola.clear();
		lettere.clear();
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {
		int maxDeg = model.findMaxDegree();
		txtResult.appendText("Grado massimo: " + maxDeg);
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {
		if(parola.getText().isEmpty()|| parola.getText().length()==0) {
			txtResult.appendText("[ATTENZIONE] Inserire una parola valida!\n");
			return;
		}
		List<String> vicini = model.displayNeighbours(parola.getText());
		if(vicini.size()==0) {
			txtResult.appendText("Termine non presente, provare nuovamente\n");
			return;
		}
		txtResult.appendText("Lista dei vicini");
		for (String s : vicini) {
			txtResult.appendText(s + "\n");
		}

	}

	@FXML
	void initialize() {
		assert lettere != null : "fx:id=\"lettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
		assert parola != null : "fx:id=\"parola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

	}

}
