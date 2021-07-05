package com.tcb.sensenet.internal.labeling;

public interface NodeLabelFactory {
	
	public String createLabel(String chain, String altLoc,
			String resName, String resInsert,
			Integer resIndex, String mutatedResname,
			String atomName, String groupTag);
	
}
