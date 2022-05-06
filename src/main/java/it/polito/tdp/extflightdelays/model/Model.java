package it.polito.tdp.extflightdelays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;





public class Model {

	private Graph<Airport, DefaultWeightedEdge> grafo; 
	Map<DefaultWeightedEdge, Double> archi_distanza; 
	

	//dichiarazione del graf fatta qui, quando viene riempita e non quando la dichiaro
	public String creaGrafo(int distanza_minima) {
		this.grafo= new SimpleWeightedGraph <Airport, DefaultWeightedEdge> (DefaultWeightedEdge.class); 
		
		
		ExtFlightDelaysDAO dao= new ExtFlightDelaysDAO(); 
		
		//lista di fermate da convertire in vertici del grafo
		archi_distanza = new HashMap<DefaultWeightedEdge,Double>(); 
		List<Flight> flights = dao.loadAllFlights(); 
		Set<Airport> airports= new HashSet<Airport>(); 
		Set <Airport> allAirports= new HashSet<Airport>(dao.loadAllAirports()); 
		Map<Integer, Airport> airportsMap= new HashMap<Integer, Airport>(); 
		
		for (Airport a: dao.loadAllAirports()) {
			airportsMap.put(a.getId(),a); 
		}
		
		//oppure al posto di airportsMap usare la lista allAirpors
		Graphs.addAllVertices(this.grafo, airportsMap.values());
		
		List <CoppiaId> aeroportiDaCollegare = dao.getAllAeroportiConnessi(distanza_minima); 
		
		for (CoppiaId coppia: aeroportiDaCollegare) {
		//METODO PER AGGIUNGERE ARCHI PESATI
		DefaultWeightedEdge e= Graphs.addEdge(grafo,  airportsMap.get(coppia.getIdPartenza()), airportsMap.get(coppia.getIdArrivo()), coppia.getDistanza());
		archi_distanza.put(e, coppia.getDistanza());
		System.out.println(e+ " " + coppia.getDistanza());
		
		}
			
		//System.out.println(this.grafo);
		System.out.println("Vertici =" + this.grafo.vertexSet().size()); //lista convertita in un set
		System.out.println("Archi =" + this.grafo.edgeSet().size());
		
		
		return "Vertici: "+ this.grafo.vertexSet().size()+ "\nArchi: " + this.grafo.edgeSet().size()+ "\n" ;
	
		
		}
	
	//per mostrare gli archi pesati
	public Map <DefaultWeightedEdge, Double> getArchi_distanza() {
		return archi_distanza; 
	
	}		
}

