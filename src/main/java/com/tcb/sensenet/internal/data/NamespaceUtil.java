package com.tcb.sensenet.internal.data;

import com.tcb.sensenet.internal.CyActivator;

public class NamespaceUtil {

	public static String removeNamespacePrefix(String s){
		if(s.startsWith(CyActivator.APP_COLUMN_PREFIX)){
			return s.substring(CyActivator.APP_COLUMN_PREFIX.length());
		} else {
			return s;
		}
	}
}
