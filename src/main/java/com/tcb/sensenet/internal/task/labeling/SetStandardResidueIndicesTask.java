package com.tcb.sensenet.internal.task.labeling;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class SetStandardResidueIndicesTask extends AbstractTask {

	private CyApplicationManagerAdapter applicationManager;
	private CyRootNetworkManagerAdapter rootNetworkManager;

	public SetStandardResidueIndicesTask(CyApplicationManagerAdapter applicationManager,
			CyRootNetworkManagerAdapter rootNetworkManager){
		this.applicationManager = applicationManager;
		this.rootNetworkManager = rootNetworkManager;
	}
	
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = rootNetworkManager.getRootNetwork(network);
		CyTableAdapter table = rootNetwork.getSharedNodeTable();
		
		for(CyRowAdapter row:table.getAllRows()){
			Integer newResIndex =  row.get(AppColumns.RESINDEX, Integer.class);
			row.set(AppColumns.RESINDEX_LABEL, newResIndex);
			String resInsert = row.get(AppColumns.RESINSERT, String.class);
			row.set(AppColumns.RESINSERT_LABEL, resInsert);
		}
	}
		
}
