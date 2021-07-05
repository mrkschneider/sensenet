package com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.view.factories.SetActiveInteractionTypesTaskFactory;

public class ActionChangeDisplayedInteractionTypesListener implements ActionListener {

	private AppGlobals appGlobals;
	private String interactionType;

	public ActionChangeDisplayedInteractionTypesListener(String interactionType,
			AppGlobals appGlobals
			){
		this.appGlobals = appGlobals;
		this.interactionType = interactionType;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> newSelection = getSortedNewInteractionTypeSelection(e);
		TaskIterator it = new TaskIterator();
		it.append(new SetActiveInteractionTypesTaskFactory(newSelection, appGlobals)
				.createTaskIterator());

		appGlobals.taskManager.execute(it);		
	}
	
	private List<String> getSortedNewInteractionTypeSelection(ActionEvent e){
		Set<String> selection = getCurrentInteractionTypeSelection();
		JCheckBox source = (JCheckBox)e.getSource();
		if(source.isSelected()){
			selection.add(interactionType);
		} else {
			selection.remove(interactionType);
		}
		
		List<String> result = new ArrayList<String>(selection);
		Collections.sort(result);
		return result;
	}
	
	private Set<String> getCurrentInteractionTypeSelection(){
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		return new HashSet<String>(
				metaNetwork.getHiddenDataRow().getList(AppColumns.SELECTED_INTERACTION_TYPES,
											String.class));
	}
	

}
