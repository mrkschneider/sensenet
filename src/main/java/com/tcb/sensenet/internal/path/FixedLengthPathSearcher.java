package com.tcb.sensenet.internal.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class FixedLengthPathSearcher implements PathSearcher {

	private final CyNetworkAdapter network;
	private final Integer minLength;
	private final Integer maxLength;
	
	protected volatile boolean cancelled = false;

	
	public FixedLengthPathSearcher(
			CyNetworkAdapter network,
			Integer minLength,
			Integer maxLength){
		this.network = network;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}
	
	@Override
	public List<Path> getPaths(
			CyNode source, CyNode target
			) throws CancelledException {
		checkMaxLength(maxLength);
		Set<CyNode> candidates = 
				PathUtil.getNodesWithinDistance(target, maxLength, network);
		
		List<Path> paths = new ArrayList<>();
		paths.add(new Path(source));
		
		List<Path> resultPaths = new ArrayList<>();
		Integer pathCost = 0;
		
		while((!paths.isEmpty()) && pathCost <= maxLength) {
			PathUtil.checkTooManyPaths(paths);
			List<Path> addPaths = new ArrayList<>();
			for(Path path:paths){
				if(cancelled) 
					throw new CancelledException("Path search cancelled");
								
				CyNode last = path.getLast();
				Long suid = last.getSUID();
				
				if(!candidates.contains(last)){
					continue;
				}
																							
				if(suid.equals(target.getSUID())) {
					if(pathCost < minLength) continue;
					resultPaths.add(path);
					continue;
				} 
				
				addPaths.addAll(
						PathUtil.appendNocyclicNeighbors(path, network));
			}
			paths = addPaths;
			pathCost++;
		}
		return resultPaths;
	}
	
	
		
	private void checkMaxLength(Integer maxLength){
		if(maxLength < 1) 
			throw new IllegalArgumentException("maxLength must be >= 1");
	}
	
	@Override
	public void cancel() {
		cancelled = true;		
	}
}
