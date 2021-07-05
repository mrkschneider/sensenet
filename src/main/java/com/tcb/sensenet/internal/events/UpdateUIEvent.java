package com.tcb.sensenet.internal.events;

import org.cytoscape.event.CyEvent;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class UpdateUIEvent implements CyEvent<CyNetworkAdapter> {

	private CyNetworkAdapter network;

	public UpdateUIEvent(CyNetworkAdapter network) {
		this.network = network;
	}

	@Override
	public Class<?> getListenerClass() {
		return UpdateUIListener.class;
	}

	@Override
	public CyNetworkAdapter getSource() {
		return network;
	}

}
