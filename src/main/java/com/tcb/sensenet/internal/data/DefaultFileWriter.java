package com.tcb.sensenet.internal.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Arrays;

public class DefaultFileWriter {
		
	public void write(Iterable<String> lines, Path path) throws IOException {
		FileOutputStream out =  new FileOutputStream(path.toFile(), false);
	    BufferedWriter w = new BufferedWriter(new OutputStreamWriter(out));
	    try{
	    	for(String line:lines){
	    		w.write(line);
	 	    } 
	    } finally {
	    	w.close();
	    }	   
	}
	
	public void write(String line, Path path) throws IOException {
		write(Arrays.asList(line),path);
	}
}
