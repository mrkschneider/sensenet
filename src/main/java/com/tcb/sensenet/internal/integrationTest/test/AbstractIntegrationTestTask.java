package com.tcb.sensenet.internal.integrationTest.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public abstract class AbstractIntegrationTestTask extends AbstractTask {

	protected Bundle bundle;
	protected AppGlobals appGlobals;

	protected abstract void runTest(TaskMonitor taskMonitor) throws Exception;
	public abstract TestReference getReference();	
		
	protected AbstractIntegrationTestTask(Bundle bundle, AppGlobals appGlobals){
		this.bundle = bundle;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		try{
			runTest(taskMonitor);
			System.out.println(this.getClass().getSimpleName() + ": OK");
		} catch(Exception e){
			System.out.println(this.getClass().getSimpleName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	protected void checkNodeColumnData(Columns column, CyRootNetworkAdapter rootNetwork, TestReference reference){
		Map<String,Map<String,Object>> refData = getRefNodeData(reference);
		List<CyRowAdapter> nodeRows = rootNetwork.getNodeList().stream()
				.map(n -> rootNetwork.getRow(n))
				.collect(Collectors.toList());
		checkColumnData(column, nodeRows, refData);
	}
	
	protected void checkEdgeColumnData(Columns column, CyRootNetworkAdapter rootNetwork, TestReference reference){
		Map<String,Map<String,Object>> refData = getRefEdgeData(reference);
		List<CyRowAdapter> edgeRows = rootNetwork.getEdgeList().stream()
				.map(n -> rootNetwork.getRow(n))
				.collect(Collectors.toList());
		Set<String> edgeRowNames = edgeRows.stream()
				.map(r -> r.get(DefaultColumns.SHARED_NAME, String.class))
				.collect(Collectors.toSet());
		// Metaedge source/target assignment is undefined - check for edges with reverse names
		for(String name: edgeRowNames){
			String reverseName = reverseName(name);
			if(!refData.containsKey(name) && refData.containsKey(reverseName)) 
				refData.put(name, refData.remove(reverseName));
		}
		checkColumnData(column, edgeRows, refData);
	}
	
	private String reverseName(String key){
		String[] split = key.split("__");
		if(split.length < 2) return key;
		String source = split[0];
		String target = split[1];
		String[] targetTypeSplit = target.split("_");
		target = targetTypeSplit[0];
		String interactionType = targetTypeSplit[1];
		return target + "__" + source + "_" + interactionType;
	}
	
	protected void checkColumnData(Columns column, List<CyRowAdapter> rows, Map<String,Map<String,Object>> refData){
		if(rows.size()!=refData.size()){
			throw new RuntimeException(
					String.format("Different number of existing vs. reference rows: %d vs. %d",
							rows.size(), refData.size()));
		}
		
		List<CyRowAdapter> sortedRows = new ArrayList<CyRowAdapter>();
		sortedRows.addAll(rows);
		sortedRows.sort((r1,r2) -> String.CASE_INSENSITIVE_ORDER.compare(
				r1.get(DefaultColumns.SHARED_NAME, String.class),
				r2.get(DefaultColumns.SHARED_NAME, String.class)));
		Function<List<?>,String> firstThreeToString = (List<?> list) -> {
			List<String> asStrings = list.subList(0,3).stream()
					.map(o -> {if(o!=null) return o.toString(); else return "null";})
					.collect(Collectors.toList());
			return String.join(",", asStrings);
		};
				
		List<String> checkFailedNames = new ArrayList<String>();
		List<Double> refWeights = new ArrayList<Double>();
		List<Double> weights = new ArrayList<Double>();
		for(CyRowAdapter row: rows){
			String name = row.get(DefaultColumns.SHARED_NAME, String.class);
			Double weight = row.getMaybe(column, Double.class)
					.orElse(null);
			Map<String,Object> refRow = refData.get(name);
			Double refWeight = (Double) refRow.get(column.toString());
			if(refWeight==null && weight==null) continue;
			if(refWeight==null && weight!=null || !refWeight.equals(weight)){
				checkFailedNames.add(name);
				refWeights.add(refWeight);
				weights.add(weight);
			}
		}
		if(!checkFailedNames.isEmpty()){
			String errorMessage = String.format("FAIL: Values in %s were not equal for (only first 3 shown): %s, %s != %s",
					column.toString(),
					firstThreeToString.apply(checkFailedNames),
					firstThreeToString.apply(refWeights),
					firstThreeToString.apply(weights));
			errorMessage += String.format("\n Total fails: %d",checkFailedNames.size());
			throw new RuntimeException(errorMessage);
		}	
	}
		
	protected String getTestDirectory(){
		return appGlobals.appProperties.getOrDefault(AppProperty.TEST_DIR);
	}
	
	protected Map<String,Map<String,Object>> getRefEdgeData(TestReference reference){
		Path path = Paths.get(getTestDirectory(),"testReference",reference.getEdgeResourcePath());
		return reference.read(path);
	}
	
	private Map<String,Map<String,Object>> getRefNodeData(TestReference reference){
		Path path = Paths.get(getTestDirectory(),"testReference",reference.getNodeResourcePath());
		return reference.read(path);
	}
	
}
