package com.tcb.sensenet.internal.meta.timeline;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.sensenet.internal.util.BasicMapManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public class TimelineStore extends BasicMapManager<Long,MetaTimeline,CyEdge> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Long getKey(CyEdge obj) {
		return obj.getSUID();
	}
	
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
		in.defaultReadObject();
		CySessionAdapter session = MetaObjectInputStream.create(in).session;
		SuidUpdater.update(getData(), session, CyEdge.class);
	}

}
