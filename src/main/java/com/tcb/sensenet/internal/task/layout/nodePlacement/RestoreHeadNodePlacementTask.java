package com.tcb.sensenet.internal.task.layout.nodePlacement;

import java.awt.geom.Point2D;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.LayoutNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.layout.NodePositionStore;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.LayoutNodeAdapter;

public class RestoreHeadNodePlacementTask extends AbstractTask {
	
	private CyNode headNode;
	private CyNetworkViewAdapter networkView;
	private MetaNetwork metaNetwork;
	private NodePositionStore nodePositionStore;

	public RestoreHeadNodePlacementTask(
			CyNode headNode, 
			MetaNetwork metaNetwork, 
			CyNetworkViewAdapter networkView,
			NodePositionStore nodePositionStore){
		this.metaNetwork = metaNetwork;
		this.headNode = headNode;
		this.networkView = networkView;
		this.nodePositionStore = nodePositionStore;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		CyRowAdapter headRow = metaNetwork.getHiddenRow(headNode);
		Point2D position = nodePositionStore.getOrDefault(headNode, new Point2D.Double(0,0));
		LayoutNodeAdapter layoutNode = new LayoutNodeAdapter(
				new LayoutNode(networkView.getNodeView(headNode), 0, headRow.getAdaptedRow()));
		layoutNode.setX(position.getX());
		layoutNode.setY(position.getY());
		layoutNode.moveToLocation();
	}

}
