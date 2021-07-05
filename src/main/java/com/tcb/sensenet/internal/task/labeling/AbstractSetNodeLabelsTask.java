package com.tcb.sensenet.internal.task.labeling;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.labeling.CyIdentifiableLabeler;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public abstract class AbstractSetNodeLabelsTask extends AbstractTask {
	
	protected abstract CyIdentifiableLabeler<CyNode> createNodeLabeler(MetaNetwork metaNetwork);
	protected abstract CyIdentifiableLabeler<CyEdge> createEdgeLabeler(MetaNetwork metaNetwork);
	
	protected MetaNetworkManager metaNetworkManager;
	
	public AbstractSetNodeLabelsTask(MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
		
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		setNodeLabels(metaNetwork);
		setEdgeLabels(metaNetwork);
	}
	
	private void setNodeLabels(MetaNetwork metaNetwork){
		CyIdentifiableLabeler<CyNode> nodeRenamer = createNodeLabeler(metaNetwork);
		label(metaNetwork.getRootNetwork().getNodeList(),nodeRenamer, metaNetwork);
	}
	
	private void setEdgeLabels(MetaNetwork metaNetwork){
		CyIdentifiableLabeler<CyEdge> edgeRenamer = createEdgeLabeler(metaNetwork);
		label(metaNetwork.getRootNetwork().getEdgeList(),edgeRenamer, metaNetwork);
	}
	
	private <T extends CyIdentifiable> void label(List<T> cyIds, CyIdentifiableLabeler<T> renamer,
			MetaNetwork metaNetwork){
		for(T cyId: cyIds){
			String newName = renamer.generateLabel(cyId);
			CyRowAdapter row = metaNetwork.getRow(cyId);
			row.set(AppColumns.LABEL, newName);
		}
	}
				
}
