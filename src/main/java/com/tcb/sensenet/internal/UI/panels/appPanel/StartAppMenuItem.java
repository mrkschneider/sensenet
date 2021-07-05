package com.tcb.sensenet.internal.UI.panels.appPanel;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.osgi.framework.BundleContext;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;

public class StartAppMenuItem extends AbstractCyAction {

	private static final String menuName = CyActivator.APP_NAME_SHORT;
	private static final String panelName = "Start";

	private CyActivator activator;
	private BundleContext bc;

	
	public StartAppMenuItem(CyActivator activator, BundleContext bc) {
		super(panelName);
		setPreferredMenu("Apps." + menuName);
		
		this.activator = activator;
		this.bc = bc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		activator.stop(bc);
		activator.setShouldInit(true);
		try{
			activator.start(bc);
		} catch(Exception ex){
			SingletonErrorDialog.showNonBlocking(ex);
			ex.printStackTrace();
			activator.addStartAppMenu(bc);
			return;
		}		
		
		
	}
	
	

}
