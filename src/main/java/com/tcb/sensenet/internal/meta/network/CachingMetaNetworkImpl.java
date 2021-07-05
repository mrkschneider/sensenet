package com.tcb.sensenet.internal.meta.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.tree.tree.Tree;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class CachingMetaNetworkImpl extends MetaNetworkImpl implements Serializable {
	
	private transient TLongObjectMap<List<CyNode>> subnodesCache;
	private transient TLongObjectMap<List<CyEdge>> subedgesCache;

	public CachingMetaNetworkImpl(CyRootNetworkAdapter rootNetwork, Tree tree) {
		super(rootNetwork, tree);
		init();
	}
	
	private void init(){
		this.subnodesCache = createSubnodesCache();
		this.subedgesCache = createSubedgesCache();
	}
	
	private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException {
		init();
	}
	
	private TLongObjectMap<List<CyNode>> createSubnodesCache(){
		return createCacheMap(getNodes(), super::getSubnodes);
	}
	
	private TLongObjectMap<List<CyEdge>> createSubedgesCache(){
		return createCacheMap(getEdges(), super::getSubedges);
	}
		
	private <T extends CyIdentifiable, U extends CyIdentifiable> TLongObjectMap<List<U>>
		createCacheMap(List<T> cyIds, Function<T,List<U>> mapFunc) {
		TLongObjectHashMap<List<U>> map = new TLongObjectHashMap<>();
		for(T cyId:cyIds){
			List<U> result = mapFunc.apply(cyId);
			map.put(cyId.getSUID(), result);
		}
		return map;
	}

	@Override
	public List<CyNode> getSubnodes(CyNode node){
		return subnodesCache.get(node.getSUID());
	}
	
	@Override
	public List<CyEdge> getSubedges(CyEdge edge){
		return subedgesCache.get(edge.getSUID());
	}
}
