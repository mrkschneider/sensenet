package com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings.AbstractCorrelationFactorsSettingsPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings.CorrelationFactorsSettings;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings.CorrelationFactorsSettingsPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings.DifferenceCorrelationFactorsSettingsPanel;
import com.tcb.sensenet.internal.UI.util.AbstractActionPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
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
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ActionCorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.common.util.SafeMap;


public class CorrelationFactorsPanel extends AbstractActionPanel {
			
	private JComboBox<EdgeCorrelationMethod> correlationMethodBox;
	private JComboBox<FrameWeightMethod> weightMethodBox;
	private ComboBoxCardPanel<EdgeCorrelationMethod,AbstractCorrelationFactorsSettingsPanel> correlationMethodCards;

	private AppGlobals appGlobals;
	
	private AppProperty defaultWeightMethodProperty = AppProperty.CORRELATION_FACTORS_DEFAULT_WEIGHT_METHOD;
	
	public CorrelationFactorsPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);

	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		return c;
	}
	
	private void addGeneralOptionsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		correlationMethodBox =
				p.addChoosableParameter(
						"Correlation method",
						EdgeCorrelationMethod.values(),
						appGlobals.appProperties.getEnumOrDefault(
								EdgeCorrelationMethod.class,
								AppProperty.CORRELATION_FACTORS_DEFAULT_CORRELATION_METHOD));
		weightMethodBox =
				p.addChoosableParameter(
						"Frame weight",
						FrameWeightMethod.values(),
						appGlobals.appProperties.getEnumOrDefault(
								FrameWeightMethod.class, defaultWeightMethodProperty));
		
		correlationMethodCards = new ComboBoxCardPanel<>(correlationMethodBox);
		correlationMethodCards.addCard(EdgeCorrelationMethod.PEARSON,
				new CorrelationFactorsSettingsPanel(appGlobals));
		correlationMethodCards.addCard(EdgeCorrelationMethod.MUTUAL_INFORMATION,
				new CorrelationFactorsSettingsPanel(appGlobals));
		correlationMethodCards.addCard(EdgeCorrelationMethod.DIFFERENCE_MUTUAL_INFORMATION,
				new DifferenceCorrelationFactorsSettingsPanel(appGlobals));
				
		target.add(p);
		p.add(correlationMethodCards);
	}
	
	
			
	@Override
	public void submitAction() {
		CorrelationFactorsTaskConfig config = getTaskConfig();
				
		appGlobals.taskManager.execute(
				new ActionCorrelationFactorsTaskFactory(appGlobals).createTaskIterator(config)
				);
	}
	
	private CorrelationFactorsTaskConfig getTaskConfig(){
		CorrelationFactorsSettings settings = correlationMethodCards.getActiveCard()
				.getSettings();
		FrameWeightMethod weightMethod = (FrameWeightMethod)
				weightMethodBox.getSelectedItem();
		Optional<MetaNetwork> refMetaNetwork = settings.getReferenceMetaNetwork();
		EdgeCorrelationMethod correlationMethod = (EdgeCorrelationMethod)
				correlationMethodBox.getSelectedItem();
		Optional<EdgeMappingMethod> mappingMethod = settings.getEdgeMappingMethod();
				
		appGlobals.appProperties.set(AppProperty.CORRELATION_FACTORS_DEFAULT_CORRELATION_METHOD, correlationMethod.name());
		appGlobals.appProperties.set(AppProperty.CORRELATION_FACTORS_DEFAULT_WEIGHT_METHOD, weightMethod.name());
		
		RowWriter edgeTableWriter = new EdgeCorrelationFactorsWriter();
		
		return CorrelationFactorsTaskConfig.create(
				correlationMethod,weightMethod,refMetaNetwork, mappingMethod, edgeTableWriter);
	}
	
		
}
