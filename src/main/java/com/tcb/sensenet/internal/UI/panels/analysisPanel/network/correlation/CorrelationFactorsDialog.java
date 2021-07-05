package com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.AbstractActionPanel;
import com.tcb.sensenet.internal.UI.util.CardPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ActionCorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;


public class CorrelationFactorsDialog extends DefaultDialog {
	private static final AppProperty defaultAnalysisTypeProperty = 
			AppProperty.CORRELATION_FACTORS_DEFAULT_ANALYSIS_TYPE;

	private AppGlobals appGlobals;
	
	private ComboBoxCardPanel<CorrelationFactorsAnalysisType,AbstractActionPanel> cards;
	private JComboBox<CorrelationFactorsAnalysisType> correlationFactorsTypeBox;

	public CorrelationFactorsDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addCorrelationFactorsTypePanel(this);
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set correlation factor parameters");

		this.pack();
	}
	
	@Override
	protected GridBagConstraints getDefaultDialogConstraints(){
		GridBagConstraints c = super.getDefaultDialogConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private void addCorrelationFactorsTypePanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		correlationFactorsTypeBox = p.addChoosableParameter(
				"Correlation factor type", CorrelationFactorsAnalysisType.values(), 		
				appGlobals.appProperties
				.getEnumOrDefault(CorrelationFactorsAnalysisType.class, defaultAnalysisTypeProperty));
		cards = new ComboBoxCardPanel<>(correlationFactorsTypeBox);
		cards.addCard(CorrelationFactorsAnalysisType.NEIGHBOUR,
				new CorrelationFactorsPanel(appGlobals));
		//JPanelUtil.setBorders(cards, "Analysis settings");
				
		this.add(p);
		this.add(cards);
	}
			
	protected void confirm() {
		CorrelationFactorsAnalysisType correlationFactorsType = 
				(CorrelationFactorsAnalysisType) correlationFactorsTypeBox.getSelectedItem();
		
		appGlobals.appProperties.set(defaultAnalysisTypeProperty, correlationFactorsType.name());
				
		AbstractActionPanel actionPanel = cards.getActiveCard();
		actionPanel.submitAction();
		this.dispose();
	}
	
	
		
}
