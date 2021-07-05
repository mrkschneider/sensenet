package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.create;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.create.ImportMetaNetworkTest;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.OldMatImporter;
import com.tcb.aifgen.importer.differenceImporter.DifferenceImporter;





public class ImportDifferenceMetaNetworkTest extends ImportMetaNetworkTest {

	private static final String matComparedPathString = "test.mat";
	private static final String matReferencePathString = "test2.mat";
	
	public ImportDifferenceMetaNetworkTest(Bundle bundle, AppGlobals appGlobals) {
		super(bundle, appGlobals);
	}
	
	@Override
	protected InteractionImporter createInteractionImporter(){
		Path matComparedPath = Paths.get(getTestDirectory(),matComparedPathString);
		Path matReferencePath = Paths.get(getTestDirectory(),matReferencePathString);
		InteractionImporter comparedImporter = new OldMatImporter(matComparedPath);
		InteractionImporter referenceImporter = new OldMatImporter(matReferencePath);
		InteractionImporter differenceImporter = new DifferenceImporter(referenceImporter,comparedImporter);
		return differenceImporter;
	}
	
	@Override
	public TestReference getReference() {
		return TestReference.SUM_WEIGHTED_TIMELINE_DIFFERENCE;
	}
	
}
