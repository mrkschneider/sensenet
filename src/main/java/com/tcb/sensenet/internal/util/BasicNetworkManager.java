package com.tcb.sensenet.internal.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.cytoscape.model.CyNetwork;

import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public abstract class BasicNetworkManager<T> extends BasicMapManager<Long,T,CyNetworkAdapter> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Long getKey(CyNetworkAdapter obj) {
		return obj.getSUID();
	}
	
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
		in.defaultReadObject();
		CySessionAdapter session = MetaObjectInputStream.create(in).session;
		SuidUpdater.update(getData(), session, CyNetwork.class);
	}
	

}
