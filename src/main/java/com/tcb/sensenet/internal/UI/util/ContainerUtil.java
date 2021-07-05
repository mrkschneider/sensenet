package com.tcb.sensenet.internal.UI.util;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import com.tcb.cytoscape.cyLib.util.ContainerUtils;

public class ContainerUtil {
	public static List<Component> getAllComponents(Container c){
		return ContainerUtils.getAllComponents(c);
	}
	
	public static void setAllEnabled(Container c, Boolean value){
		getAllComponents(c).forEach(comp -> comp.setEnabled(value));
	}
}
