package com.tcb.sensenet.internal.task.labeling;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.labeling.CyIdentifiableLabeler;
import com.tcb.sensenet.internal.labeling.DefaultEdgeLabeler;
import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.labeling.NodeLabelFactory;
import com.tcb.sensenet.internal.labeling.NodeLabeler;
import com.tcb.sensenet.internal.labeling.ShortNodeLabelFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettingsManager;

public class ApplyNodeLabelsTask extends AbstractSetNodeLabelsTask {
	private MetaNetworkSettingsManager networkSettingsManager;

	public ApplyNodeLabelsTask(
			MetaNetworkManager metaNetworkManager,
			MetaNetworkSettingsManager networkSettingsManager){
		super(metaNetworkManager);
		this.networkSettingsManager = networkSettingsManager;
	}

	@Override
	public CyIdentifiableLabeler<CyNode> createNodeLabeler(MetaNetwork metaNetwork) {
		LabelSettings settings = networkSettingsManager.get(metaNetwork).labelSettings;
		NodeLabelFactory labelFactory = new ShortNodeLabelFactory(settings);
		return new NodeLabeler(metaNetwork.getRootNetwork(), labelFactory);
	}

	@Override
	public CyIdentifiableLabeler<CyEdge> createEdgeLabeler(MetaNetwork metaNetwork) {
		return new DefaultEdgeLabeler(metaNetwork.getRootNetwork());
	}

	
				
}
