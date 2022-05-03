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

	//dichiarazione del graf fatta qui, quando viene riempita e non quando la dichiaro
	public String creaGrafo(int distanza_minima) {
		this.grafo= new SimpleWeightedGraph <Airport, DefaultWeightedEdge> (DefaultWeightedEdge.class); 
		
		
		ExtFlightDelaysDAO dao= new ExtFlightDelaysDAO(); 
		
		//lista di fermate da convertire in vertici del grafo
		List<Flight> flights = dao.loadAllFlights(); 
		Set<Airport> airports= new HashSet<Airport>(); 
		Map<Integer, Airport> airportsMap= new HashMap<Integer, Airport>(); 
		
		for (Airport a: dao.loadAllAirports()) {
			airportsMap.put(a.getId(),a); 
		}
		/*for (Flight f: flights) {
			if(f.getDistance()>=distanza_minima) {
				airports.add(airportsMap.get(f.getOriginAirportId()));
				airports.add(airportsMap.get(f.getDestinationAirportId()));
			}
		 //questa parte è inutile, considera come vertici tutti gli aeroporti	
				
		}*/
		
		Graphs.addAllVertices(this.grafo, airportsMap.values());
		
		List <CoppiaId> aeroportiDaCollegare = dao.getAllAeroportiConnessi(distanza_minima); 
		for (CoppiaId coppia: aeroportiDaCollegare) {
		//METODO PER AGGIUNGERE ARCHI PESATI
		Graphs.addEdge(grafo,  airportsMap.get(coppia.getIdPartenza()), airportsMap.get(coppia.getIdArrivo()), coppia.getDistanza());
		}
			
		
		
		System.out.println(this.grafo);
		System.out.println("Vertici =" + this.grafo.vertexSet().size()); //lista convertita in un set
		System.out.println("Archi =" + this.grafo.edgeSet().size());
		visitaGrafo(dao.loadAllAirports().get(0));
		
		return "Vertici: "+ this.grafo.vertexSet().size()+ "\nArchi: " + this.grafo.edgeSet().size()+ "\nArchi con relativa distanza:" ;
	
		
		}
	
	public void visitaGrafo(Airport aeroporto) {
		GraphIterator <Airport, DefaultWeightedEdge> visita = new BreadthFirstIterator <>(this.grafo, aeroporto);
		while (visita.hasNext()) {
			Airport f= visita.next(); 
			System.out.println(f);
			
		}
	
	}
		
	//MANCA LA PARTE DI MOSTRARE GLI ARCHI PESATI
	//dao. molto costoso in termini di tempo 
			
			
		
}

