package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ActivateTimepointWeightingInFrame10Test;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;

public class DifferenceActivateTimepointWeightingInFrame10Test extends ActivateTimepointWeightingInFrame10Test{
	
	public DifferenceActivateTimepointWeightingInFrame10Test(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}
	
	@Override
	public TestReference getReference() {
		return TestReference.OCCURENCE_WEIGHTED_TIMEPOINT_FRAME10_DIFFERENCE;
	}	

}
