package com.tcb.sensenet.internal.UI.panels.stylePanel.auto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.view.vizmap.VisualMappingFunction;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.NetworkCollectionRowStatistics;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public abstract class AbstractAutoStylePanel extends DefaultPanel {
	
	protected AppGlobals appGlobals;

	public AbstractAutoStylePanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public abstract VisualMappingFunction<?,?> getVisualMappingFunction(String columnName);
	public abstract void updateTextFields(String columnName);
	
	protected List<CyRowAdapter> getLocalAndSharedEdgeRows(Collection<? extends CyIdentifiable> cyIds){
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		return new NetworkCollectionRowStatistics(
				Arrays.asList(network,rootNetwork))
				.getEdgeRows(cyIds);
	}
}
