package com.tcb.sensenet.internal.meta.network;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.cytoscape.model.CyNetwork;

import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.sensenet.internal.util.BasicMapManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public class MetaNetworkManager extends BasicMapManager<Long,MetaNetwork,CyNetworkAdapter> {

private transient CyRootNetworkManagerAdapter rootNetworkManager;
private transient CyApplicationManagerAdapter applicationManager;
	
	public MetaNetworkManager(
			CyApplicationManagerAdapter applicationManager,
			CyRootNetworkManagerAdapter rootNetworkManager){
		this.applicationManager = applicationManager;
		this.rootNetworkManager = rootNetworkManager;
	}
	
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
		in.defaultReadObject();
		MetaObjectInputStream mIn = MetaObjectInputStream.create(in);
		this.applicationManager = mIn.appGlobals.applicationManager;
		this.rootNetworkManager = mIn.appGlobals.rootNetworkManager;
		
		CySessionAdapter session = mIn.session;
		SuidUpdater.update(getData(), session, CyNetwork.class);
	}
	
	@Override
	protected Long getKey(CyNetworkAdapter network){
		CyRootNetworkAdapter rootNetwork = rootNetworkManager.getRootNetwork(network);
		return rootNetwork.getSUID();
	}
		
	public MetaNetwork getCurrentMetaNetwork() {
		CyNetworkAdapter network = getCurrentNetwork();
		return get(network);
	}

	public CyNetworkAdapter getCurrentNetwork() {
		return applicationManager.getCurrentNetwork();
	}

	public CyNetworkViewAdapter getCurrentNetworkView() {
		return applicationManager.getCurrentNetworkView();
	}

	public Boolean currentNetworkBelongsToMetaNetwork() {
		if(!applicationManager.hasCurrentNetwork()) return false;
		return belongsToMetaNetwork(getCurrentNetwork());
	}
	
	public Boolean belongsToMetaNetwork(CyNetworkAdapter network){
		return containsKey(network);
	}
		
}
