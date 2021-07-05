package com.tcb.sensenet.internal.UI.panels.stylePanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.tcb.sensenet.internal.UI.panels.stylePanel.CustomizeLabelsDialog;
import com.tcb.sensenet.internal.app.AppGlobals;


public class ActionSetCustomNodeLabelsListener extends JFrame implements ActionListener {
	private AppGlobals appGlobals;
	
	public ActionSetCustomNodeLabelsListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JDialog dialog = new CustomizeLabelsDialog(appGlobals);
		dialog.setVisible(true);
		dialog.pack();
	}

}
