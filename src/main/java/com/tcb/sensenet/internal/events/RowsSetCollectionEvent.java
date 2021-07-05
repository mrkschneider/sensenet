package com.tcb.sensenet.internal.events;

import java.util.List;

import org.cytoscape.event.CyEvent;
import org.cytoscape.model.events.RowsSetEvent;

public class RowsSetCollectionEvent implements CyEvent<List<RowsSetEvent>>{

	@Override
	public Class<?> getListenerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RowsSetEvent> getSource() {
		// TODO Auto-generated method stub
		return null;
	}

}
