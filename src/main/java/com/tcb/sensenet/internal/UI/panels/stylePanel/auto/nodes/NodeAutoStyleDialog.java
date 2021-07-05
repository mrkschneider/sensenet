package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes;

import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoStyleDialog;
import com.tcb.sensenet.internal.UI.panels.stylePanel.auto.AbstractAutoStylePanel;
import com.tcb.sensenet.internal.UI.util.CardPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class NodeAutoStyleDialog extends AbstractAutoStyleDialog<NodeAutoStyle> {
	
	private static final AppProperty defaultStyleProperty = AppProperty.AUTO_STYLE_NODE_DEFAULT_PROPERTY;
	private static final AppProperty defaultStyleColumnProperty = AppProperty.AUTO_STYLE_NODE_DEFAULT_COLUMN_NAME;
	
	public NodeAutoStyleDialog(AppGlobals appGlobals) {
		super(appGlobals);
		this.setTitle("Set node style");
	}

	@Override
	protected NodeAutoStyle[] getAutoStyles() {
		return NodeAutoStyle.values();
	}

	@Override
	protected CardPanel<AbstractAutoStylePanel,NodeAutoStyle> createCardPanel() {
		CardPanel<AbstractAutoStylePanel,NodeAutoStyle> cards = new CardPanel<>();
		cards.addCard(new NodeAutoColorStylePanel(appGlobals), NodeAutoStyle.COLOR);
		cards.addCard(new NodeAutoSizeStylePanel(appGlobals), NodeAutoStyle.SIZE);
		return cards;
	}

	@Override
	protected CyTableAdapter getTable() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		return network.getDefaultNodeTable();
	}

	@Override
	protected NodeAutoStyle getDefaultAutoStyle() {
		NodeAutoStyle defaultStyle = appGlobals.appProperties
				.getEnumOrDefault(NodeAutoStyle.class, defaultStyleProperty);
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
