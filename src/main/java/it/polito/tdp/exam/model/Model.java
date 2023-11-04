package it.polito.tdp.exam.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;

public class Model {
	
	private List<Year> years = null ;
	private Year currentYear = null ;
	private List<Team> teams = null ;
	private Map<String, Team> teamsIdMap = null ;
	private Map<Team, Double> teamSalaries = null ;
	private Graph<Team, DefaultWeightedEdge> graph = null ;
	
	public List<Year> getYears() {
		if(years==null) {
			BaseballDAO dao = new BaseballDAO() ;
			this.years = dao.readYearsSince(Year.of(1980)) ;
		}
		return this.years ;
	}
	
	public List<Team> getTeams() {
		return this.teams ;
	}
	
	public void loadTeamsByYear(Year y) {
		BaseballDAO dao = new BaseballDAO() ;
		this.teams = dao.readTeamsByYear(y) ;
		this.teamsIdMap = new HashMap<String, Team>() ;
		for(Team t: this.teams) {
			this.teamsIdMap.put(t.getTeamCode(), t) ;
		}
		this.currentYear = y ; // così sappiamo sempre a quale anno si riferiscono i teams
	}
	
	public String creaGrafo() {
		this.graph = new SimpleWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
		
		Graphs.addAllVertices(this.graph, this.teams) ;
		
		// Compute salaries for each team
		BaseballDAO dao = new BaseballDAO() ;
		this.teamSalaries = dao.getTeamSalaries(this.currentYear, teamsIdMap) ;
		
//		System.out.println(this.teamSalaries) ;
		
		for(Team t1: this.graph.vertexSet()) {
			for(Team t2: this.graph.vertexSet()) {
				if(!t1.equals(t2)) {
					Graphs.addEdge(this.graph, t1, t2, teamSalaries.get(t1)+teamSalaries.get(t2)) ;
				}
			}
		}
		
		return "Grafo creato con "+ this.graph.vertexSet().size() + " vertici e " + this.graph.edgeSet().size() + " archi.\n" ;
	}
	
	public List<Pair<Team, Double>> getAdjacentTeams(Team t) {
		List<Pair<Team, Double>> result = new ArrayList<>() ;
		
		List<Team> neighbors = Graphs.neighborListOf(this.graph, t) ;
		for(Team t2: neighbors) {
			Double peso = this.graph.getEdgeWeight(this.graph.getEdge(t, t2)) ;
			result.add(new Pair<Team, Double>(t2, peso)) ;
		}
		
		result.sort(Comparator.comparing(Pair<Team,Double>::getSecond).reversed());
		
		return result ;
	}
}
