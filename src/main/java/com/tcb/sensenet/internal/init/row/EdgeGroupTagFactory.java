package com.tcb.sensenet.internal.init.row;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class EdgeGroupTagFactory {
	public String create(CyRowAdapter sourceRow, CyRowAdapter targetRow){
		String sourceGroupTag = sourceRow.get(AppColumns.GROUP_TAG, String.class);
		String targetGroupTag = targetRow.get(AppColumns.GROUP_TAG, String.class);
		return String.format("%s-%s", sourceGroupTag, targetGroupTag);
	}
}
