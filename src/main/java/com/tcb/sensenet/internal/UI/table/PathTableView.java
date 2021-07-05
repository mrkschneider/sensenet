package com.tcb.sensenet.internal.UI.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.path.NodeNamePrinter;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.PathUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.common.util.Rounder;

public class PathTableView extends TableView {

	private List<Path> paths;
	private CyNetworkAdapter network;

	@SuppressWarnings("unchecked")
	public PathTableView(CyNetworkAdapter network, ObjMap results, FileUtil fileUtil) {
		super(
				createColumnNames(),
				createData(network,results,createColumnNames()),
				fileUtil);
		this.paths = results.get("paths",List.class);
		this.network = network;

		addSelectRowListener();
	}
	
	private static String[] createColumnNames(){
		String[] columns = {"Path id","source","target","length","intermediates","timeline sum","timeline occurrence"};
		return columns;
	}
		
	private static Object[][] createData(CyNetworkAdapter network, ObjMap results, String[] columns){
		NodeNamePrinter printer = new NodeNamePrinter(network);
		@SuppressWarnings("unchecked")
		List<Path> paths = results.get("paths",List.class);
		@SuppressWarnings("unchecked")
		List<ObjMap> pathWeights = results.get("pathWeights",List.class);
		final int size = paths.size();
		Object[][] data = new Object[size][columns.length];
		for(int i=0;i<size;i++){
			Path path = paths.get(i);
			ObjMap weightAnalysis = pathWeights.get(i);
			data[i][0] = i;
			data[i][1] = printer.toString(path.getFirst());
			data[i][2] = printer.toString(path.getLast());
			data[i][3] = path.getLength();
			data[i][4] = printer.toString(path.getNodes().subList(1, path.getLength()));
			data[i][5] = Rounder.round(weightAnalysis.get("sumWeight",Double.class),2);
			data[i][6] = Rounder.round(weightAnalysis.get("occWeight",Double.class),2);
		}
		return data;
	}
	
	private void addSelectRowListener(){
		Consumer<Integer> selectRowFun = i -> selectPath(i,true);
	    Runnable resetRowsFun = () -> deselectPaths();
	        
	    super.addSelectRowListener(selectRowFun, resetRowsFun);
	}
	
	private void deselectPaths(){
		IntStream.range(0,data.length)
			.forEach(i -> selectPath(i,false));
	}
	
	private void selectPath(int i, boolean selected){
		Path path = paths.get(i);
		List<CyIdentifiable> cyIds = new ArrayList<>();
		cyIds.addAll(path.getNodes());
		cyIds.addAll(PathUtil.getAllPathEdges(path, network));
		
		for(CyIdentifiable id:cyIds){
			CyRowAdapter row = network.getRow(id);
			row.set(DefaultColumns.SELECTED, selected);
		}
	}
		
}
