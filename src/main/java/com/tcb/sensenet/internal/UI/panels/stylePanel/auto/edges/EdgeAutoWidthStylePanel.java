package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.edges;

import java.util.List;

import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoSizeStylePanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class EdgeAutoWidthStylePanel extends AbstractAutoSizeStylePanel {

	public EdgeAutoWidthStylePanel(AppGlobals appGlobals) {
		super(appGlobals);
	}

	@Override
	protected List<CyRowAdapter> getRows() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		return getLocalAndSharedEdgeRows(rootNetwork.getEdgeList());
	}

	@Override
	protected VisualProperty<Double> getVisualProperty() {
		return BasicVisualLexicon.EDGE_WIDTH;
	}

	@Override
	protected AppProperty getDefaultMinSizeProperty() {
		return AppProperty.AUTO_STYLE_EDGE_DEFAULT_MIN_WIDTH;
	}

	@Override
	protected AppProperty getDefaultMaxSizeProperty() {
		return AppProperty.AUTO_STYLE_EDGE_DEFAULT_MAX_WIDTH;
	}

}
