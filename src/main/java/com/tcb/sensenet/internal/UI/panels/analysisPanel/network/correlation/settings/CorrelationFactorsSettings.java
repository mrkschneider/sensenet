package com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.settings;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;

@AutoValue
public abstract class CorrelationFactorsSettings {
	
	public static CorrelationFactorsSettings create(
			Optional<MetaNetwork> refMetaNetwork,
			Optional<EdgeMappingMethod> edgeMappingMethod){
		return new AutoValue_CorrelationFactorsSettings(refMetaNetwork,edgeMappingMethod);
	}

	public abstract Optional<MetaNetwork> getReferenceMetaNetwork();
	public abstract Optional<EdgeMappingMethod> getEdgeMappingMethod();
	
	
	
}
