package com.tcb.sensenet.internal.task.layout.nodePlacement;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.layout.LayoutNodeFactory;
import com.tcb.sensenet.internal.layout.NodePositionStore;
import com.tcb.sensenet.internal.layout.nodePlacement.SubNodeOrbitPlacer;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.LayoutNodeAdapter;

public class SubNodeOrbitPlacementTask extends AbstractTask {

	private Double radius = 100d;
	private CyNode headNode;
	private MetaNetwork metaNetwork;
	private CyNetworkAdapter network;
	private CyNetworkViewAdapter networkView;
	private NodePositionStore nodePositionStore;

	public SubNodeOrbitPlacementTask(
			CyNode headNode,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network,
			CyNetworkViewAdapter networkView,
			NodePositionStore nodePositionStore){
		this.headNode = headNode;
		this.metaNetwork = metaNetwork;
		this.network = network;
		this.networkView = networkView;
		this.nodePositionStore = nodePositionStore;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {	
		List<LayoutNodeAdapter> layoutNodes = 
				LayoutNodeFactory.createAll(
						network,
						networkView);
		Set<CyNode> subNodes = metaNetwork.getSubnodes(headNode).stream()
				.collect(Collectors.toSet());
		List<LayoutNodeAdapter> layoutSubNodes = layoutNodes.stream()
					.filter(l -> subNodes.contains(l.getNode()))
					.collect(Collectors.toList());
		Point2D headCoordinates = nodePositionStore.get(headNode);
		SubNodeOrbitPlacer placer = new SubNodeOrbitPlacer(headCoordinates,layoutSubNodes,radius);
		placer.place();
		
		
	}

}
