package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.cytoscape.work.TaskIterator;

import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateClusterWeightingTaskFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateSingleFrameWeightingTaskFactory;

public class ActionChangeEdgeWeightingListener extends JFrame implements ActionListener {

	private AppGlobals appGlobals;
	private FrameWeightMethod method;

	public ActionChangeEdgeWeightingListener(AppGlobals appGlobals,
			FrameWeightMethod method) {
		this.appGlobals = appGlobals;
		this.method = method;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		
		TimelineWeightMethod mode = TimelineWeightMethod.valueOf(
				metaNetwork.getHiddenDataRow().get(AppColumns.AGGREGATION_MODE, String.class));
				
		TaskIterator tskIt = new TaskIterator();

		tskIt.append(createAggregationTasks(metaNetwork, mode));
        appGlobals.taskManager.execute(tskIt);          
		
	}
	
	private TaskIterator createAggregationTasks(MetaNetwork metaNetwork, TimelineWeightMethod mode){
		switch(mode){
		
		case AVERAGE_FRAME:
			return new ActivateAverageFrameWeightingTaskFactory(appGlobals,method)
					.createTaskIterator();
		case SINGLE_FRAME:
			return new ActivateSingleFrameWeightingTaskFactory(
					appGlobals, method,
					getSelectedFrame(metaNetwork))
					.createTaskIterator();
		case CLUSTERS:
			Integer selectedClusterIndex = metaNetwork.getHiddenDataRow()
			.get(AppColumns.SELECTED_CLUSTER_INDEX, Integer.class);
			return new ActivateClusterWeightingTaskFactory(
					appGlobals,method,
					selectedClusterIndex, getCurrentClusterSelection(metaNetwork, selectedClusterIndex))
					.createTaskIterator();
		default:
			throw new IllegalArgumentException("Unknown AggregationMode");
		}
	}
	
	private Integer getSelectedFrame(MetaNetwork metaNetwork){
		return metaNetwork.getHiddenDataRow().get(AppColumns.SELECTED_FRAME, Integer.class);
	}
	
	private Cluster getCurrentClusterSelection(MetaNetwork metaNetwork, Integer selectedClusterIndex){
		return appGlobals.state.clusteringStoreManager.get(metaNetwork)
				.get(selectedClusterIndex);
	}
	
}
