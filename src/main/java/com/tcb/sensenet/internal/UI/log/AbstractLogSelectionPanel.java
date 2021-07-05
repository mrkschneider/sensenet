package com.tcb.sensenet.internal.UI.log;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.log.LogStore;
import com.tcb.cytoscape.cyLib.log.LogBuilder;



public abstract class AbstractLogSelectionPanel extends DefaultPanel {
	
	public abstract LogBuilder getLog();
}
