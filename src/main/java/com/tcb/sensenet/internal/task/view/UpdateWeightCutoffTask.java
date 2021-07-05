package com.tcb.sensenet.internal.task.view;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;

public class UpdateWeightCutoffTask extends AbstractTask {

	private CyApplicationManagerAdapter applicationManager;
	private MetaNetworkManager metaNetworkManager;
	private Double weightCutoff;
	private Columns cutoffColumn;
	
	public UpdateWeightCutoffTask(
			Double weightCutoff,
			Columns cutoffColumn,
			CyApplicationManagerAdapter applicationManager,
			MetaNetworkManager metaNetworkManager){
		this.weightCutoff = weightCutoff;
		this.cutoffColumn = cutoffColumn;
		this.applicationManager = applicationManager;
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		MetaNetwork metaNetwork = metaNetworkManager.get(network);
		
		metaNetwork.getHiddenDataRow().set(AppColumns.CUTOFF_COLUMN, cutoffColumn.name());
		metaNetwork.getHiddenDataRow().set(AppColumns.CUTOFF_VALUE, weightCutoff);
	}

}
