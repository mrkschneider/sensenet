package com.tcb.sensenet.internal.task.labeling;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettingsManager;

public class SetDefaultNodeLabelSettingsTask extends AbstractTask {
	
	private MetaNetworkSettingsManager networkSettingsManager;
	private MetaNetworkManager metaNetworkManager;

	public SetDefaultNodeLabelSettingsTask(
			MetaNetworkManager metaNetworkManager,
			MetaNetworkSettingsManager networkSettingsManager){
		this.metaNetworkManager = metaNetworkManager;
		this.networkSettingsManager = networkSettingsManager;
	}

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		LabelSettings settings = new LabelSettings();
		networkSettingsManager.get(metaNetwork).labelSettings = settings;
	}

	

	
				
}
