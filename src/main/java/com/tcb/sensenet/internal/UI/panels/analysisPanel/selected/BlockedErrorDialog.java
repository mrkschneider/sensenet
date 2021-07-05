package com.tcb.sensenet.internal.UI.panels.analysisPanel.selected;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.plot.LinePlotType;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.selection.InvalidSelectionException;
import com.tcb.sensenet.internal.selection.SingleEdgeSelection;
import com.tcb.sensenet.internal.task.correlation.TimelineCorrelationTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ShowTimelineCorrelationTaskFactory;
import com.tcb.sensenet.internal.task.plot.PlotSelectedEdgesTask;
import com.tcb.sensenet.internal.task.plot.PlotSelectedEdgesTaskConfig;
import com.tcb.sensenet.internal.task.plot.factories.PlotSelectedEdgesTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;



public class BlockedErrorDialog extends DefaultDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<FrameWeightMethod> weightMethodBox;
	private JTextField blocksBox;
	
	private static final AppProperty defaultWeightMethodProperty =
			AppProperty.BLOCKED_ERROR_DEFAULT_WEIGHT_METHOD;
	private static final AppProperty defaultBlocksProperty =
			AppProperty.BLOCKS_DEFAULT;

	private AppGlobals appGlobals;

	public BlockedErrorDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel();

		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set blocked error parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		addWeightMethodSelectionBox(this);		
	}
	
	private void addWeightMethodSelectionBox(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		weightMethodBox = p.addChoosableParameter("Frame weight", FrameWeightMethod.values(),
						appGlobals.appProperties.getEnumOrDefault(
						FrameWeightMethod.class,
						defaultWeightMethodProperty));
		blocksBox = p.addTextParameter("Blocks", 
				appGlobals.appProperties.getOrDefault(defaultBlocksProperty));
		target.add(p);
	}
		

	protected void confirm() {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		FrameWeightMethod weightMethod = (FrameWeightMethod) weightMethodBox.getSelectedItem();
		Integer blocks = Integer.valueOf(blocksBox.getText());
		
		List<CyEdge> edgeSelection = network.getSelectedEdges();
		LinePlotType plotType = LinePlotType.BLOCKED_STANDARD_ERROR;
				
		PlotSelectedEdgesTaskConfig config = PlotSelectedEdgesTaskConfig.create(
				edgeSelection, blocks, plotType, weightMethod);
		
		appGlobals.appProperties.set(defaultWeightMethodProperty, weightMethod.name());
		appGlobals.appProperties.set(defaultBlocksProperty, blocks.toString());
		
		TaskIterator tasks = new PlotSelectedEdgesTaskFactory(
				appGlobals).createTaskIterator(config);
		appGlobals.taskManager.execute(tasks);
		this.dispose();
	}

}
