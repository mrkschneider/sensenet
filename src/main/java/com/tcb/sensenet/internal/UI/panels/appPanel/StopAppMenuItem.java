package com.tcb.sensenet.internal.UI.panels.appPanel;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;
import org.osgi.framework.BundleContext;

import com.tcb.sensenet.internal.CyActivator;

public class StopAppMenuItem extends AbstractCyAction {

	private static final String menuName = CyActivator.APP_NAME_SHORT;
	private static final String panelName = "Close";

	private CyActivator activator;
	private BundleContext bc;

	
	public StopAppMenuItem(CyActivator activator, BundleContext bc) {
		super(panelName);
		setPreferredMenu("Apps." + menuName);
		
		this.activator = activator;
		this.bc = bc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(askCancel()) return;
		activator.setShouldInit(false);
		activator.stop(bc);
		activator.addStartAppMenu(bc);
	}
	
	private Boolean askCancel(){
		int result = JOptionPane.showConfirmDialog(
				null,
				"Any open metanetworks will be lost. Continue?",
				"Warning",
				JOptionPane.OK_CANCEL_OPTION);
		if(result==JOptionPane.OK_OPTION) return false;
		else return true;
	}
	
	

}
