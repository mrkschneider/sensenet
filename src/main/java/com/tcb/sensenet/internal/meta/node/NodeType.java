package com.tcb.sensenet.internal.meta.node;

public enum NodeType {
	Node,Metanode;
	
	public String toString(){
		switch(this){
		case Node: {return "node";}
		case Metanode: {return "metanode";}
		default: {throw new IllegalArgumentException("Unknown node type");}
		
		
		}
	}
}
