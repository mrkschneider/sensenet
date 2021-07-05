package com.tcb.sensenet.internal.UI.table;

import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JCheckBox;
import javax.swing.RowFilter;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.common.util.ListFilter;

public class CyIdentifiableTableView extends TableView {
	
	private List<Long> suids;
	private CyNetworkAdapter network;
	
	public CyIdentifiableTableView(
			List<Long> suids,
			List<String> columnNames,
			List<List<?>> data,
			CyNetworkAdapter network,
			FileUtil fileUtil){
		super(
				columnNames.toArray(new String[0]),
				createData(columnNames,data),
				fileUtil);
		this.network = network;
		this.suids = suids;

		addSelectRowListener();
		addPlotHistogramButton();
        addShowOnlyActiveCheckBox();
	}
		
	public static List<List<?>> getValues(List<CyRowAdapter> rows, List<Columns> columns){
			List<List<?>> result = new ArrayList<>();
			for(Columns column:columns){
				List<Object> lst = new ArrayList<>();
				for(CyRowAdapter row:rows){
					lst.add(row.getMaybe(column, Object.class).orElse(null));
				}
				result.add(lst);
			}
			return result;
		}
	
		
	private static Object[][] createData(List<String> columnNames, List<List<?>> dataLst){
		int rowSize = ListFilter.singleton(
					dataLst.stream()
					.map(l -> l.size())
					.collect(Collectors.toSet()))
				.get();
		int columnSize = columnNames.size();
		Object[][] data = new Object[rowSize][columnSize];
		
		for(int i=0;i<rowSize;i++){
			for(int j=0;j<columnSize;j++){
				Object o = dataLst.get(j).get(i);
				if(o!=null && o.equals(Double.NaN)) o = null;
				data[i][j] = o;
			}
		}
		return data;
	}
		
	private void addShowOnlyActiveCheckBox(){
		JCheckBox showActiveCheckBox = new JCheckBox("Only show active");
				
		ItemListener buttonListener = new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				switch(e.getStateChange()){
				case ItemEvent.SELECTED: setFilter(true); break;
				case ItemEvent.DESELECTED: setFilter(false); break;
				default: break;
				}			
			}
			
		};
		
		
		showActiveCheckBox.addItemListener(buttonListener);
		
		
		GridBagConstraints c = getHeaderConstraints();
		
		this.add(showActiveCheckBox,c);
		showActiveCheckBox.doClick();
		
	}
	
	public void setFilter(boolean showVisibleEdges){
		if(showVisibleEdges){
			sorter.setRowFilter(createVisibleCyIdentifiablesFilter());
		} else {
			sorter.setRowFilter(null);
		}
	}
	
	private void addSelectRowListener(){
        Consumer<Integer> selectRowFun = i -> selectRow(getRow(i));
        Runnable resetRowsFun = () -> deselectRows();
        
        super.addSelectRowListener(selectRowFun, resetRowsFun);
	}
	
	private void deselectRows(){
		IntStream.range(0,data.length)
			.mapToObj(i -> getRow(i))
			.forEach(r -> deselectRow(r));
	}
		
	private CyRowAdapter getRow(Integer idx){
		Long suid = suids.get(idx);
		CyIdentifiable cyId = network.getNodeOrEdge(suid);
		if(cyId==null){
			return null;
		} else {
			return network.getRow(cyId);
		}
	}
	
	private void selectRow(CyRowAdapter row){
		if(row!=null){
			row.set(DefaultColumns.SELECTED, true);
		}
	}
	
	private void deselectRow(CyRowAdapter row){
		if(row!=null){
			row.set(DefaultColumns.SELECTED, false);
		}
	}
		
	private RowFilter<Object,Object> createVisibleCyIdentifiablesFilter(){
		RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
		      public boolean include(Entry entry) {
		    	int idx = (int)entry.getIdentifier();
		    	Long suid = suids.get(idx);
		    	return network.getNodeOrEdge(suid)!=null;
		      }
		    };
		return filter;    
	}
	
	
}
