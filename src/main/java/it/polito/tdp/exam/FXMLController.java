package it.polito.tdp.exam;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.alg.util.Pair;

import it.polito.tdp.exam.model.Model;
import it.polito.tdp.exam.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Model model;

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDettagli;

    @FXML
    private Button btnPercorso;

    @FXML
    private ComboBox<Year> cmbAnno;

    @FXML
    private ComboBox<Team> cmbSquadra;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtSquadre;

    @FXML
    void handleAnno(ActionEvent event) {
    	Year y = cmbAnno.getValue() ;
    	model.loadTeamsByYear(y);
    	List<Team> teams = model.getTeams() ;
    	txtSquadre.clear();

    	txtSquadre.appendText("Squadre presenti nell'anno "+y+" = "+teams.size()+"\n");
    	for(Team t: teams) {
    		txtSquadre.appendText(t.toString()+"\n");
    	}
    	cmbSquadra.getItems().clear() ;
    	cmbSquadra.getItems().addAll(teams);
    	cmbSquadra.setValue(teams.get(0)); // seleziono il primo così non avrò mai null
    	
    	btnCreaGrafo.setDisable(false);
    	cmbSquadra.setDisable(false);
    	btnDettagli.setDisable(true);
    	btnPercorso.setDisable(true) ;

    }

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	String msg = model.creaGrafo();
    	txtResult.appendText(msg);
    	btnDettagli.setDisable(false);
    	btnPercorso.setDisable(false);
    }

    @FXML
    void handleDettagli(ActionEvent event) {
    	
    	Team t = cmbSquadra.getValue() ;
    	if(t!=null) {
    		List<Pair<Team, Double>> adj = model.getAdjacentTeams(t) ;
    		txtResult.clear();
    		txtResult.appendText("Adiacenti per la squadra "+t.toString()+"\n");
    		for(Pair<Team, Double> p : adj) {
    			txtResult.appendText(String.format("%-30s %20.0f\n" , p.getFirst(), p.getSecond()));
    		}
    	}
    }

    @FXML
    void handlePercorso(ActionEvent event) {
    	// TODO: SOLUZIONE PUNTO 2
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDettagli != null : "fx:id=\"btnDettagli\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSquadre != null : "fx:id=\"txtSquadre\" was not injected: check your FXML file 'Scene.fxml'.";
        
        cmbSquadra.setDisable(true);
        btnCreaGrafo.setDisable(true);
        btnDettagli.setDisable(true);
        btnPercorso.setDisable(true);

    }

	public void setModel(Model model) {
		this.model= model;
		
		cmbAnno.getItems().setAll(model.getYears());
		
	}

}
