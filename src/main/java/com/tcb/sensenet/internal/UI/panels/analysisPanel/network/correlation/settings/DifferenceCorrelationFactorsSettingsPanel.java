package com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.tcb.common.util.SafeMap;
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
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ActionCorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;

public class DifferenceCorrelationFactorsSettingsPanel extends AbstractCorrelationFactorsSettingsPanel {
	private JComboBox<MetaNetwork> refNetworkBox;
	private JComboBox<EdgeMappingMethod> mappingMethodBox;

	private AppProperty defaultRefNetworkNameProperty = 
			AppProperty.DIFFERENCE_CORRELATION_FACTORS_DEFAULT_REF_NETWORK_NAME;
	private AppProperty defaultMappingMethodProperty =
			AppProperty.DIFFERENCE_CORRELATION_FACTORS_DEFAULT_EDGE_MAPPING_METHOD;

	private AppGlobals appGlobals;
	
	public DifferenceCorrelationFactorsSettingsPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);
		
		this.setLayout(new GridLayout());
	}
	
	private void addGeneralOptionsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		Map<String,MetaNetwork> metaNetworks = getMetaNetworkMap();
		MetaNetwork defaultNetwork = metaNetworks.getOrDefault(
				appGlobals.appProperties.getOrDefault(defaultRefNetworkNameProperty),
				null
				);
						
		refNetworkBox =
				p.addChoosableParameter("Reference network", 
						metaNetworks.values().toArray(new MetaNetwork[0]),
						defaultNetwork);
		
		mappingMethodBox =
				p.addChoosableParameter("Edge mapping",
						EdgeMappingMethod.values(),
						EdgeMappingMethod.valueOf(
								appGlobals.appProperties.getOrDefault(defaultMappingMethodProperty)));
		
		target.add(p);
	}
	
	public CorrelationFactorsSettings getSettings(){
		Optional<MetaNetwork> refMetaNetwork = getReferenceMetaNetwork();
		Optional<EdgeMappingMethod> mappingMethod = Optional.of(
				(EdgeMappingMethod) mappingMethodBox.getSelectedItem());
		appGlobals.appProperties.set(
				defaultRefNetworkNameProperty, 
				refMetaNetwork.map(n -> n.toString()).get());
		appGlobals.appProperties.set(
				defaultMappingMethodProperty,
				mappingMethod.get().name());
		return CorrelationFactorsSettings.create(refMetaNetwork,mappingMethod);
	}
	
	private Optional<MetaNetwork> getReferenceMetaNetwork(){
		MetaNetwork refMetaNetwork = (MetaNetwork) refNetworkBox.getSelectedItem();
		if(refMetaNetwork==null) throw new IllegalArgumentException(
				"Need to choose a reference network");
		return Optional.of(refMetaNetwork); 
	}
	
	private Map<String,MetaNetwork> getMetaNetworkMap(){
		Map<String,MetaNetwork> result = new SafeMap<>();
		Collection<MetaNetwork> metaNetworks = appGlobals.state.metaNetworkManager.getData().values();
		for(MetaNetwork metaNetwork:metaNetworks){
			String name = null;
			try {
				/* Dirty workaround!
				 * Sometimes metanetwork cleanup does not work properly, e.g. when switching between
				 * sessions. Thus, the metanetwork still exists, but its associated tables are already deleted,
				 * leading to NPE for getting the name. The try block is designed to keep the exception from
				 * bubbling up. 
				 */
				name = metaNetwork.getName();
			} catch(Exception e) {
				System.out.println("Warning: Skipping invalid metanetwork.");
				continue;
			}

			if(result.containsKey(name)) {
				IllegalArgumentException ex = new IllegalArgumentException(
						"Please rename network collections to have unique names");
				SingletonErrorDialog.show(ex);
				throw(ex);
			}
			result.put(name, metaNetwork);			
		}
		return result;
	}
}
