package com.tcb.sensenet.internal.session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyNetwork;

import com.tcb.sensenet.internal.app.AppPersistentState;
import com.tcb.sensenet.internal.layout.NodePositionStore;
import com.tcb.sensenet.internal.log.LogStore;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettings;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public class MetaNetworkSession implements Serializable {
	private static final long serialVersionUID = 5L;
	
	public AppPersistentState state;
			
	public MetaNetworkSession(
			AppPersistentState state
			){
		this.state = state;
	}	
}
