package com.tcb.sensenet.internal.analysis.diffusion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.RandomUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class DefaultWalkStrategy implements WalkStrategy {

	protected final int maxSteps;
	protected final CyNode source;
	protected final double restartProb;
	public Long seed = null;
	
	// May be null for unweighted
	protected final String weightColumn;	
	
	public class Run implements WalkStrategy.Run {

		protected int steps = 0;
		protected RandomUtil rnd;
		protected HashSet<CyNode> visited = new HashSet<>();
			
		protected Run(){
			this.rnd = new RandomUtil();
			if(seed!=null) rnd.setSeed(seed);
			visited.add(source);
		}
		
		@Override
		public CyNode next(CyNetworkAdapter network, CyNode node) {
			if(steps==Integer.MAX_VALUE) 
				throw new RuntimeException("Exceeded maximum number of steps");
			if(steps==maxSteps) return null;
			List<CyNode> neighbors = network.getNeighborList(node, CyEdge.Type.ANY);
			CyNode result = pickNext(network,neighbors);
			//if(result.equals(source)) visited.clear();
			visited.add(result);
			steps++;
			return result;
		}
		
		private CyNode pickNext(CyNetworkAdapter network, List<CyNode> nodes){
			Double r = 1.0 - rnd.getAdapted().nextDouble();
			if(restartProb > r){
				restart();
				return source;
			} else {
				return pickWeighted(network,nodes);
			}
		}
		
		private CyNode pickWeighted(CyNetworkAdapter network, List<CyNode> nodes){
			List<Double> weights = nodes.stream()
					.map(n -> getWeight(network,n))
					.collect(Collectors.toList());
			CyNode n = rnd.pickRandom(nodes, weights);
			return n;
		}
		
		protected void restart(){
			// Do nothing			
		}
		
		@Override
		public List<CyNode> getVisited() {
			return new ArrayList<>(visited);
		}
		
		@Override
		public Integer getSteps(){
			return steps;
		}
		
		@Override
		public Double getWeight(CyNetworkAdapter network, CyNode node) {
			if(weightColumn==null) return 1.0;
			else return network.getRow(node).getRaw(weightColumn, Double.class);
		}

		@Override
		public CyNode getSource() {
			return source;
		}
						
	}
	
	public DefaultWalkStrategy(CyNode source, Integer maxSteps, String weightColumn,
			Double restartProb){
		this.source = source;
		this.maxSteps = maxSteps;
		this.weightColumn = weightColumn;
		this.restartProb = restartProb;
	}
	
	public void setSeed(long seed){
		this.seed = seed;
	}
			
	@Override
	public WalkStrategy.Run createRun() {
		return new Run();
	}

}
