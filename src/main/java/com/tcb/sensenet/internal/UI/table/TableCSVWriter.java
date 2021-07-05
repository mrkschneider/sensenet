package com.tcb.sensenet.internal.UI.table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.table.TableModel;

import com.tcb.sensenet.internal.util.ObjectPrinter;

public class TableCSVWriter {

	private TableView table;

	public TableCSVWriter(TableView table){
		this.table = table;
	}
		
	public void write(File file) throws IOException {
		FileOutputStream out =  new FileOutputStream(file, false);

        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(out));
        
        TableModel model = table.getTable().getModel();
        List<Integer> selectedRows = table.getVisibleRows();  
        
        w.write(
        		IntStream.range(0,model.getColumnCount())
        		.boxed()
        		.map(i -> model.getColumnName(i))
        		.map(s -> quote(s))
        		.collect(Collectors.joining(",")));
        w.newLine();
        ObjectPrinter printer = new ObjectPrinter();
        
        for(Integer i: selectedRows){
        	String line = IntStream.range(0, model.getColumnCount())
        			.boxed()
        			.map(j -> model.getValueAt(i, j))
        			.map(o -> printer.toString(o))
        			.map(s -> quote(s))
        			.collect(Collectors.joining(","));
        	w.write(line);
        	w.newLine();
        }
                
        w.close();
        
	}
	
	private String quote(String s){
		return "\"" + s + "\"";
	}
	
}
