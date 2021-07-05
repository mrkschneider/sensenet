package com.tcb.sensenet.internal.init.row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tcb.aifgen.importer.InteractionImportData;
import com.tcb.aifgen.importer.InteractionList;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class RootNetworkHiddenRowInitializer implements Initializer {

	private CyRootNetworkAdapter rootNetwork;
	private InteractionImportData importData;
	private ImportConfig config;

	public RootNetworkHiddenRowInitializer(
			ImportConfig importConfig,
			InteractionImportData importData,
			CyRootNetworkAdapter rootNetwork) {
		this.config = importConfig;
		this.importData = importData;
		this.rootNetwork = rootNetwork;
	}
	
	@Override
	public void init() {
		CyTableAdapter hiddenTable = rootNetwork.getHiddenNetworkTable();
		
		CyRowAdapter hiddenRow = hiddenTable.getRow(rootNetwork.getSUID());
		hiddenRow.set(DefaultColumns.TYPE, importData.getTimelineType().name());
		hiddenRow.set(AppColumns.CUTOFF_VALUE, config.getCutoffValue());
		hiddenRow.set(AppColumns.AGGREGATION_MODE, TimelineWeightMethod.AVERAGE_FRAME.name());
		hiddenRow.set(AppColumns.SELECTED_FRAME, 0);
		hiddenRow.set(AppColumns.TIMELINE_LENGTH,
				InteractionList.getTimelineLength(importData.getInteractions()));

		hiddenRow.set(AppColumns.TIMEPOINT_WEIGHT_CUTOFF, 0.01d);
		List<String> interactionTypes = 
				getSortedAvailableInteractionTypes(importData);
		hiddenRow.set(AppColumns.AVAILABLE_INTERACTION_TYPES, 
				interactionTypes);
		hiddenRow.set(AppColumns.SELECTED_INTERACTION_TYPES, interactionTypes);
		
		hiddenRow.set(AppColumns.SELECTED_CLUSTER_INDEX, 0);
		hiddenRow.set(AppColumns.CUTOFF_COLUMN, config.getCutoffColumn().name());
		
	}
	
	private List<String> getSortedAvailableInteractionTypes(InteractionImportData importer){
		List<String> types = 
				new ArrayList<String>(InteractionList.getInteractionTypes(importer.getInteractions()));
		Collections.sort(types);
		return types;
	}

}
