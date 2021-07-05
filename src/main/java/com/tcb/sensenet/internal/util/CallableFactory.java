package com.tcb.sensenet.internal.util;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class CallableFactory {
	public static <T> Callable<T> create(Supplier<T> f){
		return new Callable<T>(){
			@Override
			public T call() throws Exception {
				return f.get();
			}
		};
	}
}
