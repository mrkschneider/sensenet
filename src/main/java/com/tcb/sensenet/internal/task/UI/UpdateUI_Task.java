package com.tcb.sensenet.internal.task.UI;

import java.util.List;

import javax.swing.SwingUtilities;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.UI.state.StateManager;

public class UpdateUI_Task extends AbstractTask {
	
	private List<StateManager<?>> stateManagers;

	public UpdateUI_Task(List<StateManager<?>> stateManagers){
		this.stateManagers = stateManagers;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		SwingUtilities.invokeLater(
				() -> stateManagers.forEach(m -> m.updateState()));
	}

}
