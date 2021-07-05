package com.tcb.sensenet.internal.session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.session.events.SessionAboutToBeSavedEvent;
import org.cytoscape.session.events.SessionAboutToBeSavedListener;
import org.cytoscape.session.events.SessionSavedEvent;
import org.cytoscape.session.events.SessionSavedListener;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.io.FileFactory;
import com.tcb.cytoscape.cyLib.io.SplitFileOutputStream;
import com.tcb.cytoscape.cyLib.io.TempFileFactory;
import com.tcb.cytoscape.cyLib.util.TempUtil;


public class ClearTempFilesWhenSessionSaved implements SessionSavedListener {
	
	private TempUtil tempUtil;
	
	public ClearTempFilesWhenSessionSaved(TempUtil tempUtil){
		this.tempUtil = tempUtil;
	}	
	
	@Override
	public void handleEvent(SessionSavedEvent e) {
		tempUtil.clean();		
	}		
}
