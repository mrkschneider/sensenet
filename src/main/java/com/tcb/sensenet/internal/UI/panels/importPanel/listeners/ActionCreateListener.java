package com.tcb.sensenet.internal.UI.panels.importPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.app.AppGlobals;



public class ActionCreateListener implements ActionListener {
	
	private AppGlobals appGlobals;
		
	public ActionCreateListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ImportNetworkDialog dialog = new ImportNetworkDialog(appGlobals);
		dialog.setVisible(true);	
	}
		
}
