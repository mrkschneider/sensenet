package com.tcb.sensenet.internal.properties.events;

import java.util.List;
import java.util.Map;

import org.cytoscape.application.events.CyShutdownEvent;
import org.cytoscape.application.events.CyShutdownListener;

import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.UI.panels.appPanel.AppPanel;
import com.tcb.sensenet.internal.UI.util.Accordeon;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppMapProperty;
import com.tcb.sensenet.internal.properties.AppProperties;


public class SaveUIStateBeforeShutdown implements CyShutdownListener {

	private AppGlobals appGlobals;
	
	public SaveUIStateBeforeShutdown(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void handleEvent(CyShutdownEvent e) {
		AppPanel p = appGlobals.stateManagers.appPanelStateManager.getRegisteredObject();
		List<Accordeon> accordeonPanels = p.getAccordeonPanels();
		
		Map<String,String> openStates = new SafeMap<>();
		for(Accordeon a:accordeonPanels){
			String key = a.getContentPanel().getClass().getName();
			String value = a.isOpen().toString();
			openStates.put(key, value);
		}
				
		AppProperties appProperties = appGlobals.appProperties;

		appProperties.setMap(AppMapProperty.UI_ACCORDEONS_OPEN, openStates);
	}

}
