package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeWeightingToOccurenceTest;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;

public class DifferenceChangeWeightingToOccurenceTest extends ChangeWeightingToOccurenceTest{
	
	public DifferenceChangeWeightingToOccurenceTest(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}
	
	@Override
	public TestReference getReference() {
		return TestReference.OCCURENCE_WEIGHTED_TIMELINE_DIFFERENCE;
	}	

}
