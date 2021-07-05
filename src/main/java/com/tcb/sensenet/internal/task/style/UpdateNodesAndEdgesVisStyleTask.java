package com.tcb.sensenet.internal.task.style;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class UpdateNodesAndEdgesVisStyleTask extends AbstractTask {
	
	private VisualMappingManager visualMappingManager;
	private CyNetworkViewManagerAdapter networkViewManager;
	private final List<CyNode> selectedNodes;
	private final List<CyEdge> selectedEdges;
	private MetaNetworkManager metaNetworkManager;
	
	public UpdateNodesAndEdgesVisStyleTask(
			List<CyNode> nodes, List<CyEdge> edges,
			MetaNetworkManager metaNetworkManager,
			VisualMappingManager visualMappingManager,
			CyNetworkViewManagerAdapter networkViewManager){
		this.selectedNodes = nodes;
		this.selectedEdges = edges;
		this.metaNetworkManager = metaNetworkManager;
		this.visualMappingManager = visualMappingManager;
		this.networkViewManager = networkViewManager;
	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		tskMon.setStatusMessage("Updating visual styles...");
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
				
		for(CyNetworkAdapter network:metaNetwork.getSubNetworks()){
			for(CyNetworkView view:networkViewManager.getNetworkViews(network)){
				updateVisualStyle(view,network,rootNetwork);
			}
		}
			 		
	}
	
	
	private void updateVisualStyle(
			CyNetworkView view,
			CyNetworkAdapter network,
			CyRootNetworkAdapter rootNetwork){
		VisualStyle visualStyle = visualMappingManager.getVisualStyle(view);
		
		List<CyNode> nodes = null;
		List<CyEdge> edges = null;
		
		if(selectedNodes!=null){
			nodes = selectedNodes;
		} else {
			nodes = network.getNodeList();
		}
		
		if(selectedEdges!=null){
			edges = selectedEdges;
		} else {
			edges = network.getEdgeList();
		}
		
		for(CyNode node: nodes)	applyNodeVisStyle(visualStyle,rootNetwork,view,node);
		for(CyEdge edge: edges) applyEdgeVisStyle(visualStyle,rootNetwork,view,edge);
	}
	

	private void applyNodeVisStyle(VisualStyle visualStyle,
			CyRootNetworkAdapter rootNetwork, CyNetworkView view, CyNode node){
		View<CyNode> cyIdView = view.getNodeView(node);
		if(cyIdView==null) return;
		CyRowAdapter row = rootNetwork.getRow(node);
		visualStyle.apply(row.getAdaptedRow(),cyIdView);
	}
	
	private void applyEdgeVisStyle(VisualStyle visualStyle,
			CyRootNetworkAdapter rootNetwork, CyNetworkView view, CyEdge edge){
		View<CyEdge> cyIdView = view.getEdgeView(edge);
		if(cyIdView==null) return;
		CyRowAdapter row = rootNetwork.getRow(edge);
		visualStyle.apply(row.getAdaptedRow(),cyIdView);
	}
	
	
}
