package com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.AbstractActionPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ActionCorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.common.util.SafeMap;


public class CorrelationFactorsSettingsPanel extends AbstractCorrelationFactorsSettingsPanel {
	private AppGlobals appGlobals;
	
	public CorrelationFactorsSettingsPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);
		
		this.setLayout(new GridLayout());
	}
	
	private void addGeneralOptionsPanel(Container target){
	}
	
	public CorrelationFactorsSettings getSettings(){
		return CorrelationFactorsSettings.create(Optional.empty(), Optional.empty());
	}
}
