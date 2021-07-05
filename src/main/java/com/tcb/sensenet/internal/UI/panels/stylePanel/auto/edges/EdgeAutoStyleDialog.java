package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.edges;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoStyleDialog;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoStylePanel;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes.NodeAutoStyle;
import com.tcb.sensenet.internal.UI.util.CardPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class EdgeAutoStyleDialog extends AbstractAutoStyleDialog<EdgeAutoStyle> {
	
	private static final AppProperty defaultStyleProperty = AppProperty.AUTO_STYLE_EDGE_DEFAULT_PROPERTY;
	private static final AppProperty defaultStyleColumnProperty = AppProperty.AUTO_STYLE_EDGE_DEFAULT_COLUMN_NAME;

	public EdgeAutoStyleDialog(AppGlobals appGlobals) {
		super(appGlobals);
		this.setTitle("Set edge style");
	}

	@Override
	protected EdgeAutoStyle[] getAutoStyles() {
		return EdgeAutoStyle.values();
	}

	@Override
	protected CardPanel<AbstractAutoStylePanel,EdgeAutoStyle> createCardPanel() {
		CardPanel<AbstractAutoStylePanel,EdgeAutoStyle> cards = new CardPanel<>();
		cards.addCard(new EdgeAutoColorStylePanel(appGlobals), EdgeAutoStyle.COLOR);
		cards.addCard(new EdgeAutoWidthStylePanel(appGlobals), EdgeAutoStyle.WIDTH);
		return cards;
	}

	@Override
	protected CyTableAdapter getTable() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		return network.getDefaultEdgeTable();
	}
	
	@Override
	protected EdgeAutoStyle getDefaultAutoStyle() {
		EdgeAutoStyle defaultStyle = appGlobals.appProperties
				.getEnumOrDefault(EdgeAutoStyle.class, defaultStyleProperty);
		return defaultStyle;
	}
	
	@Override
	protected AppProperty getDefaultStyleColumnProperty() {
		return defaultStyleColumnProperty;
	}

	@Override
	protected AppProperty getDefaultStyleProperty() {
		return defaultStyleProperty;
	}
	
		
	
}
