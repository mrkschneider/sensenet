package com.tcb.sensenet.internal.UI.panels.stylePanel;

import java.awt.Container;

import com.tcb.sensenet.internal.UI.util.Accordeon;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;

public class StylePanel extends DefaultPanel {
	private AppGlobals appGlobals;
	
	public StylePanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
			
		addNodeLabelsPanel(this);
		addEdgeStylePanel(this);
		addDummyPanel();
	}
	
	
	private void addNodeLabelsPanel(Container target){
		Accordeon a = new Accordeon("Node style", new NodeStylePanel(appGlobals));
		target.add(a);
	}
	
	private void addEdgeStylePanel(Container target){
		Accordeon a = new Accordeon("Edge style", new EdgeStylePanel(appGlobals));
		target.add(a);
	}
						
}
