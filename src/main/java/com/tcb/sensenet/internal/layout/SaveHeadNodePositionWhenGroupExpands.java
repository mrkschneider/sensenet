package com.tcb.sensenet.internal.layout;

import java.awt.geom.Point2D;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.events.NodeGroupAboutToCollapseEvent;
import com.tcb.sensenet.internal.events.NodeGroupAboutToCollapseListener;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;

public class SaveHeadNodePositionWhenGroupExpands implements NodeGroupAboutToCollapseListener {
	
	private AppGlobals appGlobals;

	public SaveHeadNodePositionWhenGroupExpands(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void handleEvent(NodeGroupAboutToCollapseEvent e) {
		CyNetworkAdapter network = e.getNetwork();
		if(shouldBreak(e,network)) return;
		
		CyNode headNode = e.getSource();
		saveGroupHeadNodePosition(network, headNode);
	}
	
	private boolean shouldBreak(NodeGroupAboutToCollapseEvent e, CyNetworkAdapter network){
		return (!appGlobals.state.metaNetworkManager.belongsToMetaNetwork(network)) || e.collapsing(); 
	}
	
	private void saveGroupHeadNodePosition(CyNetworkAdapter network, CyNode headNode){
		CyNetworkViewAdapter networkView = appGlobals.applicationManager.getCurrentNetworkView();
		Double x = networkView.getNodeView(headNode).getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		Double y = networkView.getNodeView(headNode).getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		NodePositionStore store = appGlobals.state.nodePositionStoreManager.get(network);
		store.putOrReplace(headNode, new Point2D.Double(x,y));
	}
}
