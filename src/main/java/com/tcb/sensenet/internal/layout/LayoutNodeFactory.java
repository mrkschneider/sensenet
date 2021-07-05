package com.tcb.sensenet.internal.layout;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.View;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.LayoutNodeAdapter;

public class LayoutNodeFactory {
	
	public static List<LayoutNodeAdapter> createAll(CyNetworkAdapter network, CyNetworkViewAdapter networkView){
		List<LayoutNodeAdapter> layoutNodes = new ArrayList<LayoutNodeAdapter>();
		List<View<CyNode>> nodeViews = networkView.getNodeViews();
		for(View<CyNode> nodeView:nodeViews){
			CyNode node = nodeView.getModel();
			if(!network.containsNode(node)){
				continue;
			}
			CyRowAdapter nodeRow = network.getRow(node);
			LayoutNodeAdapter layoutNode = LayoutNodeAdapter.createFrom(nodeView, nodeViews.indexOf(nodeView), nodeRow);
			layoutNodes.add(layoutNode);
		}
		return layoutNodes;
	}
}
