package com.tcb.sensenet.internal.path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.util.CancelledException;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class ShortestPathSearcher implements PathSearcher {

	private final CyNetworkAdapter network;
	protected volatile Boolean cancelled = false;
	
	public ShortestPathSearcher(CyNetworkAdapter network) {
		this.network = network;
	}

	@Override
	public List<Path> getPaths(CyNode source, CyNode target) throws CancelledException {
		List<Path> resultPaths = new ArrayList<>();
		List<Path> paths = new ArrayList<>();
		paths.add(new Path(source));
		
		Set<CyNode> visited = new HashSet<>();

		while(resultPaths.isEmpty()){
			PathUtil.checkTooManyPaths(paths);
			List<Path> addPaths = new ArrayList<>();
			List<CyNode> levelNodes = new ArrayList<>();
			for(Path path:paths){
				if(cancelled) 
					throw new CancelledException("Path search cancelled");
				
				CyNode last = path.getLast();
				Long suid = last.getSUID();
				levelNodes.add(last);
				
				if(suid.equals(target.getSUID())) {
					resultPaths.add(path);
					continue;
				}
				
				if(visited.contains(last)){
					continue;
				}
				
				addPaths.addAll(
						PathUtil.appendNocyclicNeighbors(path, network));
			}
			visited.addAll(levelNodes);
			paths = addPaths;
		}
		return resultPaths;
	}

	@Override
	public void cancel() {
		cancelled = true;		
	}
	
	

}
