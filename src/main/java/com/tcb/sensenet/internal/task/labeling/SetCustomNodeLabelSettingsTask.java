package com.tcb.sensenet.internal.task.labeling;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettingsManager;

public class SetCustomNodeLabelSettingsTask extends AbstractTask {

	private MetaNetworkSettingsManager networkSettingsManager;
	private LabelSettings labelSettings;
	private MetaNetworkManager metaNetworkManager;

	public SetCustomNodeLabelSettingsTask(
			MetaNetworkManager metaNetworkManager,
			MetaNetworkSettingsManager networkSettingsManager,
			LabelSettings labelSettings){
		this.metaNetworkManager = metaNetworkManager;
		this.networkSettingsManager = networkSettingsManager;
		this.labelSettings = labelSettings;
	}

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		networkSettingsManager.get(metaNetwork).labelSettings = labelSettings;
	}

	

	
				
}
