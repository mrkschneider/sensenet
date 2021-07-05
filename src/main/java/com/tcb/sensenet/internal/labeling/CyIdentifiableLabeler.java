package com.tcb.sensenet.internal.labeling;

import org.cytoscape.model.CyIdentifiable;

public interface CyIdentifiableLabeler<T extends CyIdentifiable> {
	public String generateLabel(T cyId);
}
