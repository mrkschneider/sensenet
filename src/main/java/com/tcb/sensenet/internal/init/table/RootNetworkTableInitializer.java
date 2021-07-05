package com.tcb.sensenet.internal.init.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.InteractionImportData;
import com.tcb.aifgen.importer.InteractionList;




public class RootNetworkTableInitializer implements Initializer {

	private CyRootNetworkAdapter rootNetwork;

	public RootNetworkTableInitializer(
			CyRootNetworkAdapter rootNetwork
			){
		this.rootNetwork = rootNetwork;
	}
	
	public void init(){
		CyTableAdapter hiddenTable = rootNetwork.getHiddenNetworkTable();
		
		hiddenTable.createColumn(AppColumns.METATIMELINE_TYPE, String.class, false);
		hiddenTable.createColumn(DefaultColumns.TYPE, String.class, false);
		hiddenTable.createColumn(AppColumns.CUTOFF_VALUE, Double.class, false);
		hiddenTable.createColumn(AppColumns.AGGREGATION_MODE, String.class, false);
		hiddenTable.createColumn(AppColumns.SELECTED_FRAME, Integer.class, false);
		hiddenTable.createColumn(AppColumns.TIMELINE_LENGTH, Integer.class, false);
		hiddenTable.createColumn(AppColumns.TIMEPOINT_WEIGHT_CUTOFF, Double.class, false);
		hiddenTable.createListColumn(AppColumns.SELECTED_INTERACTION_TYPES, String.class, false);
		hiddenTable.createListColumn(AppColumns.AVAILABLE_INTERACTION_TYPES, String.class, false);
		hiddenTable.createColumn(AppColumns.SELECTED_CLUSTER_INDEX, Integer.class, false);
		hiddenTable.createColumn(AppColumns.CLUSTERING_MODE, String.class, false);
		hiddenTable.createColumn(AppColumns.CUTOFF_COLUMN, String.class, false);
	}
	
	
		
}
