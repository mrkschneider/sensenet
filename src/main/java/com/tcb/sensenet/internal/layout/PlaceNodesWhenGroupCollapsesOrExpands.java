package com.tcb.sensenet.internal.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.events.NodeGroupCollapsedEvent;
import com.tcb.sensenet.internal.events.NodeGroupCollapsedListener;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.task.layout.nodePlacement.RestoreHeadNodePlacementTask;
import com.tcb.sensenet.internal.task.layout.nodePlacement.SubNodeOrbitPlacementTask;
import com.tcb.sensenet.internal.task.style.factories.UpdateNodesAndEdgesVisualStyleTaskFactory;
import com.tcb.sensenet.internal.task.view.factories.UpdateActiveEdgesTaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class PlaceNodesWhenGroupCollapsesOrExpands implements NodeGroupCollapsedListener {
	
	private AppGlobals appGlobals;

	public PlaceNodesWhenGroupCollapsesOrExpands(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void handleEvent(NodeGroupCollapsedEvent e) {
		CyNetworkAdapter network = e.getNetwork();
		if(shouldBreak(network)) return;
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		CyNetworkViewAdapter view = appGlobals.state.metaNetworkManager.getCurrentNetworkView();
		CyNode group = e.getSource();
				
		TaskIterator taskIt = new TaskIterator();
			
		if(e.collapsed()){
			taskIt.append(createRestoreHeadNodePlacementTasks(group,metaNetwork,network,view));
		}
		else{
			taskIt.append(createPlaceSubNodesTasks(group,metaNetwork,network,view));
		}

		taskIt.append(
				new UpdateActiveEdgesTaskFactory(appGlobals)
					.createTaskIterator()
				);
		
		appGlobals.synTaskManager.execute(taskIt);
					
	}
	
	private boolean shouldBreak(CyNetworkAdapter network){
		CyNetworkViewManagerAdapter networkViewManager = appGlobals.networkViewManager;
		MetaNetworkManager metaNetworkManager = appGlobals.state.metaNetworkManager;
		if(!metaNetworkManager.belongsToMetaNetwork(network) 
				|| networkViewManager.getNetworkViews(network).size() == 0) return true;
		return false;
	}
	
	private List<CyEdge> getAdjacentEdges(CyNetworkAdapter network, List<CyNode> nodes){
		return nodes.stream()
				.map(n -> network.getAdjacentEdgeList(n, CyEdge.Type.ANY))
				.flatMap(e -> e.stream())
				.collect(Collectors.toList());
	}
	
	private List<CyEdge> getAdjacentEdges(CyNetworkAdapter network, CyNode node){
		return getAdjacentEdges(network, Arrays.asList(node));
	}
	
	private TaskIterator createPlaceSubNodesTasks(CyNode group,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network,
			CyNetworkViewAdapter networkView){
		TaskIterator tasks = new TaskIterator();
		List<CyNode> subNodes = metaNetwork.getSubnodes(group);
		NodePositionStore posStore = appGlobals.state.nodePositionStoreManager.get(network);
		Task subNodeOrbitPlacementTask = new SubNodeOrbitPlacementTask(
				group,
				metaNetwork,
				network,
				networkView, posStore);
		TaskIterator updateVisStyleTasks = 
				new UpdateNodesAndEdgesVisualStyleTaskFactory(appGlobals)
					.createTaskIterator(new ArrayList<CyNode>(subNodes), 
						getAdjacentEdges(network, subNodes));
		tasks.append(subNodeOrbitPlacementTask);
		tasks.append(updateVisStyleTasks);
		return tasks;
	}
	
	private TaskIterator createRestoreHeadNodePlacementTasks(
			CyNode group,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network,
			CyNetworkViewAdapter networkView){
		TaskIterator tasks = new TaskIterator();
		NodePositionStore posStore = appGlobals.state.nodePositionStoreManager.get(network);
		Task restoreHeadNodePlacementTask = new RestoreHeadNodePlacementTask(
				group,
				metaNetwork,
				networkView, posStore
				);
		TaskIterator updateVisStyleTasks = 
				new UpdateNodesAndEdgesVisualStyleTaskFactory(appGlobals)
					.createTaskIterator(Arrays.asList(group), getAdjacentEdges(network,group));
		tasks.append(restoreHeadNodePlacementTask);
		tasks.append(updateVisStyleTasks);
		return tasks;
	}
	
}


