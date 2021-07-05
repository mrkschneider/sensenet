package com.tcb.sensenet.internal.UI.panels.structureViewerPanel;

import java.awt.Container;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;

public class StructureViewerPanel extends DefaultPanel {
	private AppGlobals appGlobals;
		
	private JPanel viewerStatusPanel;



	public StructureViewerPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		addViewerStatusPanel(this);

		
		addDummyPanel();

		appGlobals.stateManagers.viewerPanelStateManager.register(this);
	}
	
	private void addViewerStatusPanel(Container target){
		JPanel p = new ViewerStatusPanel(appGlobals);
		target.add(p);
		viewerStatusPanel = p;
	}
		
	
	
	
	
				
}
