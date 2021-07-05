package com.tcb.sensenet.internal.UI.panels.analysisPanel.selected;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.selection.InvalidSelectionException;
import com.tcb.sensenet.internal.selection.SingleEdgeSelection;
import com.tcb.sensenet.internal.task.correlation.TimelineCorrelationTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ShowTimelineCorrelationTaskFactory;
import com.tcb.sensenet.internal.task.divergence.EdgeDivergenceTaskConfig;
import com.tcb.sensenet.internal.task.divergence.factories.ActionEdgeDivergenceTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;



public class EdgeDivergenceTaskDialog extends DefaultDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;
	private JComboBox<DivergenceMethod> divergenceMethodBox;
	private JTextField convergenceLimitBox;
	private JTextField blocksBox;
	
	private static final AppProperty defaultWeightMethodProperty = 
			AppProperty.DIVERGENCE_DEFAULT_WEIGHT_METHOD;
	private static final AppProperty defaultDivergenceMethodProperty =
			AppProperty.DIVERGENCE_DEFAULT_METHOD;
	private static final AppProperty defaultConvergenceLimitProperty =
			AppProperty.DIVERGENCE_DEFAULT_CONVERGENCE_LIMIT;
	private static final AppProperty defaultBlocksProperty = 
			AppProperty.BLOCKS_DEFAULT;

	private AppGlobals appGlobals;

	public EdgeDivergenceTaskDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel();

		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set divergence parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		divergenceMethodBox = 
				p.addChoosableParameter("Divergence method", DivergenceMethod.values(),
				appGlobals.appProperties.getEnumOrDefault(
						DivergenceMethod.class,
						defaultDivergenceMethodProperty));
		weightMethodSelectionBox = 
				p.addChoosableParameter("Frame weight", FrameWeightMethod.values(),
				appGlobals.appProperties.getEnumOrDefault(
								FrameWeightMethod.class,
								defaultWeightMethodProperty));
		blocksBox = p.addTextParameter("Blocks", appGlobals.appProperties.getOrDefault(
				defaultBlocksProperty));
		convergenceLimitBox = p.addTextParameter("Convergence limit",
				appGlobals.appProperties.getOrDefault(defaultConvergenceLimitProperty));
				
		this.add(p);
		
	}
	
	protected void confirm() {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		FrameWeightMethod weightMethod = (FrameWeightMethod) weightMethodSelectionBox.getSelectedItem();
		DivergenceMethod divergenceMethod = (DivergenceMethod) divergenceMethodBox.getSelectedItem();
		Double convergenceLimit = Double.valueOf(convergenceLimitBox.getText());
		SingleEdgeSelection edgeSelection = getEdgeSelection(network);
		Integer blocks = Integer.valueOf(blocksBox.getText());
			
		appGlobals.appProperties.set(defaultDivergenceMethodProperty, divergenceMethod.name());
		appGlobals.appProperties.set(defaultWeightMethodProperty,weightMethod.name());
		appGlobals.appProperties.set(defaultConvergenceLimitProperty, convergenceLimit.toString());
		appGlobals.appProperties.set(defaultBlocksProperty, blocks.toString());
		
		EdgeDivergenceTaskConfig config = 
				EdgeDivergenceTaskConfig.create(
						edgeSelection,weightMethod,
						divergenceMethod,blocks,
						convergenceLimit
						);
		ObjMap results = new ObjMap();
		appGlobals.taskManager.execute(
				new ActionEdgeDivergenceTaskFactory(appGlobals)
				.createTaskIterator(results,config));	
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
