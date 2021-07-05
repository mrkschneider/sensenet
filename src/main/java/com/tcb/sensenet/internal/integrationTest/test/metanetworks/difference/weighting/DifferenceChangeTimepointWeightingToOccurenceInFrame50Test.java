package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeTimepointWeightingToOccurenceInFrame50Test;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;

public class DifferenceChangeTimepointWeightingToOccurenceInFrame50Test extends ChangeTimepointWeightingToOccurenceInFrame50Test{
	
	public DifferenceChangeTimepointWeightingToOccurenceInFrame50Test(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}
	
	@Override
	public TestReference getReference() {
		return TestReference.OCCURENCE_WEIGHTED_TIMEPOINT_FRAME50_DIFFERENCE;
	}	

}
