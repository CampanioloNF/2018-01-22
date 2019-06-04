package it.polito.tdp.seriea.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {

	
	private List<Team> squadre;
	private SerieADAO dao;
	private Graph<TeamPunteggio, DefaultWeightedEdge> grafo;
	private List <TeamPunteggio> camminoVirtuoso ; 
	
	public Model() {
		
		this.dao = new SerieADAO();
		this.squadre = dao.listTeams();
	}
	
	
	
	public List<Team> getSquadre() {
	
		return squadre;
	}



	public List<TeamPunteggio> getListaPunteggi(Team squadra) {

		List<TeamPunteggio> result = new LinkedList<>();
		TeamPunteggio tp = null;
		
		for(Season s : dao.listAllSeasons()) {
			tp = dao.getPunteggioSeason(s, squadra);
			
			if(tp!=null)
				result.add(tp);
			
		}
		
		Collections.sort(result);
		
		return result;
	}



	public TeamPunteggio annataDOro(Team squadra) {
		
		
		TeamPunteggio annata = null;
		int degree = 0;
		
		creaGrafo(squadra);
		
		if(grafo.vertexSet().size()==1)
			return this.getListaPunteggi(squadra).get(0);
	
		for(TeamPunteggio se : grafo.vertexSet()) {
			if(grafo.inDegreeOf(se)> degree) {
				annata = se;
				degree = grafo.inDegreeOf(se);
			}
				
		}
	 
		
		return annata;
		
	}



	private void creaGrafo(Team squadra) {
		

		grafo = new SimpleDirectedWeightedGraph<TeamPunteggio, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<TeamPunteggio> vertici = this.getListaPunteggi(squadra);
		
		
		
		//aggiungo i vertici 
		Graphs.addAllVertices(grafo, vertici);
		
		//aggiungo gli archi 
		
		/*
		 * Gli archi sono:
		 * - orientati A->B dove A ha un punteggio migliore di B 
		 * - il grafo risulta essere totalmente connesso.
		 *    Bastano dunque due cicli for
		 */
		
		for(TeamPunteggio tp : vertici) {
			
			for(TeamPunteggio tp2 : vertici) {
				
				if(tp.compareTo(tp2)<0) {
					
					if(tp.getPunteggio()>tp2.getPunteggio()) 
						Graphs.addEdge(grafo, tp2, tp, (tp.getPunteggio()-tp2.getPunteggio()));
					else
						Graphs.addEdge(grafo, tp, tp2, (tp2.getPunteggio()-tp.getPunteggio()));
				
						
					
				}
				
			}
			
		}
		
	}



	public List<TeamPunteggio> camminoVirtuoso(Team squadra) {
		
		/*
		 * Determiniamo i cammini virtuosi 
		 * 
		 * Algoritmo  ricorsivo. 
		 * Partiamo dal primo anno e lo salviamo 
		 * Dunque aggiungiamo season fino a quando non si spezza la sequenza
		 * Chiaramente in quell'intervallo non possono esistere dei cammini migliori, dal momento che sarebbero tutti
		 * più piccoli!
		 */
		
		camminoVirtuoso = new ArrayList<>();
		
		PriorityQueue <TeamPunteggio> candidati = new PriorityQueue<>(this.getListaPunteggi(squadra));
		
	    List <TeamPunteggio> parziale = new ArrayList<>();
		
	    //estraggo la prima season
	    TeamPunteggio tp = candidati.poll();
	    // la carico nel parziale
	    parziale.add(tp);
	    //lancio l'algoritmo ricorsivo
	    cerca(parziale,candidati);
		
		return camminoVirtuoso;
	}



	private void cerca(List<TeamPunteggio> parziale, PriorityQueue<TeamPunteggio> candidati) {
		
		
		
		while(!candidati.isEmpty()) {
	
		//appena entro estraggo dalla coda una season
		
		TeamPunteggio tp = candidati.poll();
		
		//esco dal ciclo se si spezza il cammino virtuoso
			
		if(parziale.get(parziale.size()-1).getPunteggio()>tp.getPunteggio()) {
			
			if(parziale.size()>this.camminoVirtuoso.size()) {
				this.camminoVirtuoso = parziale;
				//rilancio la ricorsione azzerzando il parziale
				 List <TeamPunteggio> nuovoParziale = new ArrayList<>();
				 nuovoParziale.add(tp);
				cerca(nuovoParziale,candidati);
				
			}
			
		}
		
		//altrimenti aggiungo al cammino virtuoso
		
		else {
		parziale.add(tp);
		cerca(parziale, candidati);
		
		}
	  }
		
		//se non ci sono più season
		if(parziale.size()>this.camminoVirtuoso.size()) {
			this.camminoVirtuoso = parziale;
			return;
		}
		
	}
	
	

}
