package com.tcb.sensenet.internal.layout;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.sensenet.internal.util.BasicMapManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public final class NodePositionStore extends BasicMapManager<Long,Point2D,CyNode> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Long getKey(CyNode obj) {
		return obj.getSUID();
	}
	
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException{
		in.defaultReadObject();
		CySessionAdapter session = MetaObjectInputStream.create(in).session;
		SuidUpdater.update(getData(), session, CyNode.class);
	}

}
