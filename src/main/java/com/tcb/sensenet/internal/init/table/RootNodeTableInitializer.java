package com.tcb.sensenet.internal.init.table;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class RootNodeTableInitializer implements Initializer {
	
	private CyRootNetworkAdapter rootNetwork;

	public RootNodeTableInitializer(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
	}
		
	public void init(){
		CyTableAdapter sharedTable = rootNetwork.getSharedNodeTable();
		CyTableAdapter hiddenTable = rootNetwork.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		sharedTable.createColumn(AppColumns.LABEL, String.class, false);
		sharedTable.createColumn(AppColumns.RESINDEX_LABEL, Integer.class, false);
		sharedTable.createColumn(AppColumns.RESINSERT_LABEL, String.class, false);
		sharedTable.createColumn(AppColumns.ATOM_NAME,String.class,false);
		sharedTable.createColumn(AppColumns.CHAIN,String.class,false);
		sharedTable.createColumn(AppColumns.RESINDEX,Integer.class,false);
		sharedTable.createColumn(AppColumns.RESNAME,String.class,false);
		sharedTable.createColumn(DefaultColumns.TYPE, String.class, false);
		sharedTable.createColumn(AppColumns.GROUP_TAG, String.class, false);
		sharedTable.createColumn(AppColumns.MUTATED_RESNAME, String.class, false);
		sharedTable.createColumn(AppColumns.ALTLOC, String.class, false);
		sharedTable.createColumn(AppColumns.RESINSERT, String.class, false);
		sharedTable.createColumn(AppColumns.SECONDARY_STRUCTURE, String.class, false);
		sharedTable.createColumn(AppColumns.CORRELATION_FACTOR, Double.class, false);
		sharedTable.createColumn(AppColumns.DEGREE, Double.class, false);
		
		hiddenTable.createColumn(AppColumns.IS_METANODE, Boolean.class, false);
		hiddenTable.createColumn(AppColumns.METANODE_SUID, Long.class, false);
		hiddenTable.createColumn(AppColumns.SELECTION_TIME, String.class, false);
		hiddenTable.createColumn(AppColumns.IMPORTED, Boolean.class, false);
	}
	
}
