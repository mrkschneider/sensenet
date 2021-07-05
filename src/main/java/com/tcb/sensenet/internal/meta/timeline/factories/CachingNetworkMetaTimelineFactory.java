package com.tcb.sensenet.internal.meta.timeline.factories;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.cytoscape.model.CyEdge;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;

public class CachingNetworkMetaTimelineFactory implements NetworkMetaTimelineFactory {
	private Cache<String,MetaTimeline> cache;
	private NetworkMetaTimelineFactory fac;
	
	public CachingNetworkMetaTimelineFactory(NetworkMetaTimelineFactory fac){
		this.fac = fac;
		this.cache = CacheBuilder.newBuilder()
				//.recordStats()
				.build();
	}
	
	/*@Override
	public void finalize(){
		System.out.println(cache.stats().toString());
		System.out.println(cache.stats().averageLoadPenalty());
	}*/

	@Override
	public MetaTimeline create(CyEdge e, MetaNetwork metaNetwork) {
		try{
			MetaTimeline result = 
					cache.get(
							getKey(e,metaNetwork),
							() -> fac.create(e,metaNetwork));
			return result;
		} catch(ExecutionException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public MetaTimelineFactory getMetaTimelineFactory() {
		return fac.getMetaTimelineFactory();
	}
		
	private String getKey(CyEdge edge, MetaNetwork metaNetwork){
		return edge.getSUID().toString() + "_" + metaNetwork.getSUID().toString();
	}

	
	

}
