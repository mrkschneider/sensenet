package com.tcb.sensenet.internal.structureViewer;

import com.tcb.sensenet.internal.properties.AppProperty;


public class ViewerTypeProperties {
	public static AppProperty getProperty(ViewerType type){
		switch(type){
		case PYMOL: return AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_PYMOL;
		case VMD: return AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_VMD;
		case CHIMERA: return AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_CHIMERA;
		default: throw new IllegalArgumentException("Unknown Viewer type: " + type.name());
		}
	}
}
