package com.tcb.sensenet.internal.UI.panels.appSettingsPanel;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.UI.panels.aboutPanel.AboutPanel;
import com.tcb.sensenet.internal.app.AppGlobals;

public class AboutMenuItem extends AbstractCyAction {

	private static final String menuName = CyActivator.APP_NAME_SHORT;
	private static final String panelName = "About " + CyActivator.APP_NAME_SHORT;
	private AppGlobals appGlobals;
	
	public AboutMenuItem(AppGlobals appGlobals) {
		super(panelName);
		setPreferredMenu("Apps." + menuName);
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new AboutPanel().setVisible(true);
	}
	
	

}
