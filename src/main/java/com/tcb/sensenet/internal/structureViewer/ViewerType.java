package com.tcb.sensenet.internal.structureViewer;

import com.tcb.netmap.fileFormat.FormatCollection;
import com.tcb.netmap.fileFormat.chimera.ChimeraStructureFormatCollection;
import com.tcb.netmap.fileFormat.chimera.ChimeraTopologyFormatCollection;
import com.tcb.netmap.fileFormat.chimera.ChimeraTrajectoryFormatCollection;
import com.tcb.netmap.fileFormat.pymol.PymolStructureFormatCollection;
import com.tcb.netmap.fileFormat.pymol.PymolTopologyFormatCollection;
import com.tcb.netmap.fileFormat.pymol.PymolTrajectoryFormatCollection;
import com.tcb.netmap.fileFormat.vmd.VmdStructureFormatCollection;
import com.tcb.netmap.fileFormat.vmd.VmdTopologyFormatCollection;
import com.tcb.netmap.fileFormat.vmd.VmdTrajectoryFormatCollection;

public enum ViewerType {
	VMD, PYMOL, CHIMERA;
	
	public String getDefaultBinName(){
		switch(this){
		case VMD: return "vmd";
		case PYMOL: return "pymol";
		case CHIMERA: return "chimera";
		default: throw new IllegalArgumentException();
		}
	}
	
	public String toString(){
		switch(this){
		case VMD: return "VMD";
		case PYMOL: return "PyMOL";
		case CHIMERA: return "UCSF Chimera";
		default: throw new IllegalArgumentException();
		}
	}
	
	public FormatCollection getStructureFormatCollection(){
		switch(this){
		case CHIMERA: return new ChimeraStructureFormatCollection();
		case VMD: return new VmdStructureFormatCollection();
		case PYMOL: return new PymolStructureFormatCollection();
		default: throw new IllegalArgumentException();
		}
	}
	
	public FormatCollection getTopologyFormatCollection(){
		switch(this){
		case CHIMERA: return new ChimeraTopologyFormatCollection();
		case VMD: return new VmdTopologyFormatCollection();
		case PYMOL: return new PymolTopologyFormatCollection();
		default: throw new IllegalArgumentException();
		}
	}
	
	public FormatCollection getTrajectoryFormatCollection(){
		switch(this){
		case CHIMERA: return new ChimeraTrajectoryFormatCollection();
		case VMD: return new VmdTrajectoryFormatCollection();
		case PYMOL: return new PymolTrajectoryFormatCollection();
		default: throw new IllegalArgumentException();
		}
	}
		
}
