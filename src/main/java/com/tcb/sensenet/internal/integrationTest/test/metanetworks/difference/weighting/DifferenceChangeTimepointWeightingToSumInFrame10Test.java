package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeTimepointWeightingToSumInFrame10Test;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;

public class DifferenceChangeTimepointWeightingToSumInFrame10Test extends ChangeTimepointWeightingToSumInFrame10Test{
	
	public DifferenceChangeTimepointWeightingToSumInFrame10Test(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}
	
	@Override
	public TestReference getReference() {
		return TestReference.SUM_WEIGHTED_TIMEPOINT_FRAME10_DIFFERENCE;
	}	

}
