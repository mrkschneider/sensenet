package com.tcb.sensenet.internal.integrationTest.test.reference;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.sensenet.internal.util.CollectorsUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.common.util.SafeMap;

public enum TestReference {
	OCCURENCE_WEIGHTED_TIMELINE, SUM_WEIGHTED_TIMELINE,
	OCCURENCE_WEIGHTED_TIMEPOINT_FRAME10, SUM_WEIGHTED_TIMEPOINT_FRAME10,
	OCCURENCE_WEIGHTED_TIMEPOINT_FRAME50, SUM_WEIGHTED_TIMEPOINT_FRAME50,
	LIFETIME, AUTOCORRELATION_ERROR_SUM_WEIGHTING, AUTOCORRELATION_ERROR_OCCURENCE_WEIGHTING,
	UNWEIGHTED_NODE_BETWEENNESS_CENTRALITY,
	OCCURENCE_WEIGHTED_TIMELINE_DIFFERENCE, SUM_WEIGHTED_TIMELINE_DIFFERENCE,
	OCCURENCE_WEIGHTED_TIMEPOINT_FRAME10_DIFFERENCE, SUM_WEIGHTED_TIMEPOINT_FRAME10_DIFFERENCE,
	OCCURENCE_WEIGHTED_TIMEPOINT_FRAME50_DIFFERENCE, SUM_WEIGHTED_TIMEPOINT_FRAME50_DIFFERENCE,
	CORRELATION_FACTOR, DIFFERENCE_CORRELATION_FACTOR;
		
	private static final String outBasePath = "";
			
	public String getNodeResourcePath(){
		return outBasePath + this.name() + ".nodes.serial";
	}
	
	public String getEdgeResourcePath(){
		return outBasePath + this.name() + ".edges.serial";
	}
	
	public String getBaseResourcePath(){
		return outBasePath;
	}
		
	public void writeNodeData(CyNetworkAdapter network){
		Map<String,Map<String,Object>> nodeData = getData(network, network.getNodeList());
		write(nodeData, getNodeResourcePath());
	}
	
	public void writeEdgeData(CyNetworkAdapter network){
		Map<String,Map<String,Object>> edgeData = getData(network, network.getEdgeList());
		write(edgeData, getEdgeResourcePath());
	}
	
	public void writeAllData(CyNetworkAdapter network){
		writeNodeData(network);
		writeEdgeData(network);
	}
			
	private Map<String,Map<String,Object>> getData(CyNetworkAdapter network, List<? extends CyIdentifiable> cyIds){
		Map<String,Map<String,Object>> data = cyIds.stream()
				.map(n -> network.getRow(n))
				.collect(CollectorsUtil.toMap(
						r -> r.get(DefaultColumns.SHARED_NAME,String.class),
						r -> r.getAllValues(),
						SafeMap::new));
		for(String keyA:data.keySet()){
			Map<String,Object> row = data.get(keyA);
			for(String key:row.keySet()){
				Object v = row.get(key);
				if (v instanceof List){
					row.put(key, new ArrayList<Object>((List<?>)v));
				}
			}
		}
		return data;
	}
	
	private void write(Map<String,Map<String,Object>> data, String path){
		 try {
	          FileOutputStream fileOut = new FileOutputStream(path);
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          out.writeObject(data);
	          out.close();
	          fileOut.close();
		 } catch (IOException e){
			 throw new RuntimeException(e);
		 }
		 
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Map<String,Object>> read(Path path){
		Map<String,Map<String,Object>> result;
		try{
			InputStream urlIn = new BufferedInputStream(new FileInputStream(path.toString()));
	        ObjectInputStream in = new ObjectInputStream(urlIn);
	        result = (Map<String, Map<String, Object>>) in.readObject();
	        in.close();
	        urlIn.close();
	      }catch(IOException|ClassNotFoundException e) {
	         throw new RuntimeException(e);	
	      }
		return result;
	}
}
