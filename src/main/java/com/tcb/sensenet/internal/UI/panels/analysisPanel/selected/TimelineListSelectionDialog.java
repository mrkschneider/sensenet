package com.tcb.sensenet.internal.UI.panels.analysisPanel.selected;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.meta.ShowMetaTimelinesTaskConfig;
import com.tcb.sensenet.internal.task.meta.factories.ShowMetaTimelinesTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;



public class TimelineListSelectionDialog extends DefaultDialog {

	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;
	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightMethodProperty = AppProperty.TIMELINE_LIST_DEFAULT_WEIGHT_METHOD;

	public TimelineListSelectionDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		addGeneralOptionsPanel();

		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set timeline parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		FrameWeightMethod defaultWeightMethod = appGlobals.appProperties
				.getEnumOrDefault(FrameWeightMethod.class, defaultWeightMethodProperty);
		
		weightMethodSelectionBox = p.addChoosableParameter("Frame weight", FrameWeightMethod.values(),
				defaultWeightMethod);
				
		this.add(p);
	}
	
	protected void confirm() {
		FrameWeightMethod weightMethod = (FrameWeightMethod) weightMethodSelectionBox.getSelectedItem();
		
		appGlobals.appProperties.set(defaultWeightMethodProperty, weightMethod.name());
		
		ShowMetaTimelinesTaskConfig config = ShowMetaTimelinesTaskConfig.create(weightMethod);
				
		ShowMetaTimelinesTaskFactory fac = new ShowMetaTimelinesTaskFactory(appGlobals);
		appGlobals.taskManager.execute(fac.createTaskIterator(config));
		this.dispose();
	}

}
