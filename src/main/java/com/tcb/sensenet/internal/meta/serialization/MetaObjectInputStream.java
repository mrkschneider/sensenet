package com.tcb.sensenet.internal.meta.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.MetaContext;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public class MetaObjectInputStream extends ObjectInputStream {

	public MetaContext context;
	public CySessionAdapter session;
	public AppGlobals appGlobals;

	public MetaObjectInputStream(
			InputStream in,
			CySessionAdapter session,
			AppGlobals appGlobals) throws IOException, SecurityException {
		super(in);
		this.appGlobals = appGlobals;
		this.session = session;
	}
	
	public static MetaObjectInputStream create(InputStream in){
		if (!(in instanceof MetaObjectInputStream)) {
			throw new RuntimeException("This object must be deserialized with " 
					+ MetaObjectInputStream.class.getSimpleName());
		}
		MetaObjectInputStream metaIn = (MetaObjectInputStream) in;
		return metaIn;
	}

}
