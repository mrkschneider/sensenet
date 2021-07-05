package com.tcb.sensenet.internal.session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.session.events.SessionAboutToBeSavedEvent;
import org.cytoscape.session.events.SessionAboutToBeSavedListener;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.io.FileFactory;
import com.tcb.cytoscape.cyLib.io.SplitFileOutputStream;
import com.tcb.cytoscape.cyLib.io.TempFileFactory;
import com.tcb.cytoscape.cyLib.util.TempUtil;


public class SaveMetaNetworkToSession implements SessionAboutToBeSavedListener {
	
	private AppGlobals appGlobals;
	private TempUtil tempUtil;
	
	public SaveMetaNetworkToSession(AppGlobals appGlobals, TempUtil tempUtil){
		this.appGlobals = appGlobals;
		this.tempUtil = tempUtil;
	}	
	
	@Override
	public void handleEvent(SessionAboutToBeSavedEvent e) {
		List<File> interactionFiles = createMetaNetworkFiles(e);
		try {
			e.addAppFiles(CyActivator.APP_NAME, interactionFiles);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private List<File> createMetaNetworkFiles(SessionAboutToBeSavedEvent e){
		List<File> tempFiles = new ArrayList<File>();
		try {
				tempFiles.addAll(writeSessionDataToFiles());
		}	
		catch (IOException ex) {
				ex.printStackTrace();
		}
		return tempFiles;
	}
				
	private List<File> writeSessionDataToFiles() throws IOException {
		/* Split session across files. Cytoscape loads the entire file in memory
		 * during deserialization, causing a limit of 2GB (int addressed byte array)
		 */
		FileFactory fileFactory = new TempFileFactory(tempUtil);
		
		SplitFileOutputStream fStream = new SplitFileOutputStream(fileFactory);
		ObjectOutputStream oStream = new ObjectOutputStream(fStream);
		
		writeSession(oStream);
		
		oStream.close();
		fStream.close();
		return fStream.getFiles();
	}
	
	private void writeSession(ObjectOutputStream oStream) throws IOException {
		MetaNetworkSession session = new MetaNetworkSession(
				appGlobals.state);
		oStream.writeObject(session);
	}
		
}
