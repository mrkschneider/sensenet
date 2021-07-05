package com.tcb.sensenet.internal.analysis.diffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.analysis.diffusion.DefaultWalkStrategy.Run;
import com.tcb.sensenet.internal.util.RandomUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class SymmetricTargetedWalkStrategy implements WalkStrategy {
	
	private TargetedWalkStrategy strat1;
	private TargetedWalkStrategy strat2;
	
	public class Run implements WalkStrategy.Run {
				
		private WalkStrategy.Run run1;
		private WalkStrategy.Run run2;
		private WalkStrategy.Run currentRun;
		
		private Run(){
			this.run1 = strat1.createRun();
			this.run2 = strat2.createRun();
			this.currentRun = run1;
			if(run1==run2) throw new 
				IllegalArgumentException("Must use different Run objects");
		}
		
		@Override
		public CyNode next(CyNetworkAdapter network, CyNode node) {
			CyNode n = currentRun.next(network, node);
									
			if(n==null)	{
				if(currentRun==run2) return n;
				else {
					currentRun = run2;
					n = currentRun.next(network, currentRun.getSource());
				}
			}
			
			return n;
		}
		
		@Override
		public List<CyNode> getVisited() {
			List<CyNode> result = new ArrayList<>();
			result.addAll(run1.getVisited());
			result.addAll(run2.getVisited());
			return result;
		}

		@Override
		public Integer getSteps() {
			return run1.getSteps() + run2.getSteps();
		}

		@Override
		public Double getWeight(CyNetworkAdapter network, CyNode node) {
			return currentRun.getWeight(network, node);
		}

		@Override
		public CyNode getSource() {
			return currentRun.getSource();
		}
		
	}
	
	public SymmetricTargetedWalkStrategy(CyNode source, CyNode target, Integer maxSteps, 
			String weightColumn,Double restartProb){
		this.strat1 = new TargetedWalkStrategy(source, target,
				maxSteps, weightColumn, restartProb);
		this.strat2 = new TargetedWalkStrategy(target, source,
				maxSteps, weightColumn, restartProb);
	}
	
	public void setSeed(long seed){
		strat1.setSeed(seed);
		strat2.setSeed(seed);
	}
	
	@Override
	public WalkStrategy.Run createRun() {
		return new Run();
	}
}
