package com.tcb.sensenet.internal.UI.state;

import java.awt.Component;
import java.awt.Container;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.tcb.cytoscape.cyLib.util.ContainerUtils;

public class NestedComponentActivator {
	
	public static void setAllEnabled(
			Container container,
			Boolean enable,
			Class<? extends Annotation> annotationClass){
		for(Component c: ContainerUtils.getAllComponents(container)){
			List<Field> annotatedFields = 
					FieldUtils.getFieldsListWithAnnotation(c.getClass(), annotationClass);
			for(Field f:annotatedFields){
				setRecursiveFieldEnabled(f,c,enable);
			}		
		}
	}
	
	public static void setAllEnabled(
			Container container,
			Boolean enable){
		for(Component c: ContainerUtils.getAllComponents(container)){
			c.setEnabled(enable);
		}
	}
	
	private static void setRecursiveFieldEnabled(Field f, Object target, Boolean enable){
		Object o = getFieldObject(f,target);
		if(o instanceof Component) 
			((Component)o).setEnabled(enable);
		if(o instanceof Container) 
			ContainerUtils.getAllComponents(
					(Container)o).forEach(c -> c.setEnabled(enable));
	}
	
	private static Object getFieldObject(Field f, Object target){
		try {
			return FieldUtils.readField(f, target, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
