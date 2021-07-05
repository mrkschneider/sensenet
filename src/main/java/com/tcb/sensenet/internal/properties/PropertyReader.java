package com.tcb.sensenet.internal.properties;

import java.util.Properties;

import org.cytoscape.property.AbstractConfigDirPropsReader;
import org.cytoscape.property.CyProperty;
import org.osgi.framework.BundleContext;

import com.tcb.sensenet.internal.CyActivator;

public class PropertyReader extends AbstractConfigDirPropsReader {
	
	public static final String PROPERTIES_NAME = CyActivator.APP_NAME + ".props";
	
	public PropertyReader(String name, String fileName) {
         super(name, fileName, CyProperty.SavePolicy.CONFIG_DIR);
	 }
	
	public static void register(BundleContext bc){
		PropertyReader propsReader = new PropertyReader(CyActivator.APP_NAME, 
				PROPERTIES_NAME);
		Properties propsReaderServiceProps = new Properties();
		propsReaderServiceProps.setProperty("cyPropertyName", PROPERTIES_NAME);
		bc.registerService(CyProperty.class.getName(),
				propsReader, propsReaderServiceProps);
	}
}
