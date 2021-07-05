package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes;

import java.util.List;

import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoSizeStylePanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class NodeAutoSizeStylePanel extends AbstractAutoSizeStylePanel {

	public NodeAutoSizeStylePanel(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected List<CyRowAdapter> getRows() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		return getLocalAndSharedEdgeRows(rootNetwork.getNodeList());
	}

	@Override
	protected VisualProperty<Double> getVisualProperty() {
		return BasicVisualLexicon.NODE_SIZE;
	}

	@Override
	protected AppProperty getDefaultMinSizeProperty() {
		return AppProperty.AUTO_STYLE_NODE_DEFAULT_MIN_SIZE;
	}

	@Override
	protected AppProperty getDefaultMaxSizeProperty() {
		return AppProperty.AUTO_STYLE_NODE_DEFAULT_MAX_SIZE;
	}

}
