package com.tcb.sensenet.internal.properties;

import java.util.function.Supplier;

public class AppPropertyUpdater {
	private AppProperties appProperties;

	public AppPropertyUpdater(AppProperties appProperties){
		this.appProperties = appProperties;
	}
	
	public <T> T parseAndUpdate(Supplier<T> supplier, AppProperty appProperty){
		T value = supplier.get();
		return update(value,appProperty);
	}
	
	public <T> T update(T value, AppProperty appProperty){
		appProperties.set(appProperty, value.toString());
		return value;
	}
}
