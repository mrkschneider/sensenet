package com.tcb.sensenet.internal.meta.edge;

public enum EdgeType {
	MetaToMeta,MetaToAtom,AtomToMeta,AtomToAtom;
	
	public String toString(){
		switch(this){
			case AtomToAtom:
				return "atom-atom";
			case AtomToMeta:
				return "atom-meta";
			case MetaToAtom:
				return "meta-atom";
			case MetaToMeta:
				return "meta-meta";
			default:
				throw new RuntimeException("Encountered unknown type of MetaEdge.");
		}
	}
		
	public static EdgeType stringToType(String str){
		if(str==null || str.equals("")){
			return null;
		}
		
		switch(str){
			case "atom-atom":
				return AtomToAtom;
			case "atom-meta":
				return AtomToMeta;
			case "meta-atom":
				return MetaToAtom;
			case "meta-meta":
				return MetaToMeta;
			default:
				throw new RuntimeException(String.format("Encountered unknown type of MetaEdge: %s",str));
		}
	}
	
	
		
}
	

