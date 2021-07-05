package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.SelectFrame50InTimepointWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;

public class DifferenceSelectFrame50InTimepointWeightingTest extends SelectFrame50InTimepointWeightingTest{
	
	public DifferenceSelectFrame50InTimepointWeightingTest(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}
	
	@Override
	public TestReference getReference() {
		return TestReference.SUM_WEIGHTED_TIMEPOINT_FRAME50_DIFFERENCE;
	}	

}
