/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamPunteggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSquadra"
    private ChoiceBox<Team> boxSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaSquadra"
    private Button btnSelezionaSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaAnnataOro"
    private Button btnTrovaAnnataOro; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaCamminoVirtuoso"
    private Button btnTrovaCamminoVirtuoso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

    @FXML
    void doSelezionaSquadra(ActionEvent event) {

    	txtResult.clear();
    	
    	Team squadra = boxSquadra.getValue();
    	
    	if(squadra!=null) {
    		
    		List<TeamPunteggio> result = model.getListaPunteggi(squadra);
    		
    		if(result.isEmpty()) 
    			txtResult.appendText("La squadra non ha ancora gareggiato");
    		
    		else {
    			
    			for(TeamPunteggio tp : result)
    				txtResult.appendText(tp.getSeason()+ " - " + tp.getTeam() + "  Punti classifica: "+ tp.getPunteggio()+"\n");
    			
    		}
    			
    	}
    	else
    		txtResult.setText("Errore! Selezionare una squadra");
    	
    	
    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {

    	
        txtResult.clear();
    	
    	Team squadra = boxSquadra.getValue();
    	
    	if(squadra!=null) {
    		
    	    
    		TeamPunteggio tp  = model.annataDOro(squadra);
    		
        	txtResult.appendText(tp.getSeason()+ " - " + tp.getTeam() + "  Punti classifica: "+ tp.getPunteggio()+"\n");
    			
    			
    	}
    	else
    		txtResult.setText("Errore! Selezionare una squadra");
    	
    	
    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {
    	
    	 txtResult.clear();
     	
     	Team squadra = boxSquadra.getValue();
     	
     	if(squadra!=null) {
     		
     	    
             model.annataDOro(squadra);
     		
     		/*
     		 * Decido di ricreare il grafo ogni volta
     		 * Tale scelta è stata presa in considerazione per due motivi:
     		 *   -  a livello di tempistiche non costa molyo
     		 *   - nel caso in cui l'utente scegliesse l'annata d'oro di una squadra
     		 *     ma poi cambiasse tale squadra dal menu a tendina insorgerebbero dei problemi 
     		 */
           
             List<TeamPunteggio> result = model.camminoVirtuoso(squadra);
             
             for(TeamPunteggio tp : result)
            	 txtResult.appendText(tp.getSeason()+ " - " + tp.getTeam() + "  Punti classifica: "+ tp.getPunteggio()+"\n");
     		
     			
     			
     	}
     	else
     		txtResult.setText("Errore! Selezionare una squadra");
     	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";
        
        //
          
    }

	public void setModel(Model model) {
	   this.model=model;
		
	}

	public void caricaBox() {
		boxSquadra.getItems().addAll(model.getSquadre());
		
	}
}
