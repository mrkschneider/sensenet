package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.create;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;

public class ImportRefMetaNetworkTest extends ImportMetaNetworkTest {

	public ImportRefMetaNetworkTest(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}

	@Override
	protected String getNetworkName(){
		return "ref-test-network";
	}
}
