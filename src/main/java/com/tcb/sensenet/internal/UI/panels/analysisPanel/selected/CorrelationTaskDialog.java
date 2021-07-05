package com.tcb.sensenet.internal.UI.panels.analysisPanel.selected;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.selection.InvalidSelectionException;
import com.tcb.sensenet.internal.selection.SingleEdgeSelection;
import com.tcb.sensenet.internal.task.correlation.TimelineCorrelationTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ShowTimelineCorrelationTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;



public class CorrelationTaskDialog extends DefaultDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;

	private AppGlobals appGlobals;

	public CorrelationTaskDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel();

		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set correlation parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		JPanel p = new JPanel();
		
		p.setLayout(new GridLayout(1,1));
		
		addWeightMethodSelectionBox(p);
		
		this.add(p);
		
	}
	
	private void addWeightMethodSelectionBox(JPanel p){
		JLabel label = new JLabel("Frame weight");
		weightMethodSelectionBox = new JComboBox<>(FrameWeightMethod.values());
		label.setHorizontalAlignment(JLabel.CENTER);
		p.add(label);
		p.add(weightMethodSelectionBox);
	}
		

	protected void confirm() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		FrameWeightMethod weightMethod = (FrameWeightMethod) weightMethodSelectionBox.getSelectedItem();
		SingleEdgeSelection edgeSelection = getEdgeSelection(network);
		TimelineCorrelationTaskConfig config = 
				TimelineCorrelationTaskConfig.create(edgeSelection,weightMethod);
		appGlobals.taskManager.execute(
				new ShowTimelineCorrelationTaskFactory(appGlobals)
				.createTaskIterator(config));	
		this.dispose();
	}
	
	private SingleEdgeSelection getEdgeSelection(CyNetworkAdapter network){
		try{
			return SingleEdgeSelection.create(network);
		} catch(InvalidSelectionException e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
