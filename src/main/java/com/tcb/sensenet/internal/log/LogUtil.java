package com.tcb.sensenet.internal.log;

import java.util.List;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public class LogUtil {
	
	public static void logNetwork(
			LogBuilder log,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network){
		CyRowAdapter hiddenRow = metaNetwork.getHiddenDataRow();
		CyRowAdapter sharedRow = metaNetwork.getSharedDataRow();
		String networkName = sharedRow.get(DefaultColumns.SHARED_NAME, String.class);
		List<String> activeInteractions = hiddenRow.getList(AppColumns.SELECTED_INTERACTION_TYPES, String.class);
		String weightMethod = FrameWeightMethod.valueOf(
				hiddenRow.get(AppColumns.METATIMELINE_TYPE, String.class))
				.toString();
		String cutoffColumn = 
				AppColumns.valueOf(hiddenRow.get(AppColumns.CUTOFF_COLUMN, String.class))
				.toString();
		Double cutoff = hiddenRow.get(AppColumns.CUTOFF_VALUE, Double.class);
				
		log.write("Metanetwork name: " + networkName);
		log.write("Active network name: " + 
		network.getDefaultNetworkTable().getRow(network.getSUID())
		.get(DefaultColumns.SHARED_NAME, String.class));
		log.write(String.format("Active interactions: %s", activeInteractions));
		log.write(String.format("Active edges: %d", network.getEdgeList().size()));
		log.write(String.format("Active nodes: %d", network.getNodeList().size()));
		
		writeTimelineWeightMode(hiddenRow,log);
		
		log.write(String.format("Frame weight: %s", weightMethod));
		log.write(String.format("Active edge cutoff column: %s", cutoffColumn));
		log.write(String.format("Active edge cutoff value: %s", cutoff));
		log.writeEmptyLine();
	}
	
	public static void logParameters(LogBuilder log, ParameterReporter o){
		log.write("Task parameters");
		log.write(o.reportParameters());
		log.writeEmptyLine();
	}
	
	private static void writeTimelineWeightMode(CyRowAdapter row, LogBuilder log){
		String weightModeEntry = row.get(AppColumns.AGGREGATION_MODE, String.class);
		TimelineWeightMethod weightMode = TimelineWeightMethod.valueOf(weightModeEntry);
		log.write(String.format("Timeline weight mode: %s", weightMode.toString()));
		
		if(weightMode.equals(TimelineWeightMethod.SINGLE_FRAME)){
			Integer selectedFrame = row.get(AppColumns.SELECTED_FRAME, Integer.class);
			log.write(String.format("Selected frame: %d", selectedFrame));
		} else if (weightMode.equals(TimelineWeightMethod.CLUSTERS)){
			Integer selectedCluster = row.get(AppColumns.SELECTED_CLUSTER_INDEX, Integer.class);
			log.write(String.format("Selected cluster: %d", selectedCluster));
		}
	}
		
}
