package com.tcb.sensenet.internal.init.table;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class RootEdgeTableInitializer implements Initializer {
	private CyRootNetworkAdapter rootNetwork;

	public RootEdgeTableInitializer(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
	}
				
	public void init(){
		CyTableAdapter rootEdgeTable = rootNetwork.getSharedEdgeTable();
		CyTableAdapter hiddenRootEdgeTable = rootNetwork.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
		rootEdgeTable.createColumn(AppColumns.LABEL, String.class, false);
		rootEdgeTable.createColumn(DefaultColumns.TYPE, String.class, false);
		rootEdgeTable.createColumn(AppColumns.SOURCE_NODE_NAME, String.class, false);
		rootEdgeTable.createColumn(AppColumns.TARGET_NODE_NAME, String.class, false);
		rootEdgeTable.createColumn(AppColumns.SOURCE_CHAIN, String.class, false);
		rootEdgeTable.createColumn(AppColumns.TARGET_CHAIN, String.class, false);
		rootEdgeTable.createColumn(AppColumns.WEIGHT, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.STANDARD_DEVIATION, Double.class,false);
		rootEdgeTable.createColumn(AppColumns.ERROR_ESTIMATE, Double.class,false);
		rootEdgeTable.createColumn(AppColumns.LIFETIME, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.AUTOCORRELATION_SAMPLE_SIZE, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.AUTOCORRELATION_TIME, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.OCCURRENCE, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.AVERAGE_INTERACTIONS, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.GROUP_TAG, String.class, false);
		rootEdgeTable.createColumn(AppColumns.DIVERGENCE, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.CONVERGENCE_TIME, Double.class, false);
		rootEdgeTable.createColumn(AppColumns.ENTROPY, Double.class, false);
				
		rootEdgeTable.createListColumn(AppColumns.BRIDGE_NAME, String.class, false);
		rootEdgeTable.createColumn(AppColumns.CORRELATION_FACTOR, Double.class, false);
				
		hiddenRootEdgeTable.createListColumn(AppColumns.TIMELINE, Double.class, false);
		hiddenRootEdgeTable.createColumn(AppColumns.IS_METAEDGE, Boolean.class, false);
		hiddenRootEdgeTable.createColumn(AppColumns.METAEDGE_SUID, Long.class, false);
		hiddenRootEdgeTable.createColumn(AppColumns.SELECTION_TIME, String.class, false);
		hiddenRootEdgeTable.createColumn(AppColumns.IMPORTED, Boolean.class, false);
	}
}


