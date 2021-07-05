package com.tcb.sensenet.internal.plot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class XYWriter {
	private FileOutputStream out;
	private BufferedWriter writer;
	private static final String commentChar = "#";

	public XYWriter(File f) throws FileNotFoundException{
		this.out =  new FileOutputStream(f, false);
        this.writer = new BufferedWriter(new OutputStreamWriter(out));
	}
	
	public void close() throws IOException{
		writer.close();
	}
	
	public void writeComment(String s) throws IOException{
		writer.write(commentChar + s);
		writer.newLine();
	}
	
	public void writeXY(Object x, Object y) throws IOException {
			writer.write(x.toString());
			writer.write(" ");
			writer.write(y.toString());
			writer.newLine();
	}
	
}
