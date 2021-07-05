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

public class TargetedWalkStrategy extends DefaultWalkStrategy {
	
	private final CyNode target;
	
	public class Run extends DefaultWalkStrategy.Run {
				
		@Override
		public CyNode next(CyNetworkAdapter network, CyNode node) {
			if(steps==maxSteps) restart();
			if(node.equals(target)) return null;
			CyNode n = super.next(network, node);
			return n;
		}
		
		@Override
		protected void restart(){
			steps = 0;
			visited.clear();
		}
	}
	
	public TargetedWalkStrategy(CyNode source, CyNode target, Integer maxSteps, 
			String weightColumn,
			Double restartProb){
		super(source,maxSteps,weightColumn,restartProb);
		this.target = target;
	}
	
	@Override
	public WalkStrategy.Run createRun() {
		return new Run();
	}
}
