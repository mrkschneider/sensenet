package com.tcb.sensenet.internal.UI.panels.appSettingsPanel;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;

public class ResetDefaultSettingsMenuItem extends AbstractCyAction {

	private static final String menuName = CyActivator.APP_NAME_SHORT;
	private static final String panelName = "Reset app settings...";
	private AppGlobals appGlobals;
	
	public ResetDefaultSettingsMenuItem(AppGlobals appGlobals) {
		super(panelName);
		setPreferredMenu("Apps." + menuName);
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int answer = JOptionPane.showConfirmDialog(
	            null,
	            "This will delete all user app settings and reset them to their defaults. Proceed?",
	            "Confirm reset",
	            JOptionPane.YES_NO_OPTION);
		if(answer==JOptionPane.YES_OPTION){
			appGlobals.appProperties.resetDefaults();
		}
	}
	
	

}
