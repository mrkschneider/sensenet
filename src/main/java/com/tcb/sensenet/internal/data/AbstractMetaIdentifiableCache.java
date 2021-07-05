package com.tcb.sensenet.internal.data;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.cytoscape.model.CyIdentifiable;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;

public abstract class AbstractMetaIdentifiableCache<T, U extends CyIdentifiable> {

	private Cache<String,T> cache;
	
	protected abstract T createNew(U e, MetaNetwork metaNetwork);
	
	public AbstractMetaIdentifiableCache(){
		this.cache = CacheBuilder.newBuilder()
				   .maximumSize(1000)
				   .expireAfterAccess(5, TimeUnit.MINUTES)
				   .recordStats()
			       .build();
	}
	
	public T create(U metaId, MetaNetwork metaNetwork){
		Callable<T> createNewCallable = new Callable<T>() {
			@Override
			public T call() {
				return createNew(metaId, metaNetwork);
			}
		};
				
		try {
			return cache.get(cacheString(metaId, metaNetwork), createNewCallable);
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException("Cache exception");
		}
	}
	
	private String cacheString(U metaId, MetaNetwork metaNetwork){
		return metaId.getSUID().toString() + "_" + metaNetwork.getSUID().toString();
	}
		
	public CacheStats getCacheStats(){
		return cache.stats();
	}
	
	public void reset(){
		cache.invalidateAll();
		cache.cleanUp();
	}
	
}
