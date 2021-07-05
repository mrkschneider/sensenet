package com.tcb.sensenet.internal.matrix;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;

import com.google.common.collect.ImmutableList;
import com.tcb.atoms.interactions.Interaction;
import com.tcb.cluster.DictStringComparator;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.util.DoubleArrayPrinter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.aifImporter.AifImporter;

public class NetworkMatrixWriter {
	
	private static final String separator = ",";
	
	private CyNetworkAdapter network;
	private MetaNetwork metaNetwork;

	private ContactMatrix contactMatrix;

	private String nodeNameColumn;

	private String weightColumn;

	public NetworkMatrixWriter(
			ContactMatrix contactMatrix,
			String weightColumn,
			String nodeNameColumn,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network){
		this.contactMatrix = contactMatrix;
		this.weightColumn = weightColumn;
		this.nodeNameColumn = nodeNameColumn;
		this.network = network;
		this.metaNetwork = metaNetwork;
	}
	
	public void write(Path path) throws IOException {
				
		List<CyNode> nodes = network.getNodeList().stream()
				.sorted(new NodeNameComparator())
				.collect(ImmutableList.toImmutableList());
		
		DoubleArrayPrinter arrayPrinter = new DoubleArrayPrinter(separator,3);
				
		List<String> lines = new ArrayList<>();
		String header = nodes.stream()
				.map(n -> getNodeName(n))
				.collect(Collectors.joining(separator));
		String firstCell = String.format(
				"Rows/Columns: %s; Values: %s" , nodeNameColumn, weightColumn);
				
		lines.add(firstCell + separator + header);
		double[][] matrix =	contactMatrix.getData(nodes);
			
		for(int i=nodes.size()-1;i>=0;i--){
			CyNode node = nodes.get(i);
			double[] row = matrix[i];
			String name = getNodeName(node);
			
			String s = arrayPrinter.toString(row);
			s = name + separator + s;
			lines.add(s);
		}
		
		String block = lines.stream()
				.collect(Collectors.joining("\n"));
		
		write(block, path);
	}
	
	private String getNodeName(CyNode node){
		return metaNetwork.getRow(node).getRaw(nodeNameColumn, Object.class).toString();
	}
		

		
	private void write(String block, Path path) throws IOException {
		Writer out = null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream(path.toString()), "utf-8"));
			out.write(block);
		} catch(IOException e){
			throw(e);
		} finally {
			if(out!=null) out.close();
		}		
	}
	
	private class NodeNameComparator implements Comparator<CyNode> {

		private Comparator<String> comp = new DictStringComparator();
		
		@Override
		public int compare(CyNode a, CyNode b) {
			String nameA = getNodeName(a);
			String nameB = getNodeName(b);
			return comp.compare(nameA, nameB);
		}
		
	}
}
