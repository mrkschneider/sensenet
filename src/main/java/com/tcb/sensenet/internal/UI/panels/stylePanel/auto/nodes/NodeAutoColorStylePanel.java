package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes;

import java.awt.Paint;
import java.util.List;

import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoColorStylePanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class NodeAutoColorStylePanel extends AbstractAutoColorStylePanel {

	public NodeAutoColorStylePanel(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected VisualProperty<Paint> getVisualProperty() {
		return BasicVisualLexicon.NODE_FILL_COLOR;
	}

	@Override
	protected AppProperty getDefaultLowColorProperty() {
		return AppProperty.AUTO_STYLE_NODE_DEFAULT_LOW_COLOR;
	}

	@Override
	protected AppProperty getDefaultMidColorProperty() {
		return AppProperty.AUTO_STYLE_NODE_DEFAULT_MID_COLOR;
	}

	@Override
	protected AppProperty getDefaultHighColorProperty() {
		return AppProperty.AUTO_STYLE_NODE_DEFAULT_HIGH_COLOR;
	}
		
	@Override
	protected List<CyRowAdapter> getRows() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		return getLocalAndSharedEdgeRows(rootNetwork.getNodeList());
	}

}
