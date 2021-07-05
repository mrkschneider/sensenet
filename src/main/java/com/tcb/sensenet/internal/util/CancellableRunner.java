package com.tcb.sensenet.internal.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class CancellableRunner {

	public static <T> T run(
			Supplier<T> f,
			Supplier<Boolean> cancelCondition,
			Runnable cancelFun) throws CancelledException {
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		Future<T> task = executor.submit(f::get);
				
		while(!task.isDone()){
			try{
				Thread.sleep(50);	
			} catch(InterruptedException e){
				Thread.currentThread().interrupt();
				cancelFun.run();
			}
			if(cancelCondition.get()){
				cancelFun.run();
			}
		}
		
		try{
			return task.get();
		} catch(ExecutionException|InterruptedException e){
			throw new CancelledException(e);
		} finally {
			executor.shutdown();
		}
		
	}
}	

